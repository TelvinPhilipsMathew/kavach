package com.ione.kavach.repository

import com.ione.kavach.data.QuestionsList
import com.ione.kavach.data.Results

interface QuestionRepository {
    fun fetchQuestions(): List<QuestionsList>
    fun fetchResults(): Results
}
