package com.ione.kavach.ui.survey

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.*
import com.ione.kavach.api.ApiClientBuilder
import com.ione.kavach.api.KavachApi
import com.ione.kavach.data.InitResponse
import com.ione.kavach.data.QuestionsList
import com.ione.kavach.data.SubmitFormRequest
import com.ione.kavach.repository.QuestionRepository
import com.ione.kavach.repository.QuestionRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyViewModel : ViewModel(), Callback<String> {

    private var initResponse: InitResponse? = null
    private var country: String? = ""
    private var city: String? = ""
    private var longtitude: Double = 0.0
    private var latitude: Double = 0.0

    private val mutableCurrentIndex = MutableLiveData<Int>()
    internal val currentIndex: LiveData<Int> = mutableCurrentIndex

    private val mutableResult = MutableLiveData<Int>()
    internal val result: LiveData<Int> = mutableResult

    private lateinit var repository: QuestionRepository
    internal lateinit var questions: List<QuestionsList>
    internal val totalNumberOfQuestions: Int by lazy {
        questions.size
    }

    private val YES = "1"
    private val NO = "0"

    fun onViewCreated(assetManager: AssetManager) {
        repository = QuestionRepositoryImpl(assetManager)
        questions = repository.fetchQuestions()
        mutableCurrentIndex.value = 0
    }

    fun decrementCurrentIndex() {
        mutableCurrentIndex.value = mutableCurrentIndex.value?.minus(1)
    }

    fun incrementCurrentIndex() {
        mutableCurrentIndex.value = mutableCurrentIndex.value?.plus(1)
    }

    fun submitSurvey(phone: String, context: Context?) {
        val submitFormRequest = getSubmitFormRequest(phone, context)
        ApiClientBuilder().build().create(KavachApi::class.java).submitForm(submitFormRequest)
            .enqueue(this)
    }

    fun calculateScore(): Int {
        var totalScore = 0
        for (question in questions) {
            question.selectedOptions.forEach {
                totalScore += it.weight
            }
        }
        return totalScore
    }

    private fun getSubmitFormRequest(phone: String, context: Context?): SubmitFormRequest {
        var submitFormRequest = SubmitFormRequest()
        submitFormRequest.age = questions[0].selectedOptions[0].option
        submitFormRequest.gender = getValueForGenderQuestion(questions[1].selectedOptions[0].option)
        submitFormRequest.travelHistory =
            getValueForYesOrNoQuestion(questions[2].selectedOptions[0].option)
        submitFormRequest.closeContact =
            getValueForYesOrNoQuestion(questions[3].selectedOptions[0].option)
        submitFormRequest.healthcareWorker =
            getValueForYesOrNoQuestion(questions[4].selectedOptions[0].option)
        submitFormRequest.symptoms =
            if (isAnswered(5)) questions[5].selectedOptions.map { it.option } else listOf()
        submitFormRequest.session = getSession()
        submitFormRequest.deviceId =
            Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
        submitFormRequest.location = listOf(latitude, longtitude)
        submitFormRequest.rating = calculateScore().toString()
        submitFormRequest.phone = phone
        submitFormRequest.city = city.orEmpty()
        submitFormRequest.country = country.orEmpty()
        return submitFormRequest
    }

    private fun isAnswered(index: Int) = questions[index].selectedOptions.size != 0

    private fun getSession() =
        VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name + ";" + Build.MODEL

    private fun getValueForGenderQuestion(gender: String) =
        if (gender == "Male") YES else NO

    private fun getValueForYesOrNoQuestion(answer: String) =
        if (answer == "Yes") YES else NO

    override fun onFailure(call: Call<String>, t: Throwable) {
        Log.e("Failure", t.localizedMessage)
        mutableResult.value = calculateScore()
    }

    override fun onResponse(call: Call<String>, response: Response<String>) {
        val successful = response.isSuccessful
        val body = response.body()
        mutableResult.value = calculateScore()
    }

    fun setLatLong(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longtitude = longitude
    }

    fun setAddress(locality: String?, countryName: String?) {
        city = locality
        country = countryName
    }

    fun setInitResponse(initResponse: InitResponse?) {
        this.initResponse = initResponse
    }
}
