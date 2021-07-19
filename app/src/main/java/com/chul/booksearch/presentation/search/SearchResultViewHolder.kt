package com.chul.booksearch.presentation.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chul.booksearch.R
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.databinding.ViewSearchResultBinding
import com.chul.booksearch.presentation.util.GlideApp

class SearchResultViewHolder(private val itemBinding: ViewSearchResultBinding, val viewModel: SearchViewModel): RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(book: Books) {
        itemBinding.data = book
        itemBinding.vm = viewModel
    }
}