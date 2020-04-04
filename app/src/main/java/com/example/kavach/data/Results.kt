package com.example.kavach.data

import com.google.gson.annotations.SerializedName


data class Results (

    @SerializedName("value") val value : List<Value>,
    @SerializedName("prevention") val prevention : Prevention
)
