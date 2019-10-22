package com.pusatruq.muttabaah.ui.main.accounts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.cardview.widget.CardView
import com.afollestad.rxkprefs.RxkPrefs
import com.afollestad.rxkprefs.rxkPrefs
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.data.AppEndPoint
import com.pusatruq.muttabaah.data.local.Preferences.AppPreferencesDataStore
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.ui.AppHome
import com.pusatruq.muttabaah.ui.core.base.BaseActivity
import com.pusatruq.muttabaah.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_account.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.Executors


class AccountActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private var TAG: String = "Account"
    lateinit var myPrefs: RxkPrefs
    private val RC_SIGN_IN = 9001
    // ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
// Parameter is your Context, like an Activity, uses PreferenceManager#getDefaultSharedPreferences
        myPrefs = rxkPrefs(this, "muttabaah")
// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Configure Google Sign In
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

        val cardView: CardView = findViewById(R.id.BtnLogin);
        cardView.setOnClickListener({
            signIn()
        })

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
//        val googleSignInClient
        progressBar.visibility = VISIBLE
        BtnLogin.visibility = GONE
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    progressBar.visibility = GONE
                    BtnLogin.visibility = VISIBLE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(account_root, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }

                // ...
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            myPrefs.string(AppPreferencesDataStore.PREFERENCES_UNAME)
                .set(user?.displayName.toString())
            myPrefs.string(AppPreferencesDataStore.PREFERENCES_EMAIL).set(user?.email.toString())
            myPrefs.string(AppPreferencesDataStore.PREFERENCES_PICT_PROFILE)
                .set(user?.photoUrl.toString())
            checking(user?.email.toString())
        } else {
            progressBar.visibility = GONE
            BtnLogin.visibility = VISIBLE
        }
    }

    fun checking(email: String) {
        AndroidNetworking.post(AppEndPoint.POST_CHECK_USER)
            .setExecutor(Executors.newSingleThreadExecutor())
            .setPriority(Priority.HIGH)
            .addBodyParameter("email", email)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    if (response!!.getBoolean("status")) {
                        //masuk menu
                        var models: JSONObject = response.getJSONObject("result")
                        myPrefs.integer(AppPreferencesDataStore.PREFERENCES_ID_USER)
                            .set(models.getInt("id"))
                        myPrefs.string(AppPreferencesDataStore.PREFERENCES_UNAME)
                            .set(models.getString("username"))
                        myPrefs.string(AppPreferencesDataStore.PREFERENCES_EMAIL)
                            .set(models.getString("email"))
                        myPrefs.string(AppPreferencesDataStore.PREFERENCES_GENDER)
                            .set(models.getString("gender"))
                        myPrefs.string(AppPreferencesDataStore.PREFERENCES_PHONE)
                            .set(models.getString("no_hp"))
                        myPrefs.string(AppPreferencesDataStore.PREFERENCES_FULL_NAME)
                            .set(models.getString("full_name"))
//                        myPrefs.string("email").set(email)
                        openActivity(
                            this@AccountActivity,
                            Intent(this@AccountActivity, AppHome::class.java)
                        )
                        finish()
                    } else {
                        //register
                        val intentRegister =
                            Intent(this@AccountActivity, SignUpActivity::class.java)
                        intentRegister.putExtra("email", email)
                        startActivity(intentRegister)
                        finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    ShowMessages(this@AccountActivity, anError!!.errorBody.toString())
                }

            })
    }
}
