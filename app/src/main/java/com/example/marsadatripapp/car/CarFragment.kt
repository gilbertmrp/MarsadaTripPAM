package com.example.marsadatripapp.car

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marsadatripapp.API.ApiClient
import com.example.marsadatripapp.API.CarsService
import com.example.marsadatripapp.R
import com.example.marsadatripapp.adapter.CarsAdapter
import com.example.marsadatripapp.helper.ProgressBarUtil
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarFragment : Fragment() {
    private lateinit var carsApi: CarsService
    private lateinit var adapter: CarsAdapter
    private lateinit var rvCars: RecyclerView
    private lateinit var pb: ProgressBarUtil
    private lateinit var sp: SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_car, container, false)
        rvCars = view.findViewById(R.id.rvCar)
        pb = ProgressBarUtil(requireContext())
        sp = SharedPref(requireContext())
        setRecyclerView()
        getCars()
        return view
    }

    private fun setRecyclerView() {
        adapter = CarsAdapter(arrayListOf())
        rvCars.adapter = adapter
        rvCars.layoutManager = LinearLayoutManager(this.context)
    }

    private fun getCars() {
        pb.showLoading()
        carsApi = ApiClient.getCars()
        carsApi.getData()
            .enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if(response.isSuccessful) {
                        val result = response.body()!!
                        Log.d("Debug", result.toString())
                        showData(response.body()!!)
                    }
                    pb.hideLoading()
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.d("Debug", t.toString())
                    pb.hideLoading()
                }
            })
    }

    private fun showData(data: Data) {
        val results = data.data
        adapter.setData(results)
    }
}