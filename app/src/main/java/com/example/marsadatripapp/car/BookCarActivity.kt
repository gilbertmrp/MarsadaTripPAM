package com.example.marsadatripapp.car

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.marsadatripapp.API.ApiClient
import com.example.marsadatripapp.API.CarsService
import com.example.marsadatripapp.MainActivity
import com.example.marsadatripapp.R
import com.example.marsadatripapp.databinding.ActivityBookCarBinding
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.Booked
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class BookCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookCarBinding
    private lateinit var carsService: CarsService
    private lateinit var sp: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)
        supportActionBar?.title = Html.fromHtml("<font color='#00000'>Atur Tanggal</font>")
        binding.tvFullName.text = intent.getStringExtra(DetailCarActivity.EXTRA_NAME_USER)
        binding.tvMobil.text = intent.getStringExtra(DetailCarActivity.EXTRA_NAME_CAR)
        sp = SharedPref(this)

        bookingCar()
    }

    @SuppressLint("SetTextI18n")
    private fun bookingCar() {
        binding.selectedDayStart.setOnClickListener {
            showDatePickerDialog(binding.selectedDayStart)
        }

        binding.selectedDayBack.setOnClickListener {
            showDatePickerDialog(binding.selectedDayBack)
        }

        binding.rgDriver.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioYa -> {
                }
                R.id.radioNo -> {
                }
            }
        }

        binding.btnBook.setOnClickListener {
            if (binding.selectedDayStart.text.isEmpty()) {
                Toast.makeText(this@BookCarActivity, "Harap pilih tanggal berangkat", Toast.LENGTH_SHORT).show()
            } else if(binding.selectedDayBack.text.isEmpty()){
                Toast.makeText(this@BookCarActivity, "Harap pilih tanggal Kembali", Toast.LENGTH_SHORT).show()
            }else if (!binding.radioYa.isChecked && !binding.radioNo.isChecked) {
                Toast.makeText(this@BookCarActivity, "Harap pilih opsi driver", Toast.LENGTH_SHORT).show()
            } else {
                if (sp.getStatus()) {
                    carsService = ApiClient.bookedCar()
                    val idUser = sp.getUser()?.id ?: 0
                    val idCar = intent.getIntExtra(DetailCarActivity.EXTRA_ID, 0)
                    val startDate = binding.selectedDayStart.text.toString()
                    val endDate = binding.selectedDayBack.text.toString()
                    val driver = if (binding.radioYa.isChecked) "Ya" else "No"

                    carsService.postBookedCar(idUser, idCar, startDate, endDate, driver)
                        .enqueue(object : Callback<Booked> {
                            override fun onResponse(call: Call<Booked>, response: Response<Booked>) {
                                if (response.isSuccessful) {
                                    val res = response.body()!!
                                    Log.d("Debug", res.data.toString())
                                    val intent = Intent(this@BookCarActivity, FinisihBookCarActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<Booked>, t: Throwable) {
                                Log.d("Debug", t.toString())
                            }
                        })
                } else {
                    AlertDialog.Builder(this@BookCarActivity)
                        .setTitle("Gagal")
                        .setMessage("Harap login terlebih dahulu")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }
        }
    }

    private fun showDatePickerDialog(textView: TextView) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateString = dateFormat.format(selectedDate.time)
                textView.text = dateString
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}