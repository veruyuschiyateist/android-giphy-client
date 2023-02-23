package com.gph.tst.giphytestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gph.tst.giphytestapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<SignUpFragment>(R.id.fragment_container_view)
////                add<HomeFragment>(R.id.fragment_container_view)
//            }
//        }
//
//        onBackPressedDispatcher.addCallback {
//            supportFragmentManager.popBackStack()
//        }
    }
}