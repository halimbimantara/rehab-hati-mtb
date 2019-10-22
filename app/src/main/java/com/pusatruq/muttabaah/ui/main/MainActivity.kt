package com.pusatruq.muttabaah.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.rxkprefs.Pref
import com.afollestad.rxkprefs.RxkPrefs
import com.afollestad.rxkprefs.rxkPrefs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.pusatruq.muttabaah.Grafik
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.databinding.MainHomeBinding
import com.pusatruq.muttabaah.databinding.NavHeaderDrawerBinding
import com.pusatruq.muttabaah.ui.core.base.BaseActivity
import com.pusatruq.muttabaah.ui.main.accounts.AccountActivity
import com.pusatruq.muttabaah.ui.main.accounts.ProfileActivity
import com.pusatruq.muttabaah.ui.main.home.HomeFragment
import com.pusatruq.muttabaah.ui.main.maparea.MapsAreaFragment
import com.pusatruq.muttabaah.ui.main.scorring.ScorringFragment
import com.pusatruq.muttabaah.ui.main.shop.ShopingFragment
import com.pusatruq.muttabaah.ui.main.shop.activity.OrderHistoryActivity
import com.pusatruq.muttabaah.util.ext.addFragment
import kotlinx.android.synthetic.main.main_home.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var myPrefs: RxkPrefs
    private var mDrawer: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private lateinit var toggle: ActionBarDrawerToggle
    private var mBottomNav: BottomNavigationView? = null
    private lateinit var dataBinding: MainHomeBinding
    private var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPrefs = rxkPrefs(this, "muttabaah")
        dataBinding = DataBindingUtil.setContentView(this, R.layout.main_home)
        bottomNavigationViewWithIcon.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
        addFragment(R.id.content_frame, ::HomeFragment)
        initDrawer()

    }

    private fun initDrawer() {
        mDrawer = dataBinding.drawerView
        mToolbar = dataBinding.toolbar
        mNavigationView = dataBinding.navigationView
        mBottomNav = dataBinding.bottomNavigationViewWithIcon

        setSupportActionBar(mToolbar)
        toggle = ActionBarDrawerToggle(
            this,
            mDrawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        dataBinding.drawerView.addDrawerListener(toggle)
        dataBinding.navigationView.setNavigationItemSelectedListener(this)
        toggle.setDrawerIndicatorEnabled(false)
        dataBinding.drawerView.addDrawerListener(toggle)
        toggle.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_menu_white_24dp))
        toggle.setToolbarNavigationClickListener({
            dataBinding.drawerView.openDrawer(
                GravityCompat.START
            )
        })
        toggle.syncState()
        setnav()
        //Setting the actionbarToggle to drawer layout
    }

    private fun setnav() {
        var Naveader: NavHeaderDrawerBinding
        Naveader = inflate(
            layoutInflater,
            R.layout.nav_header_drawer, dataBinding.navigationView, false
        )

        val myUsername: Pref<String> = myPrefs.string("username")
        val myEmail: Pref<String> = myPrefs.string("email")
        val photoPic: Pref<String> = myPrefs.string("photo_pic")
        Naveader.tvName.setText(myUsername.get())
        Naveader.tvEmail.setText(myEmail.get())

        Glide.with(this)
            .load(photoPic.get())
            .circleCrop()
            .into(Naveader.ivProfilePic)

        dataBinding.navigationView.addHeaderView(Naveader.getRoot())
    }

    fun ClearDrawer() {
        dataBinding.navigationView.clearFocus()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_bottom_home -> {

                    ClearDrawer()
                    val fragment = HomeFragment()
                    addFragmentIn(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_bottom_score -> {

                    ClearDrawer()
                    val fragment = ScorringFragment()
                    addFragmentIn(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun addFragmentIn(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content_frame, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_drawer_home) {
            Toast.makeText(this, "Clicked Home.", Toast.LENGTH_SHORT).show()
        } else if (id == R.id.nav_drawer_profile) {
            openActivity(this, Intent(this, ProfileActivity::class.java))
        } else if (id == R.id.nav_drawer_shop) {
            openActivity(this, Intent(this, OrderHistoryActivity::class.java))
        } else if (id == R.id.nav_drawer_sharing) {
            Share()
        } else if (id == R.id.nav_drawer_maps) {
            openActivity(this, Intent(this, Grafik::class.java))

        } else if (id == R.id.nav_drawer_score) {
//            val fragment = ScorringFragment()
//            addFragmentIn(fragment)
            val view: View = bottomNavigationViewWithIcon.findViewById(R.id.nav_bottom_score)
            view.performClick()
        } else if (id == R.id.nav_drawer_shop) {
            val fragment = ShopingFragment()
            addFragmentIn(fragment)
        } else if (id == R.id.nav_bottom_home) {
            val fragment = HomeFragment()
            addFragmentIn(fragment)
        } else if (id == R.id.nav_drawer_exit) {
            //dialog
            Logout()

        }
        dataBinding.drawerView.closeDrawer(GravityCompat.START)
        return true
    }

    fun Share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "mtb")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    var k = 0

    private fun Logout() {
        MaterialDialog(this).show {
            cornerRadius(7f)
            cancelable(false)
            title(text = "Warning...")
            message(text = "Are you sure to Logout ?")
            positiveButton(R.string.agree) { dialog ->
                // Do something
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity, AccountActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            negativeButton(R.string.disagree) { dialog ->
                // Do something
                dialog.dismiss()
            }
        }
    }

}