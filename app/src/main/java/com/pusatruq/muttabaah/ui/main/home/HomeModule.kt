package com.pusatruq.muttabaah.ui.main.home

import com.pusatruq.muttabaah.di.FragmentScoped
import com.pusatruq.muttabaah.ui.main.maparea.MapsAreaFragment
import com.pusatruq.muttabaah.ui.main.scorring.ScorringFragment
import com.pusatruq.muttabaah.ui.main.shop.ShopingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by cuongpm on 11/29/18.
 */

@Module
abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindMapFragment(): MapsAreaFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindScoreFragment(): ScorringFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bindShopFragment(): ShopingFragment
}