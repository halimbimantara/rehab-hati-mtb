package com.pusatruq.muttabaah.ui.main.accounts.viewmodels

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
import com.pusatruq.muttabaah.ui.main.accounts.model.RegisterPost
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import com.pusatruq.muttabaah.util.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.concurrent.Executors
import javax.inject.Inject


class SignUpViewModel @Inject constructor(private val menuRepository: MenuRepository) :
    BaseViewModel() {
    val TAG: String = "SignUpVm"
    val isRefreshing = ObservableBoolean(false)
    val title = ObservableField("")
    val modelRegister = MutableLiveData<RegisterPost>()
    private var disposable: Disposable? = null

    override fun start() {
        isRefreshing.set(true)
    }

    override fun stop() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }

    fun postReport(model:RegisterPost) {
        AndroidNetworking.post(AppEndPoint.POST_REGISTER)
            .setExecutor(Executors.newSingleThreadExecutor())
            .setPriority(Priority.HIGH)
            .addBodyParameter(model)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    if (response!!.getBoolean("status")) {
                        message.postValue(AppConstants.MESSAGE_INFO_SUCCESS)
                        model.IdUser =response.getInt("id_user")
                        modelRegister.postValue(model)
                    } else {
                        message.postValue(AppConstants.MESSAGE_INFO_FAILED)
                    }
                }
                override fun onError(anError: ANError?) {
                    onErrorCatch(anError!!)
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