package com.pusatruq.muttabaah.ui.main.sholat.activity

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.rxkprefs.RxkPrefs
import com.afollestad.rxkprefs.rxkPrefs
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.databinding.ActivitySholatBinding
import com.pusatruq.muttabaah.ui.core.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sholat.*
import javax.inject.Inject
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.rxkprefs.Pref
import com.bumptech.glide.util.Util
import com.pusatruq.muttabaah.data.local.Preferences.AppPreferencesDataStore
import com.pusatruq.muttabaah.ui.main.sholat.adapter.SholatAdapter
import com.pusatruq.muttabaah.ui.main.sholat.model.ModelListSholat
import com.pusatruq.muttabaah.ui.main.sholat.viewmodels.SholatViewModel
import com.pusatruq.muttabaah.ui.main.shop.activity.MyBasketActivity
import com.pusatruq.muttabaah.ui.main.shop.adapter.AppECommerceList1Adapter
import com.pusatruq.muttabaah.ui.main.shop.model.ShopItem
import com.pusatruq.muttabaah.util.AppConstants
import com.pusatruq.muttabaah.util.DateUtils
import com.pusatruq.muttabaah.util.Utils
import java.util.*
import kotlin.collections.ArrayList


class SholatActivity @Inject constructor() : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModels: SholatViewModel
    private lateinit var dataBinding: ActivitySholatBinding
    lateinit var myPrefs: RxkPrefs
    private var TglNow: String = ""
    private var TitleApp: String = ""
    private var Parent: String = "1"
    private var myId: Int =0
    private var UpdateRow: Boolean = false
    internal var _listSholat: ArrayList<ModelListSholat.Result> =
        ArrayList<ModelListSholat.Result>()
    internal lateinit var Madapter: SholatAdapter
    internal lateinit var mcontext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sholat)
        viewModels =
            ViewModelProviders.of(this, viewModelFactory).get(SholatViewModel::class.java)
        dataBinding.viewModel = viewModels
        mcontext = this
        myPrefs = rxkPrefs(this, AppConstants.APP_NAME_PREFERENCES)
        if (intent.extras != null) {
            initData()
            initProfile()
            InitUi()
            HandlingUI()
            initDataBindings()
            initActions()
        }
    }

    private fun InitUi() {
        Madapter = SholatAdapter(_listSholat)
        dataBinding.rvSholat.apply {
            layoutManager =
                LinearLayoutManager(this@SholatActivity, LinearLayoutManager.VERTICAL, false)
            adapter = Madapter
        }
    }

    private fun initData() {
        myId = myPrefs.integer(AppPreferencesDataStore.PREFERENCES_ID_USER).get()
        if (intent.extras != null) {
            TglNow = intent.getStringExtra("tgl")
            TitleApp = intent.getStringExtra("title")
            Parent = intent.getStringExtra("parent")
            viewModels.start()
            viewModels.getStatusChecked(
                Parent,
                if (TglNow.isEmpty()) DateUtils.getDateTimeNow() else TglNow,
                myId.toString()
            )
        }

    }

    private fun initDataBindings() {
        // bind adapter to recycler
        dataBinding.rvSholat.adapter = Madapter
    }

    private fun initActions() {

        Madapter.setOnItemClickListener(object : SholatAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View,
                obj: ModelListSholat.Result,
                position: Int,
                isChecked: Boolean
            ) {
                var isStatus = if (isChecked) "1" else "2"
                viewModels.postReport(obj.id_kategori.toString(), myId.toString(), isStatus, "testing")
            }

        })
    }

    private fun HandlingUI() {
        viewModels.message.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
                ShowMessages(this@SholatActivity, o!!.toString())
            }
        })

        viewModels.getListSholat()
            .observe(this@SholatActivity, Observer<ArrayList<ModelListSholat.Result>> {
                if (it != null) {
                    Madapter.setAppList(it)
                }
                swipe.isRefreshing = false
            })


        dataBinding.swipe.setOnRefreshListener {
            Madapter.clearAll()
            swipe.isRefreshing = true
            initData()
        }
    }

    fun initProfile() {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp)
        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }

        toolbar.title = TitleApp

        try {
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000))
        } catch (e: Exception) {
            Log.e("TEAMPS", "Can't set color.")
        }



        try {
            setSupportActionBar(toolbar)
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set support action bar.")
        }

        try {
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true);
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }
        toolbar.setNavigationOnClickListener { view ->
            finish()
        }

    }
}
