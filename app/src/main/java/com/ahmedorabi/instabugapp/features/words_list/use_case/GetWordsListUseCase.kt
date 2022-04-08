package com.ahmedorabi.instabugapp.features.words_list.use_case

import com.ahmedorabi.instabugapp.features.words_list.repo.WordsListRepository

class GetWordsListUseCase constructor(private val repository: WordsListRepository) {

    operator fun invoke() = repository.getWordsList()
}