package com.chul.booksearch.presentation.search

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.chul.booksearch.data.model.Books
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.chul.booksearch.presentation.util.AutoLoadScrollListener
import com.chul.booksearch.presentation.util.GlideApp

@BindingAdapter("setItems")
fun RecyclerView.setAdapterItems(items: MutableList<Books>?) {
    (adapter as SearchResultAdapter).submitList(items)
}

@BindingAdapter("setAutoScroll")
fun RecyclerView.setAutoScrollListener(viewModel: ViewModel) {
    if(viewModel is SearchViewModel && layoutManager is LinearLayoutManager) {
        val listener = object : AutoLoadScrollListener(layoutManager as LinearLayoutManager) {
            override fun loadMore(itemCount: Int) {
                if(itemCount < viewModel.totalCount) {
                    viewModel.onSearchMore()
                }
            }
        }
        addOnScrollListener(listener)
    }
}

@BindingAdapter("image")
fun ImageView.loadImage(url: String?) {
    if(!url.isNullOrEmpty()) {
        GlideApp.with(this).load(url).fitCenter().into(this)
    }
}