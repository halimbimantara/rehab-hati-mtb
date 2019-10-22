package com.pusatruq.muttabaah.ui.component.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by cuongpm on 12/13/18.
 */

object SwipeRefreshLayoutBinding {

    @BindingAdapter("app:isRefreshing")
    @JvmStatic
    fun SwipeRefreshLayout.isRefreshing(isRefreshing: Boolean) {
        this.isRefreshing = isRefreshing
    }

    @BindingAdapter("app:onRefreshListener")
    @JvmStatic
    fun SwipeRefreshLayout.setOnRefreshListener(func: () -> Unit) {
        setOnRefreshListener { func() }
    }
}