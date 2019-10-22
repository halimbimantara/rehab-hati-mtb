package com.pusatruq.muttabaah.ui.main.comment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.databinding.FragmentCommentBinding
import com.pusatruq.muttabaah.di.ActivityScoped
import com.pusatruq.muttabaah.ui.component.adapter.CommentAdapter
import com.pusatruq.muttabaah.ui.core.base.BaseFragment
import javax.inject.Inject

/**
 * Created by cuongpm on 12/31/18.
 */

@ActivityScoped
class CommentFragment @Inject constructor() : BaseFragment() {

    companion object {
        const val NEWS_DATA = "news_data"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var commentViewModel: CommentViewModel

    private lateinit var dataBinding: FragmentCommentBinding

    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        commentViewModel = ViewModelProviders.of(this, viewModelFactory).get(CommentViewModel::class.java)
        commentAdapter = CommentAdapter(ArrayList(0))

        dataBinding = FragmentCommentBinding.inflate(inflater, container, false).apply {
            this.viewModel = commentViewModel
            this.adapter = commentAdapter
            this.navigationListener = navigationIconClickListener
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentViewModel.newsEntity = arguments?.getString(NEWS_DATA)?.let {
            Gson().fromJson(it, NewsEntity::class.java)
        }

        commentViewModel.start()
        commentViewModel.showNewsInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        commentViewModel.stop()
    }

    private val navigationIconClickListener = View.OnClickListener { activity?.finish() }
}