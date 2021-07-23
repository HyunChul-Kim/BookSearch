package com.chul.booksearch.presentation.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.chul.booksearch.BookApplication
import com.chul.booksearch.R
import com.chul.booksearch.databinding.ActivitySearchBinding
import com.chul.booksearch.presentation.bookdetail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as BookApplication).appComponent.searchComponent().create().inject(this)
        setupViewModel()
        binding = ActivitySearchBinding.inflate(layoutInflater).apply {
            vm = searchViewModel
            lifecycleOwner = this@SearchActivity
        }
        setContentView(binding.root)
        setupListAdapter()
        setupEditText()
        setupSearchButton()
        setupEvent()
    }

    private fun setupViewModel() {
        searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
    }

    private fun setupListAdapter() {
        binding.searchRecyclerView.adapter = SearchResultAdapter(searchViewModel)
        binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupEditText() {
        binding.searchEditText.setOnEditorActionListener { textView, id, _ ->
            var result = false
            if(id == EditorInfo.IME_ACTION_SEARCH) {
                if(textView.text.isNotEmpty()) {
                    hideKeyboard()
                    searchViewModel.onSearch()
                    result = true
                }
            }
            result
        }
    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            if(binding.searchEditText.text.isNotEmpty()) {
                hideKeyboard()
                binding.searchRecyclerView.scrollToPosition(0)
                searchViewModel.onSearch()
            }
        }
    }

    private fun setupEvent() {
        searchViewModel.booksItemClickEvent.observe(this) { isbn13 ->
            val detailIntent = Intent(this, DetailActivity::class.java).apply {
                putExtra("isbn13", isbn13)
            }
            startActivity(detailIntent)
        }
        searchViewModel.networkExceptionEvent.observe(this) {
            showSnackBar(resources.getString(R.string.network_not_connected))
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

}