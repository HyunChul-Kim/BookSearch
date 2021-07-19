package com.chul.booksearch.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chul.booksearch.R
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.databinding.ViewSearchResultBinding

class SearchResultAdapter(private val viewModel: SearchViewModel): ListAdapter<Books, SearchResultViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchResultViewHolder(ViewSearchResultBinding.inflate(inflater, parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Books>() {
            override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
                return oldItem.isbn13 == newItem.isbn13
            }

            override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
                return oldItem == newItem
            }

        }
    }
}