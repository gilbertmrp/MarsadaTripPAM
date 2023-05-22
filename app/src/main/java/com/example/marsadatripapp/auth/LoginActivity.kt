package com.example.marsadatripapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.marsadatripapp.API.ApiClient
import com.example.marsadatripapp.API.UserService
import com.example.marsadatripapp.MainActivity
import com.example.marsadatripapp.R
import com.example.marsadatripapp.databinding.ActivityLoginBinding
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userApi: UserService
    private lateinit var userService: UserService

    private lateinit var sp: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar))
        userApi = ApiClient.loginUser()

        sp = SharedPref(this)

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        var isEmptyField = false
        var invalid = false

        if (email.isEmpty()) {
            isEmptyField = true
            binding.edtEmail.error = "Email is required"
            binding.edtEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            invalid = true
            binding.edtEmail.error = "Please enter a valid email"
            binding.edtEmail.requestFocus()
        }

        if (password.isEmpty()) {
            isEmptyField = true
            binding.edtPassword.error = "Password is required"
            binding.edtPassword.requestFocus()
        } else if (password.length < 8) {
            invalid = true
            binding.edtPassword.error = "Password must be at least 8 characters"
            binding.edtPassword.requestFocus()
        }

        if(!isEmptyField && !invalid) {
            userApi.login(email, password)
                .enqueue(object : Callback<ResponseModel>{
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        if(response.isSuccessful) {
                            val userResponse = response.body()!!
                            if(userResponse.success == 1) {
                                sp.setStatusLogin(true)
                                sp.setUser(userResponse.data)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this@LoginActivity, "Halo "+userResponse.data.name, Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            AlertDialog.Builder(this@LoginActivity)
                                .setTitle("Login Gagal")
                                .setMessage("Alamat pengguna tidak dikenali")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        AlertDialog.Builder(this@LoginActivity)
                            .setTitle("Login Failed")
                            .setMessage(t.message)
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }

                })
        }
    }

}