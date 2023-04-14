package com.example.marsadatripapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.marsadatripapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        supportActionBar?.hide()

        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar))

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.car -> replaceFragment(CarFragment())
                R.id.cart -> replaceFragment(CartFragment())
                R.id.contact -> replaceFragment(ContactFragment())
            }

            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTarnsaction = fragmentManager.beginTransaction()
        fragmentTarnsaction.replace(R.id.frame_layout, fragment)
        fragmentTarnsaction.commit()
    }
}