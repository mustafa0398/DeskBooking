package com.codingschool.deskbooking

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.codingschool.deskbooking.ui.login.LoginViewModel
import com.codingschool.deskbooking.ui.profile.ProfileViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val loginViewModel: LoginViewModel by viewModel()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(navGraph = navController.graph)
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = if (destination.id in listOf(
                    R.id.loginFragment,
                    R.id.registerFragment
                )
            ) View.GONE else View.VISIBLE

            val showBackArrow =
                destination.id == R.id.registerFragment || destination.id == R.id.desksFragment
            supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
        }

        loginViewModel.checkLoginStatus()
        loginViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                profileViewModel.fetchUserProfile()
                if (navController.currentDestination?.id !in listOf(R.id.bookingplanFragment)) {
                    navController.navigate(R.id.bookingplanFragment)
                }
            } else {
                if (navController.currentDestination?.id !in listOf(
                        R.id.loginFragment,
                        R.id.registerFragment
                    )
                ) {
                    navController.navigate(R.id.loginFragment)
                }
            }
        }

        profileViewModel.isLoggedOut.observe(this) { isLoggedOut ->
            if (isLoggedOut) {
                navController.navigate(R.id.loginFragment)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val currentDestination = navController.currentDestination?.id
        val showBackArrow =
            currentDestination == R.id.registerFragment || currentDestination == R.id.desksFragment
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
