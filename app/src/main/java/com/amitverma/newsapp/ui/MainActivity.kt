package com.amitverma.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amitverma.newsapp.R
import com.amitverma.newsapp.databinding.ActivityMainBinding
import com.amitverma.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.amitverma.newsapp.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topHeadlineButton.setOnClickListener {
            startActivity(TopHeadlineActivity.getStartIntent(this@MainActivity))
        }

        binding.topHeadlinePaginationButton.setOnClickListener {
            startActivity(PaginationTopHeadlineActivity.getStartIntent(this@MainActivity))
        }
    }
}