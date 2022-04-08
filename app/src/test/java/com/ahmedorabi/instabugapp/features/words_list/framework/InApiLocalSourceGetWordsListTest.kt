package com.ahmedorabi.instabugapp.features.words_list.framework

import com.ahmedorabi.instabugapp.data.api.ErrorResponse
import com.ahmedorabi.instabugapp.data.api.ResultWrapper
import com.ahmedorabi.instabugapp.data.api.SafeApiCallHelper
import com.ahmedorabi.instabugapp.data.domain.Word
import com.ahmedorabi.instabugapp.features.words_list.repo.DbManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InApiLocalSourceGetWordsListTest {


    @Mock
    lateinit var apiCallHelper: SafeApiCallHelper

    @Mock
    lateinit var dbManager: DbManager

    private lateinit var inApiLocalSourceGetWordsList: InApiLocalSourceGetWordsList


    @Before
    fun setup() {

        inApiLocalSourceGetWordsList = InApiLocalSourceGetWordsList(dbManager, apiCallHelper)

    }

    @Test
    fun shouldGetWordsListSuccessResponse() {

        val word = Word(name = "ios", total = 12)
        val list = ArrayList<Word>()
        list.add(word)


        val result = ResultWrapper.Success(list)

        runBlocking {

            Mockito.doReturn(result)
                .`when`(apiCallHelper)
                .getWordsList()

            Mockito.doReturn(list)
                .`when`(dbManager)
                .getWordsList()

            val response = inApiLocalSourceGetWordsList.getWordsList()

            assertEquals(response, result.value)

        }
    }

    @Test
    fun shouldGetWordsListFailureResponse() {

        val word = Word(name = "ios", total = 12)
        val list = ArrayList<Word>()
        list.add(word)


        val result = ResultWrapper.Error(-1, ErrorResponse("Error"))

        runBlocking {

            Mockito.doReturn(result)
                .`when`(apiCallHelper)
                .getWordsList()

            Mockito.doReturn(list)
                .`when`(dbManager)
                .getWordsList()

            val response = inApiLocalSourceGetWordsList.getWordsList()

            assertEquals(response, list)

        }
    }

}