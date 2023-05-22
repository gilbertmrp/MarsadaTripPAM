package com.example.marsadatripapp.car

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marsadatripapp.API.ApiClient
import com.example.marsadatripapp.API.CarsService
import com.example.marsadatripapp.R
import com.example.marsadatripapp.adapter.HistoryCarAdapter
import com.example.marsadatripapp.databinding.ActivityHistoryCarBinding
import com.example.marsadatripapp.helper.ProgressBarUtil
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.Detail
import com.example.marsadatripapp.model.HistoryCar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryCarBinding
    private lateinit var adapter: HistoryCarAdapter
    private lateinit var carsService: CarsService
    private lateinit var pb: ProgressBarUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)
        supportActionBar?.title = Html.fromHtml("<font color='#00000'>Riwayat Rental</font>")
        pb = ProgressBarUtil(this)
        setRecyclerView()
        getHistoryCar()

    }

    private fun getHistoryCar() {
        pb.showLoading()
        val id = SharedPref(this).getUser()!!.id
        carsService = ApiClient.getHistoryCars()
        carsService.getHistoryCar(id)
            .enqueue(object : Callback<HistoryCar> {
                override fun onResponse(
                    call: Call<HistoryCar>,
                    response: Response<HistoryCar>
                ) {
                    if(response.isSuccessful) {
                        val result = response.body()!!
                        Log.d("Debug", result.toString())
                        showData(result.data)
                    }
                    pb.hideLoading()
                }

                override fun onFailure(call: Call<HistoryCar>, t: Throwable) {
                    Log.d("Debug", t.toString())
                    pb.hideLoading()
                }

            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setRecyclerView() {
        adapter = HistoryCarAdapter(this, arrayListOf())
        binding.rvHistoryCar.adapter = adapter
        binding.rvHistoryCar.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showData(cars: List<Detail>) {
        adapter.setData(cars)
    }
}