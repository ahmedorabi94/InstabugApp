package com.ahmedorabi.instabugapp.features.words_list.framework

import android.util.Log
import com.ahmedorabi.instabugapp.data.api.ResultWrapper
import com.ahmedorabi.instabugapp.data.api.SafeApiCallHelper
import com.ahmedorabi.instabugapp.data.domain.Word
import com.ahmedorabi.instabugapp.features.words_list.repo.DbManager
import com.ahmedorabi.instabugapp.features.words_list.repo.WordsListRemoteDataSource

class InApiLocalSourceGetWordsList
    (private val dbManager: DbManager, private val apiCallHelper: SafeApiCallHelper) :
    WordsListRemoteDataSource {


    override fun getWordsList(): List<Word> {


        val wordsList = ArrayList<Word>()
        val networkResult = apiCallHelper.getWordsList()

        when (networkResult) {
            is ResultWrapper.NetworkError, is ResultWrapper.Error -> {
                val words = dbManager.getWordsList()
                wordsList.addAll(words)
            }
            ResultWrapper.NoContentError -> {
                Log.e("InApiLocalGetWords", "NoContentError")
            }
            is ResultWrapper.Success -> {
                val list = networkResult.value
                list.sortedBy { it.total }

                dbManager.deleteAllWords()
                dbManager.insertAllWords(list)
                val words = dbManager.getWordsList()
                wordsList.addAll(words)
            }
        }

        return wordsList
    }


}