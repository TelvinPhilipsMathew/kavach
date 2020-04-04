package com.example.kavach.repository

import android.content.res.AssetManager
import com.example.kavach.data.QuestionsList
import com.example.kavach.data.Results
import com.example.kavach.utils.JsonUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class QuestionRepositoryImpl constructor(private val assetsManager: AssetManager) : QuestionRepository {

    companion object {
        const val JSON_FILE_NAME = "survey.json"
        const val RESULT_JSON_FILE_NAME = "result.json"
    }

    override fun fetchQuestions(): List<QuestionsList> {

        val surveyJSON = JsonUtil.loadJSONFromAsset(assetsManager, JSON_FILE_NAME)
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        val surveyListType = object : TypeToken<List<QuestionsList>>() {}.type

        return gson.fromJson(surveyJSON, surveyListType)
    }

    override fun fetchResults(): Results {

        val resultJSON = JsonUtil.loadJSONFromAsset(assetsManager, RESULT_JSON_FILE_NAME)
        return Gson().fromJson(resultJSON, Results::class.java)
    }
}
