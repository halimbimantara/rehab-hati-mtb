package com.pusatruq.muttabaah.ui.main.shop.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.pusatruq.muttabaah.R
import kotlinx.android.synthetic.main.app_ecommerce_checkout_1_activity.*

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_ecommerce_checkout_1_activity)

        initData()

        initDataBinding()

        initActions()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {

    }


    private fun initDataBinding() {

    }

    private fun initActions() {
        nextButton.setOnClickListener {
           startActivity( Intent(this,CheckoutPaymentActivity::class.java))
        }
        closeImageView.setOnClickListener { this.finish() }

    }

}