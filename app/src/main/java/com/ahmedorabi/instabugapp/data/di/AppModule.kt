package com.ahmedorabi.instabugapp.data.di

import android.content.Context
import com.ahmedorabi.instabugapp.data.api.SafeApiCallHelper
import com.ahmedorabi.instabugapp.features.words_list.framework.InApiLocalSourceGetWordsList
import com.ahmedorabi.instabugapp.features.words_list.repo.DbManager
import com.ahmedorabi.instabugapp.features.words_list.repo.WordsListRepository
import com.ahmedorabi.instabugapp.features.words_list.use_case.GetWordsListUseCase

class AppModule(private val applicationContext: Context) {


    private val apiCallHelper: SafeApiCallHelper by lazy {
        SafeApiCallHelper()
    }

    private val dbManager: DbManager by lazy {
        DbManager(applicationContext)
    }


    private val inApiSourceGetWordsList: InApiLocalSourceGetWordsList by lazy {
        InApiLocalSourceGetWordsList(dbManager, apiCallHelper)
    }


    private val repository: WordsListRepository by lazy {
        WordsListRepository(inApiSourceGetWordsList)
    }
    val useCase: GetWordsListUseCase by lazy {
        GetWordsListUseCase(repository)
    }

}