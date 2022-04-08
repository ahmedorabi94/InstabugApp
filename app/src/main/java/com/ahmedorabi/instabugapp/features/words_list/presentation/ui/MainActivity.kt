package com.ahmedorabi.instabugapp.features.words_list.presentation.ui

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.ahmedorabi.instabugapp.R
import com.ahmedorabi.instabugapp.data.api.Resource
import com.ahmedorabi.instabugapp.data.di.AppModule
import com.ahmedorabi.instabugapp.databinding.ActivityMainBinding
import com.ahmedorabi.instabugapp.features.words_list.presentation.viewmodel.MainViewModelFactory
import com.ahmedorabi.instabugapp.features.words_list.presentation.viewmodel.WordsListViewModel
import com.ahmedorabi.instabugapp.utils.EspressoIdlingResource


class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: WordAdapter
    private lateinit var viewModel: WordsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WordAdapter()
        binding.recyclerViewMain.adapter = adapter

        val useCase = AppModule(applicationContext).useCase

        EspressoIdlingResource.increment()
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(useCase)
        )[WordsListViewModel::class.java]

        viewModel.wordsList.observe(this) {
            it?.let {
                Log.e("Main", it.toString())
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.let { list ->
                            if (list.isNotEmpty()) {
                                hideDataStatus()
                            } else {
                                showDataStatus()
                            }
                        }
                        binding.progressbar.visibility = View.GONE
                        EspressoIdlingResource.decrement()
                        adapter.submitList(it.data)


                    }
                    Resource.Status.ERROR -> {
                        hideDataStatus()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING -> {
                        hideDataStatus()
                        binding.progressbar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun showDataStatus() {
        binding.dataStatusTv.visibility = View.VISIBLE
    }

    private fun hideDataStatus() {
        binding.dataStatusTv.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView =
            menu!!.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query.equals("")) {
                    viewModel.searchWord("")
                } else {
                    viewModel.searchWord(query!!)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.equals("")) {
                    viewModel.searchWord("")
                } else {
                    viewModel.searchWord(newText!!)

                }
                return true
            }
        })

        searchView.setOnCloseListener {
            viewModel.searchWord("")
            true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search -> {
                Log.e("", "Search")
            }
            R.id.sort -> {
                Log.e("", "Sort")

                viewModel.sortWordsList()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}