package com.example.marsadatripapp.helper

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.marsadatripapp.R

class ProgressBarUtil(private val context: Context) {
    private val progressBar: View? = (context as? Activity)?.findViewById(R.id.progressBar)

    fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar?.visibility = View.GONE
    }
}