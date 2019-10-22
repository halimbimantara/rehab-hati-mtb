package com.pusatruq.muttabaah.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.home.model.News
import com.pusatruq.muttabaah.util.Utils

/**
 * Created by Panacea-Soft on 12/8/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
class FeatureDashboardNewsDashboard2CoverFlowPagerAdapter(private val context: Context?, private val newsList: List<News>) : PagerAdapter() {
    private lateinit var itemClickListener: FeatureDashboardNewsDashboard2CoverFlowPagerAdapter.OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: News, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: FeatureDashboardNewsDashboard2CoverFlowPagerAdapter.OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun getCount(): Int {
        return newsList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.feature_dashboard_news_dashboard_2_cover_flow_pager_item, container, false)

        val cardImageView = view.findViewById<ImageView>(R.id.cardImageView)
        val newsTitleTextView = view.findViewById<TextView>(R.id.newsTitleTextView)

        val news = newsList[position]

        newsTitleTextView.text = news.title

        val context = cardImageView.context

        val id = Utils.getDrawableInt(context, news.newsImage)
        Utils.setImageToImageView(context, cardImageView, id)

        val vp = container as ViewPager
        vp.addView(view, 0)

        // Listeners
        cardImageView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, newsList[position], position) }
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}
