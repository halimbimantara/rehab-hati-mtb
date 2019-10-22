package com.pusatruq.muttabaah.ui.component.binding

import android.os.Build
import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.Toolbar

/**
 * Created by cuongpm on 1/1/19.
 */

object ToolbarBinding {

    @BindingAdapter("app:navigationOnClickListener")
    @JvmStatic
    fun Toolbar.setNavigationOnClickListener(onClickListener: View.OnClickListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setNavigationOnClickListener(onClickListener)
        }
    }
}