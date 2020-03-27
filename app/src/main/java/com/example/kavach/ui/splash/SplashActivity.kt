package com.example.kavach.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.kavach.R
import com.example.kavach.ui.home.HomeActivity


class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mainIntent = Intent(this, HomeActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}
