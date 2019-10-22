package com.pusatruq.muttabaah

import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pusatruq.muttabaah.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by cuongpm on 11/29/18.
 */

open class BLApplication : DaggerApplication() {

    private lateinit var androidInjector: AndroidInjector<out DaggerApplication>

    override fun onCreate() {
        super.onCreate()

        // Initialize Stetho
        Stetho.initializeWithDefaults(this)
        AndroidThreeTen.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

        androidInjector = DaggerAppComponent.builder().application(this).build()
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> = androidInjector

}