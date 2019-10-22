package com.pusatruq.muttabaah.di.component

import com.pusatruq.muttabaah.BLApplication
import com.pusatruq.muttabaah.di.module.*
import com.pusatruq.muttabaah.di.module.ActivityBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, DatabaseModule::class, NetworkModule::class,
        RepositoryModule::class, ViewModelModule::class, ActivityBindingModule::class]
)
interface AppComponent : AndroidInjector<BLApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BLApplication): Builder

        fun build(): AppComponent
    }
}