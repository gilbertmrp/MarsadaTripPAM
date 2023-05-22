package com.example.marsadatripapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.marsadatripapp.API.ApiClient
import com.example.marsadatripapp.API.CarsService
import com.example.marsadatripapp.adapter.AdapterSlider
import com.example.marsadatripapp.adapter.CarsAdapter
import com.example.marsadatripapp.adapter.NewCarsAdapter
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.Car
import com.example.marsadatripapp.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var vpSlider : ViewPager
    private lateinit var sp: SharedPref
    private lateinit var tvName: TextView
    private lateinit var adapter: NewCarsAdapter
    private lateinit var rvNewsCars: RecyclerView
    private lateinit var carsService: CarsService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        vpSlider = view.findViewById(R.id.vp_slider_car)
        rvNewsCars = view.findViewById(R.id.rvListCar)
        sp = SharedPref(requireContext())
//        setData()

        val listSlider = ArrayList<Int>()
        listSlider.add(R.drawable.car1)
        listSlider.add(R.drawable.car2)
        listSlider.add(R.drawable.car3)

        val adapterSlider = AdapterSlider(listSlider, activity)
        vpSlider.adapter = adapterSlider

        setRecyclerView()
        getCar()

        return view
    }

    private fun setRecyclerView() {
        adapter = NewCarsAdapter(arrayListOf())
        rvNewsCars.adapter = adapter
        rvNewsCars.layoutManager = LinearLayoutManager(this.context)
    }

    private fun getCar() {
        carsService = ApiClient.getCars()
        carsService.getData()
            .enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if(response.isSuccessful) {
                        val result = response.body()!!
                        Log.d("Debug", result.toString())
                        showData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.d("Debug", t.toString())
                }
            })
    }

    @SuppressLint("SetTextI18n")
//    private fun setData() {
//        val user = sp.getUser()!!
//        if(sp.getStatus()) {
//            tvName.text = "Selamat datang, ${user.name}!"
//        }else {
//            tvName.text = "Selamat datang Anonymous!"
//        }
//    }

    private fun showData(data: Data) {
        val results = data.data.take(3)
        adapter.setData(results)
    }
}