package com.ione.kavach.ui.survey

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ione.kavach.R
import com.ione.kavach.data.Options
import com.ione.kavach.data.QuestionsList

class AnswersRecyclerViewAdapter(
    private val clickInteractionListener: ClickInteractionListener,
    private val currentQuestionsList: QuestionsList
) : RecyclerView.Adapter<AnswersRecyclerViewAdapter.AnswerViewHolder>() {

    private var list: List<Options> = currentQuestionsList.options

    class AnswerViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.answer_item, parent, false)) {
        private var answerTextView: TextView? = null

        init {
            answerTextView = itemView.findViewById(R.id.answerTextView)
        }

        fun bind(
            answer: Options,
            currentQuestionsList: QuestionsList,
            clickInteractionListener: ClickInteractionListener
        ) {
            answerTextView?.text = answer.option
            handleTextBackground(currentQuestionsList, answer)
            answerTextView?.setOnClickListener {
                addAnswer(answer, currentQuestionsList)
                clickInteractionListener.notifyAdapter()
            }
        }

        private fun handleTextBackground(
            currentQuestionsList: QuestionsList,
            answer: Options
        ) {
            if (currentQuestionsList.selectedOptions.contains(answer)) {
                answerTextView?.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.round_corner_button_bg)
                answerTextView?.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        android.R.color.white
                    )
                )
            } else {
                answerTextView?.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.round_border_rect_bg)
                answerTextView?.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        android.R.color.primary_text_light
                    )
                )
            }
        }

        private fun addAnswer(answer: Options, currentQuestionsList: QuestionsList) {
            if (!currentQuestionsList.selectedOptions.contains(answer)) {
                if (!currentQuestionsList.isMultipleSelectionAllowed)
                    currentQuestionsList.selectedOptions.clear()
                currentQuestionsList.selectedOptions.add(answer)
            } else {
                currentQuestionsList.selectedOptions.remove(answer)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AnswerViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = list[position]
        holder.bind(answer, currentQuestionsList, clickInteractionListener)
    }

    override fun getItemCount(): Int = list.size

}
