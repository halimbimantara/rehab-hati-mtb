package com.pusatruq.muttabaah.ui.main.sholat.viewmodels

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pusatruq.muttabaah.data.AppEndPoint
import com.pusatruq.muttabaah.data.repository.MenuRepository
import com.pusatruq.muttabaah.ui.core.base.BaseViewModel
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.concurrent.Executors
import javax.inject.Inject


class SholatViewModel @Inject constructor(private val menuRepository: MenuRepository) :
    BaseViewModel() {
    val TAG: String = "SholatVm"
    val isRefreshing = ObservableBoolean(false)
    val ItemList: MutableLiveData<ArrayList<ModelListSholat.Result>> by lazy {
        MutableLiveData<ArrayList<ModelListSholat.Result>>()
    }
    val title = ObservableField("")
    private var disposable: Disposable? = null
    var sholatList: ArrayList<ModelListSholat.Result>? = ArrayList()

    override fun start() {
//        getSholatView(true)
        isRefreshing.set(true)
    }

    fun getListSholat(): LiveData<ArrayList<ModelListSholat.Result>> {   // Simple getter
        return ItemList!!
    }

    override fun stop() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }

    fun refreshComments() {
        getSholatView(true)
    }

    private fun getSholatView(isRefresh: Boolean) {
        if (isRefresh) menuRepository.refreshNews()
        disposable = menuRepository.getAllMenu()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }
            .subscribe({ news ->
                //                with(items) {
//                    clear()
//                    addAll(news)
//                }
            }, { error ->
                error.printStackTrace()
            })
    }

    fun postReport(id_kat: String, userId: String, status: String, ket: String) {
        AndroidNetworking.post(AppEndPoint.POST_DAILY_SHOLAT_RWT)
            .addBodyParameter("kategori", id_kat)
            .addBodyParameter("user_id", userId)
            .addBodyParameter("status", status)
            .addBodyParameter("ket", ket)
            .setExecutor(Executors.newSingleThreadExecutor())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    if (response!!.getBoolean("status")) {
                        message.postValue(response.getString("message"))
                    } else {
                        message.postValue(response.getString("message"))
                    }
                }

                override fun onError(anError: ANError?) {
                    onErrorCatch(anError!!)
                }
            })
    }

    fun getStatusChecked(parent: String, tgl: String, userId: String) {
        AndroidNetworking.post(AppEndPoint.POST_DAILY_SHOLAT_RWT_CHECKING)
            .addPathParameter("parent", parent)
            .addBodyParameter("user_id", userId)
            .addBodyParameter("tanggal", tgl)
            .setExecutor(Executors.newSingleThreadExecutor())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    if (response!!.getBoolean("status")) {
                        val data: String = response.getJSONArray("result").toString()
                        sholatList = Gson().fromJson<ArrayList<ModelListSholat.Result>>(
                            data,
                            object : TypeToken<ArrayList<ModelListSholat.Result>>() {}.type
                        )
                        ItemList!!.postValue(sholatList)
//                        fruitList!!.postValue(sholatList)
                        isRefreshing.set(false)
                    } else {
                        ItemList!!
                        isRefreshing.set(false)
                    }
                }

                override fun onError(anError: ANError?) {
                    onErrorCatch(anError!!)
                    isRefreshing.set(false)
                }

            })
    }

    fun onErrorCatch(error: ANError) {
        if (error.errorCode != 0) {
            // received error from server
            // error.getErrorCode() - the error code from server
            // error.getErrorBody() - the error body from server
            // error.getErrorDetail() - just an error detail
            Log.d(TAG, "onError errorCode : " + error.errorCode)
            Log.d(TAG, "onError errorBody : " + error.errorBody)
            Log.d(TAG, "onError errorDetail : " + error.errorDetail)
            // get parsed error object (If ApiError is your class)
            message.postValue(error.errorBody)
//            val apiError = error.getErrorAsObject(ApiError::class.java)
        } else {
            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
            Log.e(TAG, "onError errorDetail : " + error.errorDetail)
        }
    }
}