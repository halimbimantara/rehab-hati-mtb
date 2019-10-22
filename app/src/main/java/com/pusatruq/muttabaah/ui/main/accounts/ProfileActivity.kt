package com.pusatruq.muttabaah.ui.main.accounts

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.afollestad.rxkprefs.Pref
import com.afollestad.rxkprefs.RxkPrefs
import com.afollestad.rxkprefs.rxkPrefs
import com.bumptech.glide.Glide
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.data.local.Preferences.AppPreferencesDataStore
import com.pusatruq.muttabaah.ui.core.base.BaseActivity
import com.pusatruq.muttabaah.util.AppConstants
import com.pusatruq.muttabaah.util.Utils
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : BaseActivity() {

    lateinit var myPrefs: RxkPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        myPrefs = rxkPrefs(this, AppConstants.APP_NAME_PREFERENCES)
        iniiUI()
        initProfile()
        initData()
    }

    private fun initData() {
        val myUsername: Pref<String> = myPrefs.string(AppPreferencesDataStore.PREFERENCES_UNAME)
        val myEmail: Pref<String> = myPrefs.string(AppPreferencesDataStore.PREFERENCES_EMAIL)
        val photoPic: Pref<String> = myPrefs.string(AppPreferencesDataStore.PREFERENCES_PICT_PROFILE)
        val noHp: Pref<String> = myPrefs.string(AppPreferencesDataStore.PREFERENCES_PHONE)
        nameTextView.setText(myUsername.get())
        emailTextView.setText(myEmail.get())
        phoneField.setText(noHp.get())
//pict
        Glide.with(this)
            .load(photoPic.get())
            .circleCrop()
            .into(profileImageView)

        Glide.with(this)
            .load(photoPic.get())
            .into(coverUserImageView)
    }

    fun iniiUI() {
        val id = R.drawable.profile2
//        Utils.setCircleImageToImageView(
//            applicationContext,
//            profileImageView,
//            id,
//            20,
//            R.color.md_white_1000
//        )

//        val coverUserImageView = findViewById<ImageView>(R.id.coverUserImageView)
//        Utils.setImageToImageView(applicationContext, coverUserImageView, id)
    }

    fun initProfile() {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp)
        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }

        toolbar.title = "Profile"

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
                supportActionBar?.setDisplayShowHomeEnabled(true);
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }
        toolbar.setNavigationOnClickListener { view ->
            finish()
        }

    }
}
