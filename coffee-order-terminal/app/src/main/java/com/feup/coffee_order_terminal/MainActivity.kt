package com.feup.coffee_order_terminal

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.feup.coffee_order_terminal.ui.OrderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orderList = OrderList()
        val qrcode = QRCodeFragment()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)


        setCurrentPage(orderList)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_terminal ->setCurrentPage(orderList)
                R.id.qrcode ->setCurrentPage(qrcode)
            }
            true
        }

        //val manager: FragmentManager =supportFragmentManager
        val orderFragment = OrderFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main, orderFragment).addToBackStack(null).commit()
    }

    private fun setCurrentPage(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }
}