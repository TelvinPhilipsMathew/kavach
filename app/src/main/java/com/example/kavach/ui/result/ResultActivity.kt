package com.example.kavach.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kavach.R
import com.example.kavach.data.Results
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.content_result.*

class ResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT = "RESULT"
    }

    private var resultScore: Int = 0
    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        resultScore = intent.getIntExtra(RESULT, 0)

        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)
        viewModel.onViewCreated(assets)
        viewModel.resultLiveData.observe(this, Observer {
            renderUI(it)
        })
        btnClose.setOnClickListener {
            finish()
        }

    }

    private fun renderUI(results: List<Results>?) {

        results?.forEach {
            if (resultScore >= it.minScore && resultScore <= it.maxScore) {
                riskDescription.text = it.description
                riskTitle.text = it.risk
                when (it.risk.toLowerCase()) {
                    "low" -> {
                        riskTitle.setTextColor(ContextCompat.getColor(this, R.color.green))
                    }
                    "medium" -> {
                        riskTitle.setTextColor(ContextCompat.getColor(this, R.color.orange))
                    }
                }
                adviceDescription.text = adviceDescription.text.toString().format(it.description)
            }
        }
    }
}
