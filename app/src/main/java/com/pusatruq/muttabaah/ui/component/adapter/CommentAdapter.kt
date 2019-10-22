package com.pusatruq.muttabaah.ui.component.adapter

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.data.local.room.CommentEntity
import com.pusatruq.muttabaah.databinding.ItemCommentBinding

/**
 * Created by cuongpm on 12/10/18.
 */

class CommentAdapter(
    private var comments: List<CommentEntity>
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = DataBindingUtil.inflate<ItemCommentBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_comment, parent, false
        )

        return CommentViewHolder(binding)
    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) = holder.bind(comments[position])

    class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentEntity: CommentEntity) {
            with(binding)
            {
                comment = commentEntity
                executePendingBindings()
            }
        }
    }

    fun setData(comments: List<CommentEntity>) {
        this.comments = comments
        notifyDataSetChanged()
    }
}