package com.pusatruq.muttabaah.di.module

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pusatruq.muttabaah.data.remote.MenuService
import com.pusatruq.muttabaah.data.remote.NewsService
import com.pusatruq.muttabaah.util.Memory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val HACKER_NEWS_URL = "http://pusatruqyah.com/api/"
    }

    private fun buildOkHttpClient(application: Application): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .cache(
                Cache(
                    File(application.cacheDir, "BLCache"),
                    Memory.calcCacheSize(application, .25f)
                )
            )
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient = buildOkHttpClient(application)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(HACKER_NEWS_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Provides
    @Singleton
    fun provideMenuService(retrofit: Retrofit): MenuService =
        retrofit.create(MenuService::class.java)

}
