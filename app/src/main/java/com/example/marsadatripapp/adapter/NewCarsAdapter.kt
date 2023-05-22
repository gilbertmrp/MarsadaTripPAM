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
import org.w3c.dom.Text

class NewCarsAdapter(private val listCars: ArrayList<Car>) : RecyclerView.Adapter<NewCarsAdapter.NewCarsHolder>() {
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

    inner class NewCarsHolder(view: View) : RecyclerView.ViewHolder(view) {

//        val imgView: ImageView = view.findViewById(R.id.img_mobil)
        val merk: TextView = view.findViewById(R.id.NewCarsName)
        val lokasi: TextView = view.findViewById(R.id.tvLocationNewCars)
        val tvNewPassanger: TextView = view.findViewById(R.id.tvNewPassanger)
        val btnNewCars: Button = view.findViewById(R.id.btnBookNewCars)

        init {
            btnNewCars.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val car = listCars[position]

                    val intent = Intent(view.context, DetailCarActivity::class.java)
                    intent.putExtra(CarsAdapter.EXTRA_NAME, car.merk)
                    intent.putExtra(CarsAdapter.EXTRA_JENIS, car.jenis)
                    intent.putExtra(CarsAdapter.EXTRA_LOKASI, car.lokasi)
                    intent.putExtra(CarsAdapter.EXTRA_JLHPENUMPANG, car.jlh_penumpang)
                    intent.putExtra(CarsAdapter.EXTRA_COOLER, car.cooler)
                    intent.putExtra(CarsAdapter.EXTRA_TRANSMISI, car.transmisi)
                    intent.putExtra(CarsAdapter.EXTRA_JLHPINTU, car.jlh_pintu)
                    intent.putExtra(EXTRA_ID, car.id)
                    view.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCarsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_new_cars, parent, false)
        return NewCarsHolder(view)
    }

    override fun getItemCount(): Int = listCars.size

    override fun onBindViewHolder(holder: NewCarsHolder, position: Int) {
        val cars = listCars[position]
        holder.merk.text = cars.merk
        holder.lokasi.text = cars.lokasi
        holder.tvNewPassanger.text = cars.jlh_penumpang
//        holder.imgView.setImageResource(cars.gambar)
    }

    fun setData(data: List<Car>) {
        this.listCars.clear()
        this.listCars.addAll(data)
        notifyDataSetChanged()
    }
}

