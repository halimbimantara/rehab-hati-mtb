package com.pusatruq.muttabaah.di.module

import android.app.Application
import android.content.Context
import com.pusatruq.muttabaah.BLApplication
import com.pusatruq.muttabaah.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module

/**
 * Created by cuongpm on 11/29/18.
 */

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: BLApplication): Context

    @Binds
    abstract fun bindApplication(application: BLApplication): Application
}