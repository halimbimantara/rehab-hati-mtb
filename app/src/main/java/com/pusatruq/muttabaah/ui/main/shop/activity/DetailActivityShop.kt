package com.pusatruq.muttabaah.ui.main.shop.activity

import android.content.Intent
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.pusatruq.muttabaah.data.remote.shooping.ShopItemRepository
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.main.shop.adapter.FeatureDetailECommerceItemDetail4PagerAdapter
import com.pusatruq.muttabaah.ui.main.shop.model.ShopItem
import com.pusatruq.muttabaah.util.Utils
import kotlinx.android.synthetic.main.feature_detail_ecommerce_item_detail_4_activity.*
import java.util.*

class DetailActivityShop : AppCompatActivity() {

    // data variables
    private lateinit var shopItem: ShopItem
//    private lateinit var userReviewList: List<UserReview>

    // ui lateinit variables
    private lateinit var featureECommerceDetail1PagerAdapter: FeatureDetailECommerceItemDetail4PagerAdapter
    //    private lateinit var adapter: FeatureDetailECommerceItemDetail4ReviewAdapter
    private var ImgUrl: String = "";
    private var TitleItem: String = "";
    //    private lateinit var recyclerView: RecyclerView
//
//    private lateinit var addressTextView: TextView
//    private lateinit var phoneTextView: TextView
//    private lateinit var websiteTextView: TextView
//    private lateinit var emailTextView: TextView
//
//    private lateinit var descTextView: TextView
//    private lateinit var reviewCountTextView: TextView
//    private lateinit var priceTextView: TextView
//    private lateinit var originalPriceTextView: TextView
//    private lateinit var itemRatingBar: RatingBar
//
//    private lateinit var writeReviewTextView: TextView
//    private lateinit var viewAllReviewButton: Button
//    private lateinit var addToBasketButton: Button
//
//    private lateinit var inquiryTextView: TextView
//
    internal lateinit var sizeList: List<String>
    internal lateinit var colorList: List<String>
    internal lateinit var sizeArrayAdapter: ArrayAdapter<String>
    internal lateinit var colorArrayAdapter: ArrayAdapter<String>
//
//    internal lateinit var sizeSpinner: Spinner
//    internal lateinit var colorSpinner: Spinner
//
//    internal lateinit var detailImageView: ImageView
//    internal lateinit var detailShadowImageView: ImageView
//
//    internal lateinit var minusImageView: ImageView
//    internal lateinit var plusImageView: ImageView
//
//    internal lateinit var qtyEditText: EditText
//
//    internal lateinit var favFab: FloatingActionButton
//
//    internal lateinit var toolbarLayout: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feature_detail_ecommerce_item_detail_4_activity)

        initData()

        initUI()

        initDataBindings()

        initActions()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.action_share) {
            Toast.makeText(this, "Clicked Share.", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.action_basket) {
            Toast.makeText(this, "Clicked Basket.", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share_basket, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initData() {
        TitleItem=intent.getStringExtra("title")
        ImgUrl=intent.getStringExtra("image")
        // get shopItem detail
        shopItem = ShopItemRepository.womenShopItem

        featureECommerceDetail1PagerAdapter =
            FeatureDetailECommerceItemDetail4PagerAdapter(this, shopItem)

        // get place list
//        userReviewList = UserReviewRepository.userReviewList

        sizeList = getSizeList()
        colorList = getColorList()


    }

    private fun initUI() {

        initToolbar()


        // get list adapter
//        adapter = FeatureDetailECommerceItemDetail4ReviewAdapter(userReviewList)

        // get recycler view
        val mLayoutManager = LinearLayoutManager(applicationContext)
//        reviewRecyclerView.layoutManager = mLayoutManager
//        reviewRecyclerView.itemAnimator = DefaultItemAnimator()
//        reviewRecyclerView.isNestedScrollingEnabled = false

        sizeArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sizeList)

        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)




        colorArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colorList)

        colorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


    }

    private fun initDataBindings() {

        descTextView.text = shopItem.description
        reviewCountTextView.text = shopItem.ratingCount
        itemRatingBar.rating = java.lang.Float.parseFloat(shopItem.totalRating)

        val priceStr = "Rp. " + shopItem.price
        val originalPriceStr = "Rp. " + shopItem.originalPrice
        priceTextView.text = priceStr
        originalPriceTextView.text = originalPriceStr
        originalPriceTextView.paintFlags =
            originalPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        val id = Utils.getDrawableInt(this, ImgUrl)
        Utils.setImageToImageView(this, detailImageView, id)

        Utils.setImageToImageView(this, detailShadowImageView, R.drawable.black_top_bottom_alpha_70)

        collapsingToolbar.title = shopItem.shop.shopName

        // bind adapter to recycler
//        reviewRecyclerView.adapter = adapter
    }


    private fun getSizeList(): List<String> {
        val sizeList = ArrayList<String>()

        sizeList.add("Please select size.")
        sizeList.add("Small ( S )")
        sizeList.add("Medium ( M )")
        sizeList.add("Large ( L )")
        sizeList.add("Extra Large ( XL )")

        return sizeList
    }

    private fun getColorList(): List<String> {
        val sizeList = ArrayList<String>()

        sizeList.add("Please select color.")
        sizeList.add("Green")
        sizeList.add("White")
        sizeList.add("Red")
        sizeList.add("Dark Grey")

        return sizeList
    }

    private fun initActions() {

        featureECommerceDetail1PagerAdapter.setOnItemClickListener(object :
            FeatureDetailECommerceItemDetail4PagerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: ShopItem, position: Int) {
                Toast.makeText(
                    this@DetailActivityShop,
                    "Selected : " + obj.imageList.get(position).imageName,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        addToBasketButton.setOnClickListener {
            startActivity(Intent(this, MyBasketActivity::class.java))
        }


        favFloatingActionButton.setOnClickListener {
            Toast.makeText(
                this,
                "Clicked Favourite.",
                Toast.LENGTH_SHORT
            ).show()
        }

        minusImageView.setOnClickListener {

            try {
                var value = Integer.parseInt(qtyEditText.text.toString())

                if (value > 0) {
                    value -= 1
                }

                qtyEditText.setText(value.toString())

            } catch (ignored: Exception) {
            }
        }

        plusImageView.setOnClickListener {

            try {
                var value = Integer.parseInt(qtyEditText.text.toString())

                value += 1

                qtyEditText.setText(value.toString())

            } catch (ignored: Exception) {
            }
        }
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }

        toolbar.title = TitleItem

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
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }


        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)

        if (Utils.isRTL) {
            collapsingToolbarLayout.collapsedTitleGravity = Gravity.END
            collapsingToolbarLayout.expandedTitleGravity = Gravity.END or Gravity.BOTTOM
        } else {
            collapsingToolbarLayout.collapsedTitleGravity = Gravity.START
            collapsingToolbarLayout.expandedTitleGravity = Gravity.START or Gravity.BOTTOM
        }

    }
}
