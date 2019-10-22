package com.pusatruq.muttabaah.ui.main.home.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.pusatruq.muttabaah.R
import com.smarteist.autoimageslider.SliderViewAdapter

internal class SliderAdapterHome : SliderViewAdapter<SliderAdapterHome.SliderAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.widget_image_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        viewHolder.textViewDescription.text = "This is slider item $position"

        when (position) {
            0 -> Glide.with(viewHolder.itemView)
                .load("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
            1 -> Glide.with(viewHolder.itemView)
                .load("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
            2 -> Glide.with(viewHolder.itemView)
                .load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
            else -> Glide.with(viewHolder.itemView)
                .load("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
        }

    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return 4
    }

    internal inner class SliderAdapterVH(var itemView: View) :
        SliderViewAdapter.ViewHolder(itemView) {
        var imageViewBackground: ImageView
        var textViewDescription: TextView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
        }
    }
}
