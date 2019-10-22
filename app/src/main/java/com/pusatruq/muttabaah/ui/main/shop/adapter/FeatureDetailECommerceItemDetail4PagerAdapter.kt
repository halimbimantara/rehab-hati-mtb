package com.pusatruq.muttabaah.ui.main.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.shop.model.ShopItem
import com.pusatruq.muttabaah.util.Utils

/**
 * Created by Panacea-Soft on 19/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
class FeatureDetailECommerceItemDetail4PagerAdapter(private val context: Context, private val shopItem: ShopItem) : PagerAdapter() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: ShopItem, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun getCount(): Int {
        return shopItem.imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.feature_detail_ecommerce_item_detail_4_pager_item, container, false)
        val pagerItemImageView = view.findViewById<ImageView>(R.id.pagerItemImageView)
        val pagerItemBgImageView = view.findViewById<ImageView>(R.id.pagerItemBgImageView)


        val context = container.context

        val id = Utils.getDrawableInt(context, shopItem.imageList[position].imageName!!)
        Utils.setImageToImageView(context, pagerItemImageView, id)

        Utils.setImageToImageView(context, pagerItemBgImageView, R.drawable.black_top_bottom_alpha_70)

        val vp = container as ViewPager
        vp.addView(view, 0)

        // Listeners

        pagerItemImageView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, shopItem, position) }
        pagerItemBgImageView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, shopItem, position) }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}

