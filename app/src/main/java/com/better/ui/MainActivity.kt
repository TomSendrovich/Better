package com.better.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.better.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var logoutButton: Button
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        initViews()
        initDrawerLayout()

        setSharedPrefPageSelectedToDefault()
    }

    private fun initViews() {
        logoutButton = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            logoutFromApp()
        }
    }

    private fun logoutFromApp() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(EXTRA_LOGOUT, true)
        startActivity(intent)
        finish()
    }

    private fun initDrawerLayout() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_feed,
                R.id.nav_matches,
                R.id.nav_about
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


        //init header view
        val headerView: View = navView.getHeaderView(0)
        val imageViewUser = headerView.findViewById<View>(R.id.nav_header_photo) as ImageView
        val navTitle = headerView.findViewById<View>(R.id.nav_header_title) as TextView
        val navSubtitle = headerView.findViewById<View>(R.id.num_header_subtitle) as TextView
        val adminSubtitle = headerView.findViewById<View>(R.id.admin_header_subtitle) as TextView

        imageViewUser.setOnClickListener {
            // navigate to profile fragment
            viewModel.updateProfileToShow()
            navController.navigate(R.id.nav_profile)
            drawerLayout.close()
        }

        viewModel.appUser.observe(this, { user ->
            navTitle.text = user.name
            navSubtitle.text = user.email

            if (user.isAdmin) {
                adminSubtitle.visibility = VISIBLE
            } else {
                adminSubtitle.visibility = GONE
            }

            Glide
                .with(imageViewUser.context)
                .load(user.photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .placeholder(R.drawable.ic_profile)
                .into(imageViewUser)
        })

        viewModel.isBanned.observe(this, { value ->
            if (value) {
                Toast.makeText(applicationContext, "Your account is banned!", Toast.LENGTH_LONG)
                    .show()
                logoutFromApp()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setSharedPrefPageSelectedToDefault() {
        val preferences = getSharedPreferences(VIEW_PAGER, MODE_PRIVATE)
        val editor = preferences.edit()
        editor?.putInt(SHARED_PREF_PAGE_SELECTED, PAGE_SELECTED_DEFAULT)
        editor?.apply()
    }
}
