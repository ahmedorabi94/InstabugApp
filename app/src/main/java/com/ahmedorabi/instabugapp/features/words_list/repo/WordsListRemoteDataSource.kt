package com.ahmedorabi.instabugapp.features.words_list.repo

import com.ahmedorabi.instabugapp.data.domain.Word

interface WordsListRemoteDataSource {

    fun getWordsList(): List<Word>
}