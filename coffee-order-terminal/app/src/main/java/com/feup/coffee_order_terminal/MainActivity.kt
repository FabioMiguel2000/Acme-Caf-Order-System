package com.feup.coffee_order_terminal

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val orderList = OrderList()
        val qrcode = QRCodeFragment()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        toolbar.setTitle("Orders")
        setCurrentPage(orderList)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_terminal ->setCurrentPage(orderList)
                R.id.qrcode ->setCurrentPage(qrcode)
            }
            true
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