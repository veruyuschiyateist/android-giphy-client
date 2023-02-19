package com.gph.tst.giphytestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.core.os.BuildCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container_view)
            }
        }

        onBackPressedDispatcher.addCallback {
            supportFragmentManager.popBackStack()
        }
    }
}