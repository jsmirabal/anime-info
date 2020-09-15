package com.jsmirabal.animeinfo.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jsmirabal.animeinfo.InstantTaskExecutorExtension
import com.jsmirabal.animeinfo.data.DUMMY_ERROR_MESSAGE
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyBusinessError
import com.jsmirabal.animeinfo.data.dummyDomainTopAnimes
import com.jsmirabal.animeinfo.data.dummyDomainTopAnimesResultSuccess
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.usecase.FetchTopAiringAnimesUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutorExtension::class)
internal class AnimeViewModelTest {
    private val fetchTopAnimesUseCase = mockk<FetchTopAiringAnimesUseCase>()
    private val animeViewModelSpy =
        spyk(AnimeViewModel(fetchTopAnimesUseCase), recordPrivateCalls = true)
    private val onResult =
        slot<suspend (ResultWrapper<DomainTopAnimes, DomainLayerError>) -> Unit>()
    private val success = slot<(DomainTopAnimes) -> Unit>()
    private val error = slot<(DomainLayerError) -> Unit>()

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun fetchTopAnimesSuccess() = runBlockingTest {
        TestLogger.given("FetchTopAnimesUseCase returns mocked data")
        every { useCaseExecution() } returns Job()
        TestLogger.and("ResultWrapper returns mocked data")
        every { resultWrapperEither() } just runs

        TestLogger.whenever("AnimeViewModel#fetchTopAnimes() is executed")
        animeViewModelSpy.fetchTopAnimes()
        onResult.captured.invoke(dummyDomainTopAnimesResultSuccess)
        success.captured.invoke(dummyDomainTopAnimes)

        TestLogger.then("Verify methods were called")
        verifyAll {
            animeViewModelSpy.fetchTopAnimes()
            useCaseExecution()
            resultWrapperEither()
            animeViewModelSpy["onSuccess"](dummyDomainTopAnimes)

            TestLogger.then("Verify a job was added to a job list")
            (animeViewModelSpy getProperty "jobs").apply {
                (this as? MutableList<Job>)?.isNotEmpty() shouldBe true
            }

            TestLogger.then("Verify topAnimesLiveData.value has been updated")
            (animeViewModelSpy getProperty "topAnimesLiveData").apply {
                (this as? MutableLiveData<DomainTopAnimes>)?.value shouldBe dummyDomainTopAnimes
            }
        }

        TestLogger.finally("Validate every mock calls were verified")
        confirmVerified(
            fetchTopAnimesUseCase,
            dummyDomainTopAnimesResultSuccess,
            animeViewModelSpy
        )
    }

    @Test
    fun fetchTopAnimesError() = runBlockingTest {
        TestLogger.given("FetchTopAnimesUseCase returns mocked data")
        every { useCaseExecution() } returns Job()
        TestLogger.and("ResultWrapper returns mocked data")
        every { resultWrapperEither() } just runs
        TestLogger.and("DomainLayerError.DelegateError returns mocked data")
        every { dummyBusinessError.cause } returns DUMMY_ERROR_MESSAGE

        TestLogger.whenever("AnimeViewModel#fetchTopAnimes() is executed")
        animeViewModelSpy.fetchTopAnimes()
        onResult.captured.invoke(dummyDomainTopAnimesResultSuccess)
        error.captured.invoke(dummyBusinessError)

        TestLogger.then("Verify methods were called")
        verifyAll {
            animeViewModelSpy.fetchTopAnimes()
            useCaseExecution()
            resultWrapperEither()
            animeViewModelSpy["onError"](dummyBusinessError)

            TestLogger.then("Verify a job was added to a job list")
            (animeViewModelSpy getProperty "jobs").apply {
                (this as? MutableList<Job>)?.isNotEmpty() shouldBe true
            }

            TestLogger.then("Verify topAnimesLiveData.value has been updated")
            (animeViewModelSpy getProperty "errorLiveData").apply {
                (this as? MutableLiveData<String>)?.value shouldBe DUMMY_ERROR_MESSAGE
            }
        }

        TestLogger.finally("Validate every mock calls were verified")
        confirmVerified(
            fetchTopAnimesUseCase,
            dummyDomainTopAnimesResultSuccess,
            animeViewModelSpy
        )
    }

    @Test
    fun getTopAnimesLiveData() {
        TestLogger.log("Verify getTopAnimesLiveData() returns an instance")
        animeViewModelSpy.getTopAnimesLiveData() shouldNotBe null
    }

    @Test
    fun getErrorLiveData() {
        TestLogger.log("Verify getErrorLiveData() returns an instance")
        animeViewModelSpy.getErrorLiveData() shouldNotBe null
    }

    @Test
    fun onCleared() {
        // Not sure how to call onCleared()
    }

    private fun MockKMatcherScope.resultWrapperEither() {
        dummyDomainTopAnimesResultSuccess.successOrError(capture(success), capture(error))
    }

    private fun MockKMatcherScope.useCaseExecution() =
        fetchTopAnimesUseCase.runAsync("1", capture(onResult))

}
