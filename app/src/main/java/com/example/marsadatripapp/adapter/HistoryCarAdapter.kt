package com.example.marsadatripapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marsadatripapp.R
import com.example.marsadatripapp.model.Car
import com.example.marsadatripapp.model.Detail
import com.example.marsadatripapp.model.HistoryCar
import org.w3c.dom.Text

class HistoryCarAdapter(
        private val context: Context,
        private val carList: ArrayList<Detail>
    ) : RecyclerView.Adapter<HistoryCarAdapter.HistoryCarHolder>() {

    inner class HistoryCarHolder(view: View) : RecyclerView.ViewHolder(view) {
        val merk: TextView = view.findViewById(R.id.tvHistoryCarName)
        val lokasi: TextView = view.findViewById(R.id.tvHistoryLocCar)
        val jlh_penumpang: TextView = view.findViewById(R.id.tvPassanger)
        val cooler: TextView = view.findViewById(R.id.tvWinter)
        val transmisi: TextView = view.findViewById(R.id.tvTransmisi)
        val jlh_pintu: TextView = view.findViewById(R.id.tvDoor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCarHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.list_history_car, parent, false)
        return HistoryCarHolder(view)
    }

    override fun getItemCount(): Int = carList.size

    override fun onBindViewHolder(holder: HistoryCarHolder, position: Int) {
        val hCars = carList[position]
        holder.merk.text = hCars.car.merk
        holder.lokasi.text = hCars.car.lokasi
        holder.jlh_penumpang.text = hCars.car.jlh_penumpang
        holder.cooler.text = hCars.car.cooler
        holder.transmisi.text = hCars.car.transmisi
        holder.jlh_pintu.text = hCars.car.jlh_pintu
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Detail>) {
        this.carList.clear()
        this.carList.addAll(data)
        notifyDataSetChanged()
    }

}
