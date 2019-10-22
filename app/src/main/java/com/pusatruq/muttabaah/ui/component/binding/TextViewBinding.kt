package com.pusatruq.muttabaah.ui.component.binding

import androidx.databinding.BindingAdapter
import android.text.Html
import android.widget.TextView

/**
 * Created by cuongpm on 1/2/19.
 */

object TextViewBinding {

    @BindingAdapter("app:textHtml")
    @JvmStatic
    fun TextView.textHtml(text: String?) {
        text?.let { setText(Html.fromHtml(it)) }
    }
}