package com.pusatruq.muttabaah.ui.main.home

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
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pusatruq.muttabaah.data.AppEndPoint
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.data.repository.MenuRepository
import com.pusatruq.muttabaah.data.repository.NewsRepository
import com.pusatruq.muttabaah.ui.core.base.BaseViewModel
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import com.pusatruq.muttabaah.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by cuongpm on 11/29/18.
 */

class HomeViewModel
@Inject constructor(
    private val newsRepository: NewsRepository,
    private val menuRepository: MenuRepository
) : BaseViewModel() {
    val TAG: String = "HomeVm"
    val isRefreshing = ObservableBoolean(false)
    val items: ObservableList<NewsEntity> = ObservableArrayList()
    val itemsMenu: ObservableList<MenuEntity> = ObservableArrayList()
    val onNewsOpenEvent = SingleLiveEvent<NewsEntity>()

    val ItemList: MutableLiveData<ArrayList<MenuEntity>> by lazy {
        MutableLiveData<ArrayList<MenuEntity>>()
    }
    val title = ObservableField("")
    var menuList: ArrayList<MenuEntity>? = ArrayList()

    private var disposable: Disposable? = null

    override fun start() {
//        getAllMenu(false)
        isRefreshing.set(true)
    }

    override fun stop() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }

    fun refreshNews() {
        getAllMenu(true)
    }

    fun getListSubmenu(): LiveData<ArrayList<MenuEntity>> {   // Simple getter
        return ItemList!!
    }

    fun openNews(news: NewsEntity) {
        onNewsOpenEvent.value = news
    }

    private fun getAllNews(isRefresh: Boolean) {
        if (isRefresh) newsRepository.refreshNews()

        disposable = newsRepository.getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }
            .subscribe({ news ->
                with(items) {
                    clear()
                    addAll(news)
                }
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun getAllMenu(isRefresh: Boolean) {
        if (isRefresh) menuRepository.refreshNews()

        disposable = menuRepository.getAllMenu()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }
            .subscribe({ menus ->
                with(itemsMenu) {
                    clear()
                    addAll(menus)
                }
            }, { error ->
                error.printStackTrace()
            })
    }

    fun getSubmenu() {
        AndroidNetworking.get(AppEndPoint.GET_MAIN_MENU)
            .setExecutor(Executors.newSingleThreadExecutor())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onError(anError: ANError?) {
                    onErrorCatch(anError!!)
                }

                override fun onResponse(response: JSONArray?) {
                    menuList = Gson().fromJson<ArrayList<MenuEntity>>(
                        response.toString(),
                        object : TypeToken<ArrayList<MenuEntity>>() {}.type
                    )
                    ItemList!!.postValue(menuList)
//
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