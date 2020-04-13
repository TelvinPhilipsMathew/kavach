package com.ione.kavach.api

import com.ione.kavach.data.InitResponse
import com.ione.kavach.data.SubmitFormRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KavachApi {

    @POST("v1/submit/form")
    fun submitForm(@Body request: SubmitFormRequest?): Call<String>
    @GET("kavachinit")
    fun init(): Call<InitResponse>
}
