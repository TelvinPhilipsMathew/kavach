package com.ione.kavach.data

import com.google.gson.annotations.SerializedName

data class Value (

	@SerializedName("min_score") val min_score : Int,
	@SerializedName("max_score") val max_score : Int,
	@SerializedName("risk") val risk : String,
	@SerializedName("description") val description : String,
	@SerializedName("instruction") val instruction : Instruction
)
