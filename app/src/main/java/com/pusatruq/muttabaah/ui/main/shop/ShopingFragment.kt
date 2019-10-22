package com.pusatruq.muttabaah.ui.main.shop

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.pusatruq.muttabaah.data.remote.shooping.ShopItemRepository
import com.pusatruq.muttabaah.di.ActivityScoped
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.databinding.FragmentShopBinding
import com.pusatruq.muttabaah.ui.core.base.BaseFragment
import com.pusatruq.muttabaah.ui.main.MainActivity
import com.pusatruq.muttabaah.ui.main.shop.activity.DetailActivityShop
import com.pusatruq.muttabaah.ui.main.shop.activity.MyBasketActivity
import com.pusatruq.muttabaah.ui.main.shop.adapter.AppECommerceList1Adapter
import com.pusatruq.muttabaah.ui.main.shop.model.ShopItem
import javax.inject.Inject


/**
 * Created by cuongpm on 11/29/18.
 */

@ActivityScoped
class ShopingFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var shopViewModel: ShopViewModel

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var dataBinding: FragmentShopBinding

    internal lateinit var shopItemList: List<ShopItem>
    internal lateinit var adapter: AppECommerceList1Adapter
    internal lateinit var mcontext: Context


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopViewModel = ViewModelProviders.of(this, viewModelFactory).get(ShopViewModel::class.java)
//        newsAdapter = NewsAdapter(ArrayList(0), shopViewModel)

        dataBinding = FragmentShopBinding.inflate(inflater, container, false).apply {
            this.viewModel = shopViewModel
        }

        setHasOptionsMenu(true)
        initData()
        initUI()

        initDataBindings()

        initActions()
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mcontext = requireContext()
//        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        scoreViewModel.start()
//        handleUIEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shopViewModel.stop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_basket, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {

        } else {
            Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initData() {
        // get place list
        shopItemList = ShopItemRepository.womenShopItemList
    }

    private fun initUI() {
        inittoolbars()

        // get list adapter
        adapter = AppECommerceList1Adapter(shopItemList)

        // get recycler view
        val mLayoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerView.layoutManager = mLayoutManager
        dataBinding.recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun initDataBindings() {
        // bind adapter to recycler
        dataBinding.recyclerView.adapter = adapter
    }

    private fun initActions() {
        dataBinding.icoMycart.setOnClickListener({
            startActivity(Intent(requireContext(), MyBasketActivity::class.java))
        })

        adapter.setOnItemClickListener(object : AppECommerceList1Adapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: ShopItem, position: Int) {
//                Toast.makeText(requireContext(), "Selected " + obj.name, Toast.LENGTH_SHORT).show()
            }

            override fun onAddToCartClick(view: View, obj: ShopItem, position: Int) {
//                Toast.makeText(requireContext(), "Clicked add to cart.", Toast.LENGTH_SHORT).show()
            }

            override fun onMenuClick(view: View, obj: ShopItem, position: Int, Name: String) {
                val detail = Intent(mcontext, DetailActivityShop::class.java)
                detail.putExtra("title", Name)
                detail.putExtra("image", obj.imageName)
                startActivity(detail)
            }
        })
    }

    //region Init toolbars
    private fun inittoolbars() {

//        (activity as MainActivity).delegate.setSupportActionBar(dataBinding.toolbars)
//        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        (activity as MainActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        dataBinding.toolbars.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (dataBinding.toolbars.navigationIcon != null) {
            dataBinding.toolbars.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }

        dataBinding.toolbars.title = "Food Delivery"

        try {
            dataBinding.toolbars.setTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.md_white_1000
                )
            )
        } catch (e: Exception) {
            Log.e("TEAMPS", "Can't set color.")
        }

//        try {
//            (activity as AppCompatActivity).setSupportActionBar(dataBinding.toolbars)
//        } catch (e: Exception) {
//            Log.e("TEAMPS", "Error in set support action bar.")
//        }


    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).supportActionBar!!.show()
    }

}