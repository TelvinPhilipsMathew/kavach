package com.ione.kavach.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuestionsList {
    @SerializedName("question")
    @Expose
    lateinit var title: String

    @SerializedName("answers")
    @Expose
    lateinit var options: List<Options>

    @SerializedName("isMultipleSelectionAllowed")
    @Expose
    var isMultipleSelectionAllowed: Boolean = false

    var selectedOptions: MutableList<Options> = mutableListOf()
}
