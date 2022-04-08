package com.ahmedorabi.instabugapp.features.words_list.presentation.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedorabi.instabugapp.data.api.Resource
import com.ahmedorabi.instabugapp.data.domain.Word
import com.ahmedorabi.instabugapp.features.words_list.use_case.GetWordsListUseCase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WordsListViewModel constructor(
    private val useCase: GetWordsListUseCase
) :
    ViewModel() {

    private val _wordsList = MutableLiveData<Resource<List<Word>>>()
    val wordsList get() = _wordsList

    //    private val _filterWordsList = MutableLiveData<Resource<List<Word>>>()
//    val filterWordsList get() = _filterWordsList
//
    private val originalList = ArrayList<Word>()

    private var isAsc = false

    init {
        getWords()
    }

    private fun getWords() {
        _wordsList.value = Resource.loading(null)
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val list = useCase.invoke()
            originalList.addAll(list)

            handler.post {

                _wordsList.value = Resource.success(list)


            }
        }
    }

    fun searchWord(query: String) {
        Log.e("searchWord", query)
        Log.e("searchWord", _wordsList.value.toString())

        if (query == "") {
            _wordsList.value = Resource.success(originalList)
            return
        }

        originalList.let {
            val newList = originalList.filter {
                it.name == query
            }
            Log.e("searchWord", newList.toString())
            if (newList.isNotEmpty()) {
                _wordsList.value = Resource.success(newList)
            }
        }

    }

    fun sortWordsList() {

        val words = _wordsList.value?.data

        words?.let {


            isAsc = if (!isAsc) {
                val newList = words.sortedBy {
                    it.total
                }
                _wordsList.value = Resource.success(newList)
                true
            } else {
                val newList = words.sortedByDescending {
                    it.total
                }
                _wordsList.value = Resource.success(newList)

                false
            }
        }


    }

}