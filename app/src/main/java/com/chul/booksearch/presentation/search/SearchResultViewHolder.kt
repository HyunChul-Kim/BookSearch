package com.chul.booksearch.presentation.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chul.booksearch.R
import com.chul.booksearch.data.model.Books

abstract class SearchResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.search_result_image)
    private val title: TextView = itemView.findViewById(R.id.search_result_title)
    private val subtitle: TextView = itemView.findViewById(R.id.search_result_subtitle)
    private val price: TextView = itemView.findViewById(R.id.search_result_price)

    private var isbn13 = ""

    init {
        itemView.setOnClickListener {
            if(isbn13.isNotEmpty()) {
                onClicked(isbn13)
            }
        }
    }

    fun bind(book: Books) {
        Glide.with(itemView.context).load(book.image).fitCenter().into(imageView)
        title.text = book.title
        subtitle.text = book.subtitle
        price.text = book.price
        isbn13 = book.isbn13
    }

    abstract fun onClicked(isbn13: String)
}