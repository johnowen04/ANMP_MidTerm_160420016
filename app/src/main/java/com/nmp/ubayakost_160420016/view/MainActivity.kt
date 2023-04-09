package com.nmp.ubayakost_160420016.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nmp.ubayakost_160420016.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Instantiate navController and initiate later
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initiate navController from supportFragmentManager
        navController = (supportFragmentManager.findFragmentById(R.id.navHost)
                as NavHostFragment).navController

        // Bind navController to bottomNav
        bottomNav.setupWithNavController(navController)

        // Set default menu bottomNav
        bottomNav.menu.findItem(R.id.itemHome).isChecked = true
    }
}