package com.example.marsadatripapp.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.content.ContextCompat
import com.example.marsadatripapp.MainActivity
import com.example.marsadatripapp.R
import com.example.marsadatripapp.auth.LoginActivity
import com.example.marsadatripapp.car.HistoryCarActivity
import com.example.marsadatripapp.databinding.ActivityProfileBinding
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.settings.AboutActivity
import com.example.marsadatripapp.settings.EditPasswordActivity
import com.example.marsadatripapp.settings.EditProfileActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sp: SharedPref

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASSWORD = "extra_password"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)
        supportActionBar?.title = Html.fromHtml("<font color='#00000'>Profilku</font>")
        sp = SharedPref(this)

        onClickListener()
        setData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onClickListener() {

        binding.btnBusiness.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.keluar.setOnClickListener {
            sp.setStatusLogin(false)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        binding.btnRiwayat.setOnClickListener {
            val intent = Intent(this, HistoryCarActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            val user = sp.getUser()!!
            intent.putExtra(EXTRA_NAME, user.name)
            intent.putExtra(EXTRA_EMAIL, user.email)
            intent.putExtra(EXTRA_PASSWORD, user.password)
            startActivity(intent)
        }

        binding.btnUbahPassword.setOnClickListener {
            val intent = Intent(this, EditPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setData() {
        if(sp.getUser() == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            return
        }

        val user = sp.getUser()!!
        binding.nameUser.text = user.name
        binding.emailUser.text = user.email
    }
}