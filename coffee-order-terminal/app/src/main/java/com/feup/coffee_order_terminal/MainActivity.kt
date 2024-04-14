package com.feup.coffee_order_terminal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.feup.coffee_order_terminal.ui.fragment.OrderListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orderList = OrderListFragment()
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
        /*val orderFragment = OrderFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main, orderFragment).addToBackStack(null).commit()*/
    }

    private fun setCurrentPage(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }
}