package com.chul.booksearch.presentation.bookdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chul.booksearch.BookApplication
import com.chul.booksearch.R
import com.chul.booksearch.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as BookApplication).appComponent.detailComponent().create().inject(this)
        setupViewModel()
        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            vm = detailViewModel
            lifecycleOwner = this@DetailActivity
        }
        setContentView(binding.root)
        setupEvent()
        start()
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }

    private fun setupEvent() {
        detailViewModel.networkExceptionEvent.observe(this) {
            showSnackBar(resources.getString(R.string.network_error_msg))
        }
    }

    private fun start() {
        val isbn13 = intent.getStringExtra("isbn13") ?: ""
        detailViewModel.requestDetail(isbn13)
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
}