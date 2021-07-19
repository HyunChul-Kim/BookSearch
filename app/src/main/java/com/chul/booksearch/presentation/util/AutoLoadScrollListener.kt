package com.chul.booksearch.presentation.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AutoLoadScrollListener constructor(private val layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {

    private var threshold = 5
    private var prevItemCount = 0
    private var called = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if(totalItemCount != prevItemCount) {
            called = false
            prevItemCount = totalItemCount
        }

        if(!called && lastVisibleItemPosition + threshold >= totalItemCount) {
            called = true
            loadMore(totalItemCount)
        }
    }

    abstract fun loadMore(itemCount: Int)
}