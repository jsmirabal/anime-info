package com.jsmirabal.animeinfo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.mainfeed.DomainMainFeed
import com.jsmirabal.animeinfo.domain.usecase.build.BuildMainFeedUseCase
import kotlinx.coroutines.Job

class MainViewModel(
    private val buildMainFeedUseCase: BuildMainFeedUseCase
) : ViewModel() {

    private val jobs: MutableList<Job> = mutableListOf()
    private val mainFeedLiveData: MutableLiveData<DomainMainFeed> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun buildMainFeed() {
        buildMainFeedUseCase.runAsync(Unit) { result ->
            result.successOrError(::onMainFeedSuccess, ::onError)
        }.apply { jobs.add(this) }
    }

    fun getMainFeedLiveData(): LiveData<DomainMainFeed> = mainFeedLiveData

    fun getErrorLiveData(): LiveData<String> = errorLiveData

    override fun onCleared() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    private fun onMainFeedSuccess(result: DomainMainFeed) {
        mainFeedLiveData.value = result
    }

    private fun onError(error: DomainLayerError) {
        errorLiveData.value = when (error) {
            is DomainLayerError.BusinessError -> error.cause
            is DomainLayerError.DelegateError -> error.delegate.message
        }
    }
}
