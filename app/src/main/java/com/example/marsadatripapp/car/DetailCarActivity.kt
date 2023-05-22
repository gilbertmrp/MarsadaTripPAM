package com.example.marsadatripapp.car

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.content.ContextCompat
import com.example.marsadatripapp.R
import com.example.marsadatripapp.adapter.CarsAdapter
import com.example.marsadatripapp.databinding.ActivityDetailCarBinding
import com.example.marsadatripapp.helper.SharedPref

class DetailCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCarBinding
    private lateinit var sp: SharedPref

    companion object {
        const val EXTRA_NAME_USER = "extra_name"
        const val EXTRA_NAME_CAR = "extra_car"
        const val EXTRA_ID = "extra_id"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)
        supportActionBar?.title = Html.fromHtml("<font color='#00000'>Detail Mobil</font>")
        val namaMobil = intent.getStringExtra(CarsAdapter.EXTRA_NAME)
        val idMobil = intent.getIntExtra(CarsAdapter.EXTRA_ID, 0)
        binding.tvJenisMobil.text = intent.getStringExtra(CarsAdapter.EXTRA_JENIS)
        binding.tvLokasi.text = intent.getStringExtra(CarsAdapter.EXTRA_LOKASI)
        binding.tvNama.text = namaMobil
        binding.tvPassanger.text = intent.getStringExtra(CarsAdapter.EXTRA_JLHPENUMPANG)
        binding.tvCooler.text = intent.getStringExtra(CarsAdapter.EXTRA_COOLER)
        binding.tvJlhPintu.text = intent.getStringExtra(CarsAdapter.EXTRA_JLHPINTU)
        binding.tvTransmisi.text = intent.getStringExtra(CarsAdapter.EXTRA_TRANSMISI)
        sp = SharedPref(this)

        binding.btnPesan.setOnClickListener {
            val intent = Intent(this, BookCarActivity::class.java).apply {
                putExtra(EXTRA_NAME_USER, sp.getUser()!!.name)
                putExtra(EXTRA_NAME_CAR, namaMobil)
                putExtra(EXTRA_ID, idMobil)
            }
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}