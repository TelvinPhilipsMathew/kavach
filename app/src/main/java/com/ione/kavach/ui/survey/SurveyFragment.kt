package com.ione.kavach.ui.survey

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ione.kavach.KavachApplication
import com.ione.kavach.R
import com.ione.kavach.ui.result.ResultActivity
import kotlinx.android.synthetic.main.phone_dialog.view.*
import kotlinx.android.synthetic.main.survey_fragment.*


class SurveyFragment : Fragment(), ClickInteractionListener {

    companion object {
        fun newInstance() = SurveyFragment()
    }

    private lateinit var viewModel: SurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.survey_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(SurveyViewModel::class.java)
        viewModel.setInitResponse((activity?.application as KavachApplication).initResponse)
        observeCurrentIndex()
        setClickListeners()
    }

    private fun observeCurrentIndex() {
        viewModel.currentIndex.observe(activity!!, Observer {
            renderUi(it)
            if (it == viewModel.totalNumberOfQuestions - 1) {
                submitButton.visibility = View.VISIBLE
                nextButton.visibility = View.GONE
                observeSubmitForm()
            } else {
                submitButton.visibility = View.GONE
                nextButton.visibility = View.VISIBLE
            }
            handleNavigationButton()
            handleBackgroundImage(it)
        })
    }

    private fun handleBackgroundImage(index: Int?) {
        backgroundImage.setImageDrawable(
            when (index) {
                0 -> ContextCompat.getDrawable(
                    context!!, R.drawable.question_1
                )
                1 -> ContextCompat.getDrawable(
                    context!!, R.drawable.question_2
                )
                2 -> ContextCompat.getDrawable(
                    context!!, R.drawable.question_3
                )
                3 -> ContextCompat.getDrawable(
                    context!!, R.drawable.question_4
                )
                4 -> ContextCompat.getDrawable(
                    context!!, R.drawable.question_5
                )
                else -> null
            }
        )
    }

    private fun setClickListeners() {
        backButton.setOnClickListener {
            viewModel.decrementCurrentIndex()
        }
        nextButton.setOnClickListener {
            viewModel.incrementCurrentIndex()
        }
        submitButton.setOnClickListener {
            val calculateScore = viewModel.calculateScore()
            val highestMinScore =
                (activity!!.application as KavachApplication).initResponse?.results?.value?.last()?.min_score
                    ?: 0
            if (calculateScore > highestMinScore) {
                showPhoneDialog()
            } else {
                showProgress()
                viewModel.submitSurvey("", context)
            }
        }
    }

    private fun showProgress() {
        progressLayout.visibility = View.VISIBLE
    }


    private fun observeSubmitForm() {
        viewModel.result.observe(activity!!, Observer {
            hideProgress()
            val intent = Intent(activity!!, ResultActivity::class.java)
            intent.putExtra(ResultActivity.RESULT, it)
            activity?.startActivity(intent)
            activity?.finish()
            viewModel.result.removeObservers(activity!!)
        })
    }

    private fun showPhoneDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity!!)
        val dialogView: View =
            LayoutInflater.from(activity!!).inflate(R.layout.phone_dialog, null, false)
        builder.setView(dialogView)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()

        dialogView.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (dialogView.phoneEditText.text.toString().length < 10) {
                    dialogView.submitButton.alpha = 0.5f
                    dialogView.submitButton.isEnabled = false
                } else {
                    dialogView.submitButton.alpha = 1f
                    dialogView.submitButton.isEnabled = true
                }
            }
        })
        dialogView.submitButton.setOnClickListener {
            showProgress()
            alertDialog.dismiss()
            viewModel.submitSurvey(dialogView.phoneEditText.text.toString(), context)
        }
    }

    private fun hideProgress() {
        progressLayout.visibility = View.GONE
    }

    private fun renderUi(currentIndex: Int) {
        val currentQuestion = viewModel.questions[currentIndex]

        questionTextView.text = currentQuestion.title

        answersRecyclerView.layoutManager =
            if (!currentQuestion.isMultipleSelectionAllowed)
                LinearLayoutManager(activity)
            else
                GridLayoutManager(activity, 2)
        answersRecyclerView.adapter = AnswersRecyclerViewAdapter(this, currentQuestion)
    }

    override fun notifyAdapter() {
        handleNavigationButton()
        answersRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun handleNavigationButton() {
        if (viewModel.questions[viewModel.currentIndex.value!!].selectedOptions.isNotEmpty()) {
            nextButton.isEnabled = true
            nextButton.alpha = 1f
        } else {
            nextButton.isEnabled = false
            nextButton.alpha = 0.5f
        }

    }
}
