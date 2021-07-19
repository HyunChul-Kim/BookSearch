package com.chul.booksearch.presentation.bookdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chul.booksearch.BookApplication
import com.chul.booksearch.databinding.ActivityDetailBinding
import javax.inject.Inject

class DetailActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as BookApplication).appComponent.detailComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            vm = detailViewModel
            lifecycleOwner = this@DetailActivity
        }
        setContentView(binding.root)
        start()
    }

    private fun start() {
        val isbn13 = intent.getStringExtra("isbn13") ?: ""
        detailViewModel.requestDetail(isbn13)
    }
}