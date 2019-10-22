package com.pusatruq.muttabaah.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.home.model.TimelineTodo

/**
 * Created by Panacea-Soft on 8/24/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class FeatureTimelineAdapter(private val timelineTodoList: List<TimelineTodo>?, private val limitCount: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: TimelineTodo, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_timeline_general_timeline_2_important_item, parent, false)
            return ImportantItemViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_timeline_general_timeline_2_item, parent, false)
            return ItemViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val timelineTodo = timelineTodoList!![position]

            viewHolder.timeTextView.text = timelineTodo.time
            viewHolder.noteTextView.text = timelineTodo.note
            viewHolder.intervalTextView.text = timelineTodo.interval

            when (timelineTodo.color) {
                "green" -> viewHolder.colorImageView.setColorFilter(ContextCompat.getColor(viewHolder.colorImageView.context,
                        R.color.md_green_500))
                "blue" -> viewHolder.colorImageView.setColorFilter(ContextCompat.getColor(viewHolder.colorImageView.context,
                        R.color.md_blue_500))
                "orange" -> viewHolder.colorImageView.setColorFilter(ContextCompat.getColor(viewHolder.colorImageView.context,
                        R.color.md_orange_500))
            }

                viewHolder.constraintLayout.setOnClickListener { v: View -> itemClickListener.onItemClick(v, timelineTodoList[position], position) }

        } else if (viewHolder is ImportantItemViewHolder) {

            val timelineTodo = timelineTodoList!![position]

            viewHolder.timeTextView.text = timelineTodo.time
            viewHolder.noteTextView.text = "Important : " + timelineTodo.note
            viewHolder.intervalTextView.text = timelineTodo.interval

            viewHolder.lineView.setBackgroundColor(ContextCompat.getColor(viewHolder.lineView.context,
                    R.color.colorPrimary))

                viewHolder.constraintLayout.setOnClickListener { v: View -> itemClickListener.onItemClick(v, timelineTodoList[position], position) }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (timelineTodoList!![position].isImportant!!) {
            0
        } else {
            1
        }
    }

    override fun getItemCount(): Int {

        return if (timelineTodoList != null) {

            if (limitCount > 0) {
                if (limitCount > timelineTodoList.size) {
                    timelineTodoList.size
                } else {
                    limitCount
                }
            } else {
                timelineTodoList.size
            }

        } else {
            0
        }
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var timeTextView: TextView = view.findViewById(R.id.timeTextView)
        internal var noteTextView: TextView = view.findViewById(R.id.noteTextView)
        internal var intervalTextView: TextView = view.findViewById(R.id.intervalTextView)
        internal var colorImageView: ImageView = view.findViewById(R.id.colorImageView)
        internal var constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)

    }

    inner class ImportantItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var timeTextView: TextView = view.findViewById(R.id.timeTextView)
        internal var noteTextView: TextView = view.findViewById(R.id.noteTextView)
        internal var intervalTextView: TextView = view.findViewById(R.id.intervalTextView)
        internal var lineView: View = view.findViewById(R.id.lineView)
        internal var constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)

    }
}

