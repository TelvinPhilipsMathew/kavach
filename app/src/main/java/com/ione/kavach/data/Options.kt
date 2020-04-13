package com.ione.kavach.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Options {
    @SerializedName("option")
    @Expose
    var option: String = ""

    @SerializedName("weight")
    @Expose
    var weight: Int = 0
}
