package com.pusatruq.muttabaah.ui.main.shop.adapter

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.shop.model.Basket
import com.pusatruq.muttabaah.util.Utils
import kotlinx.android.synthetic.main.feature_basket_ecommerce_basket_3_item.view.*

/**
 * Created by Panacea-Soft on 20/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
class FeatureBasketECommerceBasket3Adapter(private val basketList: List<Basket>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var currency: String
    private var total = 0

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Basket, position: Int)

        fun onDeleteClick(view: View, obj: Basket, position: Int)

        fun onPriceChange(currency: String, subTotal: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_basket_ecommerce_basket_3_item, parent, false)

        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is PlaceViewHolder) {

            val basket = basketList[position]
            currency = basket.currency
            viewHolder.itemNameTextView.text = basket.name

            val context = viewHolder.holderCardView.context

            val id = Utils.getDrawableInt(context, basket.image)
            Utils.setImageToImageView(context, viewHolder.itemImageView, id)


            val priceStr = basket.currency + " " + basket.price
            viewHolder.priceTextView.text = priceStr

            viewHolder.attributeTextView.text = basket.size

            try {
                val value = Integer.parseInt(viewHolder.qtyTextView.text.toString())
                val price = Integer.parseInt(basket.price)
                val subTotal = value * price
                val subTotalStr = basket.currency + " " + subTotal

                total += subTotal

                viewHolder.subTotalTextView.text = subTotalStr
            } catch (ignored: Exception) {
            }

            viewHolder.minusImageView.setOnClickListener {

                try {
                    var value = Integer.parseInt(viewHolder.qtyTextView.text.toString())

                    if (value > 1) {
                        value -= 1
                    }

                    viewHolder.qtyTextView.text = value.toString()

                    val itemPriceStr = viewHolder.priceTextView.text.toString()
                    if (itemPriceStr != "") {

                        val price = convertPriceStrToInt(itemPriceStr)
                        val originalSubTotal = convertPriceStrToInt(viewHolder.subTotalTextView.text.toString())
                        total -= originalSubTotal

                        val subTotal = value * price
                        val subTotalStr = basket.currency + " " + subTotal
                        viewHolder.subTotalTextView.text = subTotalStr

                        total += subTotal
                        itemClickListener.onPriceChange(basket.currency, total)

                    }

                } catch (ignored: Exception) {
                }
            }

            viewHolder.plusImageView.setOnClickListener {

                try {
                    var value = Integer.parseInt(viewHolder.qtyTextView.text.toString())

                    value += 1

                    viewHolder.qtyTextView.text = value.toString()

                    val itemPriceStr = viewHolder.priceTextView.text.toString()
                    if (itemPriceStr != "") {

                        val price = convertPriceStrToInt(itemPriceStr)
                        val originalSubTotal = convertPriceStrToInt(viewHolder.subTotalTextView.text.toString())
                        total -= originalSubTotal

                        val subTotal = value * price
                        val subTotalStr = basket.currency + " " + subTotal
                        viewHolder.subTotalTextView.text = subTotalStr

                        total += subTotal
                        itemClickListener.onPriceChange(basket.currency, total)

                    }

                } catch (ignored: Exception) {
                }
            }

        }
    }

    private fun convertPriceStrToInt(priceStr: String): Int {
        var price = 0
        try {
            val lPriceStr = priceStr.replace(currency, "").replace(" ", "")
            price = Integer.parseInt(lPriceStr)
        } catch (ignored: Exception) {
        }

        return price
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    inner class PlaceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView
        internal var itemNameTextView: TextView
        internal var priceTextView: TextView
        internal var holderCardView: CardView
        internal var deleteImageView: ImageView
        internal var subTotalTextView: TextView
        internal var attributeTextView: TextView
        internal var minusImageView: ImageView
        internal var plusImageView: ImageView
        internal var qtyTextView: TextView

        init {

            itemImageView = view.itemImageView
            itemNameTextView = view.itemNameTextView
            priceTextView = view.priceTextView
            holderCardView = view.holderCardView
            deleteImageView = view.deleteImageView
            subTotalTextView = view.subTotalTextView
            attributeTextView = view.attributeTextView
            minusImageView = view.minusImageView
            plusImageView = view.plusImageView
            qtyTextView = view.qtyTextView

        }
    }
}
