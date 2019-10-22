package com.pusatruq.muttabaah.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.pusatruq.muttabaah.di.ActivityScoped
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.databinding.FragmentHomeBinding
import com.pusatruq.muttabaah.ui.core.base.BaseFragment
import com.pusatruq.muttabaah.ui.main.comment.CommentActivity
import com.pusatruq.muttabaah.ui.main.comment.CommentFragment.Companion.NEWS_DATA
import com.pusatruq.muttabaah.ui.main.home.adapter.FeatureDashboardNewsDashboard2CoverFlowPagerAdapter
import com.pusatruq.muttabaah.ui.main.home.adapter.FeatureTimelineAdapter
import com.pusatruq.muttabaah.ui.main.home.adapter.MenuAdapter
import com.pusatruq.muttabaah.ui.main.home.model.News
import com.pusatruq.muttabaah.ui.main.home.model.SliderHomeRepository
import com.pusatruq.muttabaah.ui.main.sholat.activity.SholatActivity
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import com.pusatruq.muttabaah.util.DateUtils
import kotlinx.android.synthetic.main.activity_sholat.*
import javax.inject.Inject


/**
 * Created by cuongpm on 11/29/18.
 */

@ActivityScoped
class HomeFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var dataBinding: FragmentHomeBinding
    private var TglNow: String = ""
    private lateinit var appNewsHome1CoverFlowPagerAdapter: FeatureDashboardNewsDashboard2CoverFlowPagerAdapter
    private lateinit var appMenuadapter: MenuAdapter
    internal var _listMenu: ArrayList<MenuEntity> =
        ArrayList<MenuEntity>()
    private lateinit var imageViewPager: ViewPager
    private lateinit var pager_indicator: LinearLayout
    private lateinit var dots: Array<ImageView?>
    private var dotsCount: Int = 0
    // data
    internal lateinit var menuList: List<News>
    // RecyclerView
    internal lateinit var itemsRecyclerView: RecyclerView
    private lateinit var featureTimelineGeneralTimeline2Adapter: FeatureTimelineAdapter

    internal var noOfColumn = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        dataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            this.viewModel = homeViewModel
        }
        initData()
        initUI(dataBinding.root)
        initDataBindings()
        handleUIEvent()
        initActions()
        return dataBinding.root
    }

    private fun initData() {
        homeViewModel.getSubmenu()
        menuList = SliderHomeRepository.newsList
        TglNow = DateUtils.getDateTimeNow()
        appMenuadapter = MenuAdapter(_listMenu)
        dataBinding.tvTitleTgl.setText(TglNow)
        val editorPickedNewsList = SliderHomeRepository.newsList
        appNewsHome1CoverFlowPagerAdapter =
            FeatureDashboardNewsDashboard2CoverFlowPagerAdapter(context, editorPickedNewsList)
    }

    private fun initUI(view: View) {
        imageViewPager = view.findViewById(R.id.imageViewPager)
        pager_indicator = view.findViewById(R.id.viewPagerCountDots)
        dataBinding.rvListSubmenu.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = appMenuadapter
        }
    }

    private fun initDataBindings() {
        imageViewPager.adapter = appNewsHome1CoverFlowPagerAdapter
        setupSliderPagination()
        dataBinding.rvListSubmenu.adapter = appMenuadapter
    }

    private fun initActions() {
        appMenuadapter.setOnItemClickListener(object : MenuAdapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: MenuEntity, position: Int) {
                val intent = Intent(context, SholatActivity::class.java)
                intent.putExtra("title", obj.title)
                intent.putExtra("tgl", TglNow)
                intent.putExtra("parent", Integer.toString(obj.idKategori))
                startActivity(intent)
            }
        })
        imageViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {


                setupSliderPagination()


                for (i in 0 until dotsCount) {
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.nonselecteditem_dot
                        )
                    )
                }

                dots[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.selecteditem_dot
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        appNewsHome1CoverFlowPagerAdapter.setOnItemClickListener(object :
            FeatureDashboardNewsDashboard2CoverFlowPagerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: News, position: Int) {
                Toast.makeText(context, "Selected : " + obj.title, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.start()

//        handleUIEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.stop()
    }

    private fun handleUIEvent() {
//        homeViewModel.onNewsOpenEvent.observe(this, Observer { news ->
//            news?.let {
//                val intent = Intent(context, CommentActivity::class.java)
//                intent.putExtra(NEWS_DATA, Gson().toJson(it))
//                startActivity(intent)
//            }
//        })
        homeViewModel.getListSubmenu().observe(requireActivity(), Observer<ArrayList<MenuEntity>> {
            if (it != null) {
                appMenuadapter.setAppList(it)
            }
//            swipe.isRefreshing = false
        })
    }

    fun setupSliderPagination() {

        dotsCount = appNewsHome1CoverFlowPagerAdapter.count

        if (dotsCount > 0) {

            dots = arrayOfNulls(dotsCount)


            if (pager_indicator.childCount > 0) {
                pager_indicator.removeAllViewsInLayout()

            }

            for (i in 0 until dotsCount) {
                dots[i] = ImageView(context)
                dots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.nonselecteditem_dot
                    )
                )

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.setMargins(4, 0, 4, 0)

                pager_indicator.addView(dots[i], params)
            }

            dots[0]?.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.selecteditem_dot
                )
            )

        }

    }


}