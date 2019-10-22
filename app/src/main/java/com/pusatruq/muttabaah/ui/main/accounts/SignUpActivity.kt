package com.pusatruq.muttabaah.ui.main.accounts

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.afollestad.rxkprefs.Pref
import com.afollestad.rxkprefs.RxkPrefs
import com.afollestad.rxkprefs.rxkPrefs
import com.jaredrummler.materialspinner.MaterialSpinner
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.data.local.Preferences.AppPreferencesDataStore
import com.pusatruq.muttabaah.databinding.ActivitySignupBinding
import com.pusatruq.muttabaah.ui.AppHome
import com.pusatruq.muttabaah.ui.core.base.BaseActivity
import com.pusatruq.muttabaah.ui.main.MainActivity
import com.pusatruq.muttabaah.ui.main.accounts.model.RegisterPost
import com.pusatruq.muttabaah.ui.main.accounts.viewmodels.SignUpViewModel
import javax.inject.Inject


class SignUpActivity @Inject constructor() : BaseActivity() {
    private var TAG: String = "SignUp"
    lateinit var myPrefs: RxkPrefs
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModels: SignUpViewModel
    private lateinit var dataBinding: ActivitySignupBinding
    private lateinit var GenderSpin: MaterialSpinner
    //basik folder
    private var City: String = "Bandung"
    private var Gender = ""
    private var Email: String = ""
    private var Fullname: String = ""
    private var Addres: String = ""
    private var No_hp: String = ""
    // ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModels =
            ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java!!)
        dataBinding.viewModel = viewModels
        myPrefs = rxkPrefs(this, "muttabaah")

        initDataBinding()
        InitData()
        InitAction()
        HandlingObserveable()
        GenderSpin.setOnItemSelectedListener({ view, position, id, item ->
            Gender = item.toString()
        })
    }

    fun initDataBinding() {
        GenderSpin = dataBinding.SpinGender
        GenderSpin.setItems("Pilih. . . ", "Laki-Laki", "Perempuan")
    }

    fun InitData() {
        val myEmail: Pref<String> = myPrefs.string("email")
        dataBinding.etEmail.setText(myEmail.get())
        dataBinding.etEmail.isEnabled = false
    }

    fun InitAction() {
        Email = dataBinding.etEmail.text.toString()
        dataBinding.registerButton.setOnClickListener({
            No_hp = dataBinding.etNoHp.text.toString()
            Fullname = dataBinding.EtFullname.text.toString()
            Addres = dataBinding.etAddress.text.toString()

            if (No_hp.isEmpty()) {
                dataBinding.etNoHp.setError(resources.getString(R.string.warning_info_pls_datacanempty))
            } else if (Fullname.isEmpty()) {
                dataBinding.EtFullname.setError(resources.getString(R.string.warning_info_pls_datacanempty))
            } else if (Addres.isEmpty()) {
                dataBinding.etAddress.setError(resources.getString(R.string.warning_info_pls_datacanempty))
            } else {
                var inUname = dataBinding.Etusername.text.toString()
                val postModel = RegisterPost(
                    0,
                    inUname,
                    Gender,
                    Email,
                    Fullname,
                    Addres,
                    City,
                    No_hp
                )

                viewModels.postReport(postModel)
            }
        })


    }

    fun HandlingObserveable() {
        viewModels.message.observe(this, object : Observer<Any> {
            override fun onChanged(o: Any?) {
//                if (o!!.equals(AppConstants.MESSAGE_INFO_SUCCESS)){
//
//                }else{
//
//                }
                ShowMessages(this@SignUpActivity, o!!.toString())
            }
        })

        viewModels.modelRegister.observe(this, object : Observer<RegisterPost> {
            override fun onChanged(t: RegisterPost?) {
                if (t != null) {
                    //
                    myPrefs.integer(AppPreferencesDataStore.PREFERENCES_ID_USER).set(t.IdUser)
                    myPrefs.string(AppPreferencesDataStore.PREFERENCES_UNAME).set(t.UserName)
                    myPrefs.string(AppPreferencesDataStore.PREFERENCES_EMAIL).set(t.Email)
                    myPrefs.string(AppPreferencesDataStore.PREFERENCES_GENDER).set(t.Gender)
                    myPrefs.string(AppPreferencesDataStore.PREFERENCES_PHONE).set(t.No_hp)
                    myPrefs.string(AppPreferencesDataStore.PREFERENCES_FULL_NAME).set(t.FullName)
                    openActivity(
                        this@SignUpActivity,
                        Intent(this@SignUpActivity, AppHome::class.java)
                    )
                    finish()
                }
            }

        })
    }
}
