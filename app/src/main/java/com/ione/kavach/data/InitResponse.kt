package com.ione.kavach.data

import com.google.gson.annotations.SerializedName


data class InitResponse (

	@SerializedName("_id") val _id : String,
	@SerializedName("introduction_text") val introduction_text : String,
	@SerializedName("result") val results : Results
)
