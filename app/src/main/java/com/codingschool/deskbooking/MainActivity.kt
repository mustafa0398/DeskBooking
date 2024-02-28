package com.codingschool.deskbooking

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.codingschool.deskbooking.ui.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setupWithNavController(navController)

        setupActionBarWithNavController(navController, AppBarConfiguration(navGraph = navController.graph))

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> View.GONE
                else -> View.VISIBLE
            }
        }

        viewModel.checkLoginStatus()
        viewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                if (navController.currentDestination?.id == R.id.loginFragment || navController.currentDestination?.id == R.id.registerFragment) {
                    navController.navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            } else {
                if (navController.currentDestination?.id != R.id.loginFragment && navController.currentDestination?.id != R.id.registerFragment) {
                    navController.navigate(R.id.loginFragment)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
