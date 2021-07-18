package com.chul.booksearch.presentation.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AutoLoadScrollListener constructor(private val layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {

    private var threshold = 3
    private var prevItemCount = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if(isLoading && totalItemCount > prevItemCount) {
            isLoading = false
            prevItemCount = totalItemCount
        }

        if(!isLoading && lastVisibleItemPosition + threshold >= totalItemCount) {
            loadMore(totalItemCount)
            isLoading = true
        }
    }

    abstract fun loadMore(itemCount: Int)
}