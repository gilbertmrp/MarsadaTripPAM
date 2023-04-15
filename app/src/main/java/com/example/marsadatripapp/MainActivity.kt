package com.example.marsadatripapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.profile_account -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
            }
        }

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.car -> replaceFragment(CarFragment())
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