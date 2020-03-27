package com.example.kavach.api

import com.example.kavach.data.SubmitFormRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QuestionApi {

    @POST("submit/form")
    fun submitForm(@Body request: SubmitFormRequest?): Call<String>
}
