package com.ahmedorabi.instabugapp.features.words_list.repo

import com.ahmedorabi.instabugapp.data.domain.Word

interface WordsListLocalDataSource {

    fun getWordsList() :  List<Word>
    fun insertAllWords(words : List<Word>)
    fun deleteAllWords()
}