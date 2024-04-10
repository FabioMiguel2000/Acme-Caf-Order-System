package com.feup.coffee_order_terminal.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.feup.coffee_order_terminal.ui.fragment.QRCodeFragment
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.ui.fragment.OrderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val orderList = OrderFragment()
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