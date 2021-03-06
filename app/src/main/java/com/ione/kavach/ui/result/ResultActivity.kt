package com.ione.kavach.ui.result

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ione.kavach.BaseActivity
import com.ione.kavach.KavachApplication
import com.ione.kavach.R
import com.ione.kavach.data.Results
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.content_result.*

class ResultActivity : BaseActivity() {

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
        viewModel.onViewCreated(assets, (application as KavachApplication).initResponse)
        viewModel.resultLiveData.observe(this, Observer {
            renderUI(it)
        })
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun renderUI(results: Results?) {
        val valuesList = results?.value
        valuesList?.forEach {
            if (resultScore >= it.min_score && resultScore <= it.max_score) {
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
                adviceTitle.text = it.instruction.title
                adviceDescription.text = it.instruction.description
            }
        }
        preventionTitle.text = results?.prevention?.title
        preventionDescription.text = results?.prevention?.description
    }
}
