package com.example.kavach.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Results {
    @SerializedName("min_score")
    @Expose
    var minScore: Int = 0

    @SerializedName("max_score")
    @Expose
    var maxScore: Int = 0

    @SerializedName("risk")
    @Expose
    lateinit var risk: String

    @SerializedName("description")
    @Expose
    lateinit var description: String

}
