package com.chul.booksearch.presentation

import androidx.recyclerview.widget.RecyclerView
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.presentation.search.SearchResultAdapter
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.chul.booksearch.presentation.search.SearchViewModel
import com.chul.booksearch.presentation.util.AutoLoadScrollListener

@BindingAdapter("setItems")
fun RecyclerView.setAdapterItems(items: MutableList<Books>?) {
    items?.let {
        (adapter as SearchResultAdapter).submitList(it)
    }
}

@BindingAdapter("setAutoScroll")
fun RecyclerView.setAutoScrollListener(viewModel: ViewModel) {
    if(viewModel is SearchViewModel && layoutManager is LinearLayoutManager) {
        val listener = object : AutoLoadScrollListener(layoutManager as LinearLayoutManager) {
            override fun loadMore(itemCount: Int) {
                if(itemCount < viewModel.totalCount) {
                    viewModel.searchMore()
                }
            }
        }
        addOnScrollListener(listener)
    }
}