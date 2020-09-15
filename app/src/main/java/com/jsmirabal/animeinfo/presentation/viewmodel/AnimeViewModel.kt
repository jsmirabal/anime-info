package com.jsmirabal.animeinfo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.usecase.FetchTopAiringAnimesUseCase
import kotlinx.coroutines.Job

class AnimeViewModel(
    private val fetchTopAiringAnimesUseCase: FetchTopAiringAnimesUseCase
) : ViewModel() {

    private val jobs: MutableList<Job> = mutableListOf()
    private val topAnimesLiveData: MutableLiveData<DomainTopAnimes> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun fetchTopAnimes() {
        fetchTopAiringAnimesUseCase.runAsync("1") { result ->
            result.successOrError(this::onSuccess, this::onError)
        }.apply { jobs.add(this) }
    }

    fun getTopAnimesLiveData(): LiveData<DomainTopAnimes> = topAnimesLiveData

    fun getErrorLiveData(): LiveData<String> = errorLiveData

    override fun onCleared() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    private fun onSuccess(result: DomainTopAnimes) {
        topAnimesLiveData.value = result
    }

    private fun onError(error: DomainLayerError) {
        errorLiveData.value = when (error) {
            is DomainLayerError.BusinessError -> error.cause
            is DomainLayerError.DelegateError -> error.delegate.message
        }
    }
}
