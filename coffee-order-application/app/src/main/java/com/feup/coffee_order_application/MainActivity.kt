package com.feup.coffee_order_application

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.feup.coffee_order_application.fragments.Cart
import com.feup.coffee_order_application.fragments.Categories
import com.feup.coffee_order_application.fragments.Profile
//import com.feup.coffee_order_application.services.ProductController
import com.google.android.material.bottomnavigation.BottomNavigationView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar1))

        val home = Categories()
        val profile = Profile()
        val cart = Cart()
        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        setCurrentPage(home)

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentPage(home)
                R.id.cart->setCurrentPage(cart)
                R.id.person->setCurrentPage(profile)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            //implement some code here
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentPage(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, fragment)
            commit()
        }
    }
}