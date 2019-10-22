package com.pusatruq.muttabaah.ui.core.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.pusatruq.muttabaah.util.NetworkUtils
import dagger.android.support.DaggerAppCompatActivity


/**
 * Created by cuongpm on 11/29/18.
 */

abstract class BaseActivity : DaggerAppCompatActivity() {
    fun openActivity(activity: Activity?, intent: Intent) {
        activity?.startActivity(intent)
    }

    fun ShowMessages(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm != null) {
                imm!!.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }
}
