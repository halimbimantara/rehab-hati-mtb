package com.pusatruq.muttabaah.ui.main.shop.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pusatruq.muttabaah.R
import kotlinx.android.synthetic.main.app_ecommerce_checkout_1_activity.closeImageView
import kotlinx.android.synthetic.main.app_ecommerce_checkout_1_activity.nextButton
import kotlinx.android.synthetic.main.app_ecommerce_checkout_6_activity.*

class CheckoutFinish : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_ecommerce_checkout_6_activity)

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
            Toast.makeText(this, "Clicked Next.", Toast.LENGTH_SHORT).show()
        }

        MyHistory.setOnClickListener {
            startActivity( Intent(this,OrderHistoryActivity::class.java))
        }
        closeImageView.setOnClickListener { this.finish() }

    }

}