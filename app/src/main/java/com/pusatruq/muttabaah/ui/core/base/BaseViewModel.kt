package com.pusatruq.muttabaah.ui.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by cuongpm on 12/10/18.
 */

abstract class BaseViewModel : ViewModel() {
    val message = MutableLiveData<Any>()
    abstract fun start()
    abstract fun stop()
}