package com.example.kavach.ui.result

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kavach.data.InitResponse
import com.example.kavach.data.Results
import com.example.kavach.repository.QuestionRepository
import com.example.kavach.repository.QuestionRepositoryImpl

class ResultViewModel : ViewModel() {

    private var initResponse: InitResponse? = null
    private lateinit var results: Results
    private lateinit var repository: QuestionRepository

    private val mutableResult = MutableLiveData<Results>()
    internal val resultLiveData: LiveData<Results> = mutableResult

    fun onViewCreated(
        assetManager: AssetManager,
        initResponse: InitResponse?
    ) {
        this.initResponse = initResponse
        if (initResponse == null) {
            repository = QuestionRepositoryImpl(assetManager)
            results = repository.fetchResults()
        } else {
            results = initResponse.results
        }
        mutableResult.value = results
    }

}
