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

        val order_list = OrderList()
        val qrcode = QRCodeFragment()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Orders")
        setSupportActionBar(toolbar)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        setCurrentPage(order_list)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.order_list_fragment ->setCurrentPage(order_list)
                R.id.qr_code_reader_fragment ->setCurrentPage(qrcode)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.)
        return super.onCreateOptionsMenu(menu)
    }
}