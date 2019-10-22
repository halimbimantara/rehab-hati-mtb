package com.pusatruq.muttabaah.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import kotlinx.android.synthetic.main.item_default_checklist.view.*
import kotlinx.android.synthetic.main.item_menu_muttabaah.view.*

/**
 * Created by Mhbx on 20/10/19.
 * Contact Email : mhalim@gmail.com
 */
class MenuAdapter(private val basketList: ArrayList<MenuEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: MenuEntity, position: Int)
    }

    fun setAppList(Models: ArrayList<MenuEntity>) {
        basketList.addAll(Models)
        //notifyItemRangeInserted(0, categoryModel.size)
        notifyDataSetChanged()
    }

    fun clearAll() {
        basketList.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_muttabaah, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is PlaceViewHolder) {
            val basket = basketList[position]
            viewHolder.itemNameTextView.text = basket.title
            viewHolder.itemMenuCardView.setOnClickListener({
                itemClickListener.onItemClick(it,basket,position)
            })

        }
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    inner class PlaceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemMenuCardView: CardView
        internal var itemNameTextView: TextView

        init {
            itemMenuCardView = view.CV_Menu_Home
            itemNameTextView = view.tv_title_submenu
        }
    }
}
