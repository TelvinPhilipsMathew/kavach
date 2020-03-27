package com.example.kavach.ui.survey

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kavach.R
import com.example.kavach.ui.result.ResultActivity
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

        observeCurrentIndex()
        setClickListeners()
        observeSubmitForm()
    }

    private fun observeCurrentIndex() {
        viewModel.currentIndex.observe(activity!!, Observer<Int> {
            renderUi(it)
            if (it == viewModel.totalNumberOfQuestions - 1) {
                submitButton.visibility = View.VISIBLE
                nextButton.visibility = View.GONE
            } else {
                submitButton.visibility = View.GONE
                nextButton.visibility = View.VISIBLE
            }
            handleNavigationButton()
            handleBackgroundImage(it)
        })
    }

    private fun handleBackgroundImage(index: Int?) {
        backgroundImage.setImageDrawable( when (index) {
                0 -> ContextCompat.getDrawable(
                    context!!,R.drawable.question_1)
                1 -> ContextCompat.getDrawable(
                    context!!,R.drawable.question_2)
                2 -> ContextCompat.getDrawable(
                    context!!,R.drawable.question_3)
                3 -> ContextCompat.getDrawable(
                    context!!,R.drawable.question_4)
                4 -> ContextCompat.getDrawable(
                    context!!,R.drawable.question_5)
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
            showProgress()
            viewModel.submitClick(context)
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