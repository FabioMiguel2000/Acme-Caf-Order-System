package com.feup.coffee_order_application.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.service.SessionManager
import com.feup.coffee_order_application.ui.fragment.CartFragment
import com.feup.coffee_order_application.ui.fragment.HomeFragment
import com.feup.coffee_order_application.ui.fragment.ProfileFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar1))

        val home = HomeFragment()
        val profile = ProfileFragment()
        val cart = CartFragment()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        setCurrentPage(home)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->setCurrentPage(home)
                R.id.cart ->setCurrentPage(cart)
                R.id.person ->setCurrentPage(profile)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.logout -> {
                SessionManager(this).clearSession()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setCurrentPage(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }
}