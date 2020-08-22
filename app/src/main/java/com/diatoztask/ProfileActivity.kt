package com.diatoztask

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.diatoztask.LoginPage
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

class ProfileActivity : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    var logoutBtn: Button? = null
    var userName: TextView? = null
    var userEmail: TextView? = null
    var userId: TextView? = null
    var profileImage: ImageView? = null
    private var googleApiClient: GoogleApiClient? = null
    private var gso: GoogleSignInOptions? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_main, container, false)
        logoutBtn = view.findViewById<View>(R.id.logoutBtn) as Button
        userName = view.findViewById<View>(R.id.name) as TextView
        userEmail = view.findViewById<View>(R.id.email) as TextView
        //        userId=(TextView)view.findViewById(R.id.userId);
        profileImage = view.findViewById<View>(R.id.profileImage) as ImageView
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(activity!!)
                .enableAutoManage(activity!!, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso!!)
                .build()
        logoutBtn!!.setOnClickListener {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status ->
                if (status.isSuccess) {
                    gotoMainActivity()
                } else {
                    Toast.makeText(activity, "Session not close", Toast.LENGTH_LONG).show()
                }
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        val opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }

    override fun onPause() {
        super.onPause()
        googleApiClient!!.stopAutoManage(activity!!)
        googleApiClient!!.disconnect()
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            userName!!.text = account!!.displayName
            userEmail!!.text = account.email
            //            userId.setText(account.getId());
            try {
                Glide.with(activity).load(account.photoUrl).into(profileImage)
            } catch (e: NullPointerException) {
                Toast.makeText(activity, "image not found", Toast.LENGTH_LONG).show()
            }
        } else {
            gotoMainActivity()
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(activity, LoginPage::class.java)
        startActivity(intent)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity!!.findViewById<View>(R.id.toolbar_main) as Toolbar
        val fragmentTitle = toolbar.findViewById<TextView>(R.id.title_nav)
        fragmentTitle.text = "Profile Page"
        //you can set the title for your toolbar here for different fragments different titles
    }
}