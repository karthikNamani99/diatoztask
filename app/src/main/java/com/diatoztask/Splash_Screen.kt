package com.diatoztask

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.diatoztask.LoginPage

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)
        Handler().postDelayed({
            val i = Intent(this@Splash_Screen, LoginPage::class.java)
            startActivity(i)

            // close this activity
            finish()
        }, 5 * 1000.toLong())
    }
}