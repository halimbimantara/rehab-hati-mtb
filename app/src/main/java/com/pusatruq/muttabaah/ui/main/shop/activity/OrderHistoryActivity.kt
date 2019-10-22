package com.pusatruq.muttabaah.ui.main.shop.activity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pusatruq.muttabaah.R
import kotlinx.android.synthetic.main.app_ecommerce_order_history.*

class OrderHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_ecommerce_order_history)

        initData()

        initDataBinding()

        initActions()
        initToolbar()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {

    }
    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Detail Transaction"

        try {
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.md_white_1000))
        } catch (e: Exception) {
            Log.e("TEAMPS", "Can't set color.")
        }

        try {
            setSupportActionBar(toolbar)
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set support action bar.")
        }

        try {
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }



    }

    private fun initDataBinding() {

    }

    private fun initActions() {
        viewDetailsTextView1.setOnClickListener({
            startActivity(Intent(this, DetailHistoryActivity::class.java))
        })
        viewDetailsTextView2.setOnClickListener({
            startActivity(Intent(this, DetailHistoryActivity::class.java))
        })
        viewDetailsTextView3.setOnClickListener({
            startActivity(Intent(this, DetailHistoryActivity::class.java))
        })
        viewDetailsTextView4 . setOnClickListener ({
            startActivity(Intent(this, DetailHistoryActivity::class.java))
        })
    }

}