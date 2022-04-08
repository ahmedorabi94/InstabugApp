package com.ahmedorabi.instabugapp.features.words_list.repo

class WordsListRepository constructor(private val remoteDataSource: WordsListRemoteDataSource){

    fun getWordsList() = remoteDataSource.getWordsList()
}