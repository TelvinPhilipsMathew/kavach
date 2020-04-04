package com.example.kavach.data


import com.google.gson.annotations.SerializedName


class SubmitFormRequest {
    @SerializedName("age")
    lateinit var age: String
    @SerializedName("gender")
    lateinit var gender: String
    @SerializedName("travel_history")
    lateinit var travelHistory: String
    @SerializedName("close_contact")
    lateinit var closeContact: String
    @SerializedName("healthcare_worker")
    lateinit var healthcareWorker: String
    @SerializedName("symptoms")
    lateinit var symptoms: List<String>
    @SerializedName("rating")
    lateinit var rating: String
    @SerializedName("phone")
    lateinit var phone: String
    @SerializedName("city")
    lateinit var city: String
    @SerializedName("country")
    lateinit var country: String
    @SerializedName("device_id")
    lateinit var deviceId: String
    @SerializedName("location")
    lateinit var location: List<Double>
    @SerializedName("session")
    lateinit var session: String

}
