package com.chul.booksearch.presentation.search

import androidx.recyclerview.widget.RecyclerView
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.databinding.ViewSearchResultBinding

class SearchResultViewHolder(private val itemBinding: ViewSearchResultBinding, val viewModel: SearchViewModel): RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(book: Books) {
        itemBinding.data = book
        itemBinding.vm = viewModel
    }
}