package com.example.kavach.ui.result

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kavach.data.Results
import com.example.kavach.repository.QuestionRepository
import com.example.kavach.repository.QuestionRepositoryImpl

class ResultViewModel: ViewModel() {

    private lateinit var results: List<Results>
    private lateinit var repository: QuestionRepository

    private val mutableResult = MutableLiveData<List<Results>>()
    internal val resultLiveData: LiveData<List<Results>> = mutableResult

    fun onViewCreated(assetManager: AssetManager) {
        repository = QuestionRepositoryImpl(assetManager)
        results = repository.fetchResults()
        mutableResult.value = results
    }

}
