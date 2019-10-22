package com.pusatruq.muttabaah.ui.main.comment

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.pusatruq.muttabaah.data.local.room.CommentEntity
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.data.repository.NewsRepository
import com.pusatruq.muttabaah.ui.core.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by cuongpm on 12/31/18.
 */

class CommentViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    var newsEntity: NewsEntity? = null

    val isRefreshing = ObservableBoolean(false)
    val title = ObservableField("")
    val items: ObservableList<CommentEntity> = ObservableArrayList()

    private var disposable: Disposable? = null

    override fun start() {
        getAllComments(false)
    }

    override fun stop() {
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }

    fun refreshComments() {
        getAllComments(true)
    }

    fun showNewsInfo() {
        newsEntity?.let {
            title.set(it.title)
        }
    }

    private fun getAllComments(isRefresh: Boolean) {
        if (isRefresh) newsRepository.refreshComments()

        newsEntity?.let {
            disposable = newsRepository.getAllComments(it.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isRefreshing.set(true) }
                .doAfterTerminate { isRefreshing.set(false) }
                .subscribe({ comments ->
                    with(items) {
                        clear()
                        addAll(comments)
                    }
                }, { error ->
                    error.printStackTrace()
                })
        }
    }
}