package com.example.kavach.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.kavach.BaseActivity
import com.example.kavach.KavachApplication
import com.example.kavach.R
import com.example.kavach.api.ApiClientBuilder
import com.example.kavach.api.KavachApi
import com.example.kavach.data.InitResponse
import com.example.kavach.ui.result.ResultActivity
import com.example.kavach.ui.survey.SurveyActivity
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : BaseActivity(), Callback<InitResponse> {

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
        callInitAPI()
    }

    private fun callInitAPI() {
        showProgress()
        ApiClientBuilder().build().create(KavachApi::class.java)
            .init()
            .enqueue(this)
    }

    override fun onFailure(call: Call<InitResponse>, t: Throwable) {
        hideProgress()
    }

    private fun showProgress() {
        progressLayoutHome.visibility = View.VISIBLE
        introText.visibility = View.GONE
    }

    private fun hideProgress() {
        progressLayoutHome.visibility = View.GONE
        introText.visibility = View.VISIBLE
    }

    override fun onResponse(call: Call<InitResponse>, response: Response<InitResponse>) {
        hideProgress()
        val initResponse = response.body()

        if (initResponse != null) {
            introText.text = initResponse.introduction_text
            (application as KavachApplication).initResponse = initResponse
        }
    }

}
