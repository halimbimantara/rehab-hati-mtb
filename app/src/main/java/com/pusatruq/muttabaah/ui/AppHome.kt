package com.pusatruq.muttabaah.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.MainActivity
import com.pusatruq.muttabaah.util.Utils
import kotlinx.android.synthetic.main.app_main_home.*

class AppHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main_home)

        initData()

        initToolbar()

        initDataBinding()

        initActions()

    }

    //region Init Functions
    private fun initData() {

    }


    private fun initDataBinding() {
        Utils.setImageToImageView(this, gradientImageView, R.drawable.black_alpha_70)

        Utils.setImageToImageView(this, stationaryImageView, R.drawable.menu_quran)
        Utils.setImageToImageView(this, booksImageView, R.drawable.menu_mtb)
        Utils.setImageToImageView(this, tutorImageView, R.drawable.menu_ruqyah)
        Utils.setImageToImageView(this, institutionImageView, R.drawable.menu_infaq)

    }

    private fun initActions() {

        gradientImageView.setOnClickListener {
            info()
        }

        stationaryImageView.setOnClickListener {
            info()
        }

        booksImageView.setOnClickListener {
            startActivity(Intent(this@AppHome, MainActivity::class.java))
        }

        tutorImageView.setOnClickListener {
            info()
        }

        institutionImageView.setOnClickListener {
            info()
        }

    }

    fun info() {
        Toast.makeText(this, "Segera Hadir ...", Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }

        toolbar.title = "Project Hati app"

        try {
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000))
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
}

