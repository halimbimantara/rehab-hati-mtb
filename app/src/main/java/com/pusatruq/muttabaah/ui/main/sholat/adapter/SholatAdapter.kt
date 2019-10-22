package com.pusatruq.muttabaah.ui.main.sholat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import kotlinx.android.synthetic.main.item_default_checklist.view.*

/**
 * Created by Mhbx on 20/10/19.
 * Contact Email : mhalim@gmail.com
 */
class SholatAdapter(private val basketList: ArrayList<ModelListSholat.Result>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: ModelListSholat.Result, position: Int, isChecked: Boolean)
    }

    fun setAppList(Models: ArrayList<ModelListSholat.Result>) {
        basketList.addAll(Models)
        //notifyItemRangeInserted(0, categoryModel.size)
        notifyDataSetChanged()
    }

   fun clearAll(){
       basketList.clear()
       notifyDataSetChanged()
   }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_default_checklist, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is PlaceViewHolder) {
            val basket = basketList[position]
            viewHolder.itemNameTextView.text = basket.nama
            viewHolder.itemCheckBox.isChecked = if (basket.status.equals("1")) true else false
            viewHolder.itemCheckBox.setOnCheckedChangeListener({ buttonView, isChecked ->
                itemClickListener.onItemClick(buttonView, basket, position, isChecked)
            })
        }
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    inner class PlaceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemCheckBox: CheckBox
        internal var itemNameTextView: TextView

        init {
            itemCheckBox = view.checkbox_sholat
            itemNameTextView = view.tv_title_check
        }
    }
}
