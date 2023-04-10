package com.nmp.ubayakost_160420016.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nmp.ubayakost_160420016.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val LOCALHOST = "10.0.2.2"
    }

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

        // Set button on click to open drawer
        drawerButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // Set navView with navController
        NavigationUI.setupWithNavController(navView, navController)
    }
}