package com.jsmirabal.animeinfo.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jsmirabal.animeinfo.InstantTaskExecutorExtension
import com.jsmirabal.animeinfo.data.DUMMY_ERROR_MESSAGE
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyBusinessError
import com.jsmirabal.animeinfo.data.dummyDomainMainFeed
import com.jsmirabal.animeinfo.data.dummyDomainMainFeedSuccess
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.mainfeed.DomainMainFeed
import com.jsmirabal.animeinfo.domain.usecase.build.BuildMainFeedUseCase
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
internal class MainViewModelTest {
    private val buildMainFeedUseCase = mockk<BuildMainFeedUseCase>()
    private val animeViewModelSpy =
        spyk(MainViewModel(buildMainFeedUseCase), recordPrivateCalls = true)
    private val mainFeedResultSlot =
        slot<suspend (ResultWrapper<DomainMainFeed, DomainLayerError>) -> Unit>()
    private val mainFeedSlot = slot<(DomainMainFeed) -> Unit>()
    private val errorSlot = slot<(DomainLayerError) -> Unit>()

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun buildMainFeedSuccess() = runBlockingTest {
        TestLogger.given("BuildMainFeedUseCase returns mocked data")
        every { buildMainFeedUseCaseCaptured() } returns Job()
        TestLogger.and("ResultWrapper returns mocked data")
        every { mainFeedSuccessCaptured() } just runs

        TestLogger.whenever("AnimeViewModel#buildMainFeed() is executed")
        animeViewModelSpy.buildMainFeed()
        mainFeedResultSlot.captured.invoke(dummyDomainMainFeedSuccess)
        mainFeedSlot.captured.invoke(dummyDomainMainFeed)

        TestLogger.then("Verify methods were called")
        verifyAll {
            animeViewModelSpy.buildMainFeed()
            buildMainFeedUseCaseCaptured()
            mainFeedSuccessCaptured()
            animeViewModelSpy["onMainFeedSuccess"](dummyDomainMainFeed)

            TestLogger.then("Verify a job was added to a job list")
            (animeViewModelSpy getProperty "jobs").apply {
                (this as? MutableList<Job>)?.isNotEmpty() shouldBe true
            }

            TestLogger.then("Verify mainFeedLiveData.value has been updated")
            (animeViewModelSpy getProperty "mainFeedLiveData").apply {
                (this as? MutableLiveData<DomainMainFeed>)?.value shouldBe dummyDomainMainFeed
            }
        }

        TestLogger.finally("Validate every mock calls were verified")
        confirmVerified(
            buildMainFeedUseCase,
            dummyDomainMainFeedSuccess,
            animeViewModelSpy
        )
    }

    @Test
    fun buildMainFeedError() = runBlockingTest {
        TestLogger.given("BuildMainFeedUseCase returns mocked data")
        every { buildMainFeedUseCaseCaptured() } returns Job()
        TestLogger.and("ResultWrapper returns mocked data")
        every { mainFeedSuccessCaptured() } just runs
        TestLogger.and("DomainLayerError.DelegateError returns mocked data")
        every { dummyBusinessError.cause } returns DUMMY_ERROR_MESSAGE

        TestLogger.whenever("AnimeViewModel#buildMainFeed() is executed")
        animeViewModelSpy.buildMainFeed()
        mainFeedResultSlot.captured.invoke(dummyDomainMainFeedSuccess)
        errorSlot.captured.invoke(dummyBusinessError)

        TestLogger.then("Verify methods were called")
        verifyAll {
            animeViewModelSpy.buildMainFeed()
            buildMainFeedUseCaseCaptured()
            mainFeedSuccessCaptured()
            animeViewModelSpy["onError"](dummyBusinessError)

            TestLogger.then("Verify a job was added to a job list")
            (animeViewModelSpy getProperty "jobs").apply {
                (this as? MutableList<Job>)?.isNotEmpty() shouldBe true
            }

            TestLogger.then("Verify mainFeedLiveData.value has been updated")
            (animeViewModelSpy getProperty "errorLiveData").apply {
                (this as? MutableLiveData<String>)?.value shouldBe DUMMY_ERROR_MESSAGE
            }
        }

        TestLogger.finally("Validate every mock calls were verified")
        confirmVerified(
            buildMainFeedUseCase,
            dummyDomainMainFeedSuccess,
            animeViewModelSpy
        )
    }

    @Test
    fun getMainFeedLiveData() {
        TestLogger.log("Verify getMainFeedLiveData() returns an instance")
        animeViewModelSpy.getMainFeedLiveData() shouldNotBe null
    }

    @Test
    fun getErrorLiveData() {
        TestLogger.log("Verify getErrorLiveData() returns an instance")
        animeViewModelSpy.getErrorLiveData() shouldNotBe null
    }

    private fun MockKMatcherScope.mainFeedSuccessCaptured() {
        dummyDomainMainFeedSuccess.successOrError(capture(mainFeedSlot), capture(errorSlot))
    }

    private fun MockKMatcherScope.buildMainFeedUseCaseCaptured() =
        buildMainFeedUseCase.runAsync(Unit, capture(mainFeedResultSlot))

}
