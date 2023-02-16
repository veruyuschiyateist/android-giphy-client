package com.gph.tst.giphytestapp.ui.pager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.ActivityPagerBinding

class PagerActivity : AppCompatActivity() {

    private val binding: ActivityPagerBinding by lazy { ActivityPagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)


    }
}