package com.pusatruq.muttabaah.ui.component.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.data.local.room.CommentEntity
import com.pusatruq.muttabaah.ui.component.adapter.CommentAdapter
//import com.kotlin.mvvm.gogolf.ui.component.adapter.NewsAdapter

/**
 * Created by cuongpm on 12/13/18.
 */

object RecyclerViewBinding {

    @BindingAdapter("app:addVerticalItemDecoration")
    @JvmStatic
    fun RecyclerView.addVerticalItemDecoration(isVertical: Boolean) {
        addItemDecoration(DividerItemDecoration(context, if (isVertical) VERTICAL else HORIZONTAL))
    }

//    @BindingAdapter("app:items")
//    @JvmStatic
//    fun setListNews(recyclerView: RecyclerView, items: List<NewsEntity>) {
//        with(recyclerView.adapter as NewsAdapter?) {
//            this?.setData(items)
//        }
//    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setListComments(recyclerView: RecyclerView, items: List<CommentEntity>) {
        with(recyclerView.adapter as CommentAdapter?) {
            this?.setData(items)
        }
    }
}