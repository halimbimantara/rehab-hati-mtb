package com.pusatruq.muttabaah.ui.main.shop.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.shop.model.ShopItem
import com.pusatruq.muttabaah.util.Utils
import kotlinx.android.synthetic.main.app_ecommerce_list_1_item.view.*

/**
 * Created by Panacea-Soft on 6/26/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class AppECommerceList1Adapter(private val shopItemList: List<ShopItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(view: View, obj: ShopItem, position: Int)
        fun onAddToCartClick(view: View, obj: ShopItem, position: Int)
        fun onMenuClick(view: View, obj: ShopItem, position: Int,Name:String)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.app_ecommerce_list_1_item, parent, false)

        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is PlaceViewHolder) {

            val shopItem = shopItemList[position]

            viewHolder.itemNameTextView.text = shopItem.name

            val context = viewHolder.holderCardView.context

            val id = Utils.getDrawableInt(context, shopItem.imageName)
            Utils.setImageToImageView(context, viewHolder.itemImageView, id)

            viewHolder.categoryTextView.text = shopItem.categoryName
            val priceStr = shopItem.currency + " " + shopItem.price
            viewHolder.priceTextView.text = priceStr

            val originalPriceStr = shopItem.currency + " " + shopItem.originalPrice
            viewHolder.originalPriceTextView.text = originalPriceStr

            viewHolder.ratingBar.rating = java.lang.Float.parseFloat(shopItem.totalRating)
            val ratingCountStr = "(" + shopItem.totalRating + ")"
            viewHolder.ratingCountTextView.text = ratingCountStr

            if (Integer.parseInt(shopItem.discount) > 0) {
                viewHolder.promoCardView.visibility = View.VISIBLE
                val discount = shopItem.discount + " %"
                viewHolder.promoAmtTextView.text = discount
                viewHolder.originalPriceTextView.paintFlags = viewHolder.originalPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                viewHolder.promoCardView.visibility = View.GONE
                viewHolder.originalPriceTextView.visibility = View.GONE
            }

            viewHolder.holderCardView.setOnClickListener { v: View -> itemClickListener.onItemClick(v,
                shopItemList[position], position)
            }
            viewHolder.addToBasketImageView.setOnClickListener { v: View -> itemClickListener.onAddToCartClick(v, shopItemList[position], position) }
            viewHolder.menuImageView.setOnClickListener { v: View -> itemClickListener.onMenuClick(v, shopItemList[position], position,shopItem.name) }
            viewHolder.holderCardView.setOnClickListener { v: View -> itemClickListener.onMenuClick(v, shopItemList[position], position,shopItem.name) }


        }
    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }

    inner class PlaceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.itemImageView
        internal var ratingBar: RatingBar = view.ratingBar
        internal var itemNameTextView: TextView = view.itemNameTextView
        internal var categoryTextView: TextView = view.categoryTextView
        internal var priceTextView: TextView = view.priceTextView
        internal var originalPriceTextView: TextView = view.originalPriceTextView
        internal var ratingCountTextView: TextView = view.ratingCountTextView
        internal var promoAmtTextView: TextView = view.promoAmtTextView
        internal var promoCardView: CardView = view.promoCardView
        internal var holderCardView: CardView = view.holderCardView
        internal var addToBasketImageView: ImageView = view.addToCartImageView
        internal var menuImageView: ImageView = view.menuImageView

    }

}