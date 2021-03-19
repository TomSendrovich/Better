package com.better.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.better.EXTRA_LOGOUT
import com.better.R
import com.better.SHARED_PREF_PAGE_SELECTED
import com.better.VIEW_PAGER
import com.better.model.Repository
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        initViews()
        initDrawerLayout()

        val preferences = getSharedPreferences(VIEW_PAGER, MODE_PRIVATE)
        val editor = preferences.edit()
        editor?.putInt(SHARED_PREF_PAGE_SELECTED, 7)
        editor?.apply()
    }

    private fun initViews() {
        logoutButton = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(EXTRA_LOGOUT, true)
            startActivity(intent)
            finish()
        }
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
                R.id.nav_about,
                R.id.nav_profile
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


        //init header view
        val headerView: View = navView.getHeaderView(0)
        val imageViewUser = headerView.findViewById<View>(R.id.nav_header_photo) as ImageView
        val navTitle = headerView.findViewById<View>(R.id.nav_header_title) as TextView
        val navSubtitle = headerView.findViewById<View>(R.id.num_header_subtitle) as TextView

        navTitle.text = Repository.appUser.name
        navSubtitle.text = Repository.appUser.email

        Glide
            .with(imageViewUser.context)
            .load(Repository.appUser.photoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .placeholder(R.drawable.ic_profile)
            .into(imageViewUser)
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
}
