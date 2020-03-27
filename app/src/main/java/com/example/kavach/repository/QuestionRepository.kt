package com.example.kavach.repository

import com.example.kavach.data.QuestionsList
import com.example.kavach.data.Results

interface QuestionRepository {
    fun fetchQuestions(): List<QuestionsList>
    fun fetchResults(): List<Results>
}
