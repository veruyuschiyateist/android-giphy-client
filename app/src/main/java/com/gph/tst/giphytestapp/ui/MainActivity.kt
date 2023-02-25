package com.gph.tst.giphytestapp.ui

import android.app.ActionBar.OnMenuVisibilityListener
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.domain.AuthState
import com.gph.tst.giphytestapp.domain.repository.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authManager: AuthManager

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavController()
        setupBottomNav()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                if (isUserAuthenticated()) {
                    setBottomNavVisibility(View.VISIBLE)
                } else {
                    setBottomNavVisibility(View.GONE)
                    navController.navigate(R.id.signInFragment)
                }
            }
        }

    }

    private fun isUserAuthenticated(): Boolean {
        val auth = authManager.getAuthState == AuthState.AUTHENTICATED
        Log.d(TAG, "isUserAuthenticated: $auth")
        return authManager.getAuthState == AuthState.AUTHENTICATED
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupBottomNav() {
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavView.setupWithNavController(navController)
    }

    private fun setBottomNavVisibility(visibility: Int) {
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavView?.visibility = visibility
    }

    companion object {
        private const val TAG = "MainActivity"
    }


}