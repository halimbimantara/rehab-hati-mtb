package com.pusatruq.muttabaah.ui.main.shop

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.data.repository.NewsRepository
import com.pusatruq.muttabaah.ui.core.base.BaseViewModel
import com.pusatruq.muttabaah.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by cuongpm on 11/29/18.
 */

class ShopViewModel
    @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    val isRefreshing = ObservableBoolean(false)
    val items: ObservableList<NewsEntity> = ObservableArrayList()
    val onNewsOpenEvent =
        SingleLiveEvent<NewsEntity>()

    private var disposable: Disposable? = null

    override fun start() {
        getAllNews(false)
    }

    override fun stop() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }

    fun refreshNews() {
        getAllNews(true)
    }

    fun openNews(news: NewsEntity) {
        onNewsOpenEvent.value = news
    }

    private fun getAllNews(isRefresh: Boolean) {
        if (isRefresh) newsRepository.refreshNews()

        disposable = newsRepository.getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }
            .subscribe({ news ->
                with(items) {
                    clear()
                    addAll(news)
                }
            }, { error ->
                error.printStackTrace()
            })
    }
}