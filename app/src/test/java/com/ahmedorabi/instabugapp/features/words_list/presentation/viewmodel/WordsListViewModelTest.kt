package com.ahmedorabi.instabugapp.features.words_list.presentation.viewmodel

import android.os.Handler
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ahmedorabi.instabugapp.data.api.Resource
import com.ahmedorabi.instabugapp.data.domain.Word
import com.ahmedorabi.instabugapp.features.words_list.use_case.GetWordsListUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class WordsListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiWordsObserver: Observer<Resource<List<Word>>>

    private lateinit var viewModel: WordsListViewModel

    @Mock
    private lateinit var useCase: GetWordsListUseCase


    @Before
    fun setup() {
        viewModel = WordsListViewModel(useCase)
    }

    @Test
    fun shouldGetWordsListSuccessResponse() {
        mock(Handler::class.java)

        val word = Word(name = "ios", total = 12)
        val list = ArrayList<Word>()
        list.add(word)


        Mockito.doReturn(list)
            .`when`(useCase)
            .invoke()

        viewModel.wordsList.observeForever(apiWordsObserver)


        verify(apiWordsObserver).onChanged(Resource.loading(null))

        val data = CompletableFuture<List<Word>>()
        val liveData = CompletableFuture<Resource<List<Word>>>()

        Executors.newSingleThreadExecutor().submit {
            data.complete(useCase.invoke())
            liveData.complete(Resource.success(list))
        }

        assertEquals(data.get(), list)
        assertEquals(liveData.get(), Resource.success(list))


    }


}