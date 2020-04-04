package com.example.kavach.data

import com.google.gson.annotations.SerializedName

data class Instruction (

	@SerializedName("title") val title : String,
	@SerializedName("description") val description : String
)
