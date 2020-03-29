package com.example.kavach.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kavach.R
import com.example.kavach.ui.survey.SurveyActivity
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        startSurvey.setOnClickListener {
            startActivity(Intent(this, SurveyActivity::class.java))
        }
        app_bar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset.toFloat() / appBarLayout.totalScrollRange.toFloat()) >= 0.5) {
                startSurvey.visibility = View.GONE
            } else {
                startSurvey.visibility = View.VISIBLE
            }
        })
    }

}
