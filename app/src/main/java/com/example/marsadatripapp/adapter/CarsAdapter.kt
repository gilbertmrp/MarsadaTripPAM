package com.example.marsadatripapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marsadatripapp.R
import com.example.marsadatripapp.car.DetailCarActivity
import com.example.marsadatripapp.model.Car

class CarsAdapter(private val listCars: ArrayList<Car>) : RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_JENIS = "extra_jenis"
        const val EXTRA_LOKASI = "extra_lokasi"
        const val EXTRA_JLHPENUMPANG = "extra_jlhpenumpang"
        const val EXTRA_COOLER = "extra_cooler"
        const val EXTRA_TRANSMISI = "extra_transmisi"
        const val EXTRA_JLHPINTU = "extra_jlhpintu"
        const val EXTRA_ID = "extra_id"
    }

    inner class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgView: ImageView = view.findViewById(R.id.img_mobil)
        val merk: TextView = view.findViewById(R.id.tv_nama_mobil)
        val jenis: TextView = view.findViewById(R.id.tv_jenisMobil)
        val lokasi: TextView = view.findViewById(R.id.tvLocation)
        val jlh_penumpang: TextView = view.findViewById(R.id.tvPassanger)
        val cooler: TextView = view.findViewById(R.id.tvWinter)
        val transmisi: TextView = view.findViewById(R.id.tvTransmisi)
        val jlh_pintu: TextView = view.findViewById(R.id.tvDoor)
        val btnDetail: Button = view.findViewById(R.id.btnDetail)

        init {
            btnDetail.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val car = listCars[position]

                    val intent = Intent(view.context, DetailCarActivity::class.java)
                    intent.putExtra(EXTRA_NAME, car.merk)
                    intent.putExtra(EXTRA_JENIS, car.jenis)
                    intent.putExtra(EXTRA_LOKASI, car.lokasi)
                    intent.putExtra(EXTRA_JLHPENUMPANG, car.jlh_penumpang)
                    intent.putExtra(EXTRA_COOLER, car.cooler)
                    intent.putExtra(EXTRA_TRANSMISI, car.transmisi)
                    intent.putExtra(EXTRA_JLHPINTU, car.jlh_pintu)
                    intent.putExtra(EXTRA_ID, car.id)
                    view.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_car, parent, false)
        return CarViewHolder(view)
    }

    override fun getItemCount(): Int = listCars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val cars = listCars[position]
        holder.merk.text = cars.merk
        holder.jenis.text = cars.jenis
        holder.lokasi.text = cars.lokasi
        holder.jlh_penumpang.text = cars.jlh_penumpang
        holder.cooler.text = cars.cooler
        holder.transmisi.text = cars.transmisi
        holder.jlh_pintu.text = cars.jlh_pintu
//        holder.imgView.setImageResource(cars.gambar)
    }

    fun setData(data: List<Car>) {
        this.listCars.clear()
        this.listCars.addAll(data)
        notifyDataSetChanged()
    }

}