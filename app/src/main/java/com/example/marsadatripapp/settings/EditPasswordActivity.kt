package com.example.marsadatripapp.settings

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.marsadatripapp.API.ApiClient
import com.example.marsadatripapp.API.UserService
import com.example.marsadatripapp.MainActivity
import com.example.marsadatripapp.R
import com.example.marsadatripapp.databinding.ActivityEditPasswordBinding
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPasswordBinding
    private lateinit var sp: SharedPref
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this,R.color.status_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)
        supportActionBar?.title = Html.fromHtml("<font color='#00000'>Ubah Kata Sandi</font>")
        sp = SharedPref(this)
        binding.btnSave.setOnClickListener {
            ubahPassword()
        }
    }

    private fun ubahPassword() {
        val email = binding.edtEmail.text.toString().trim()
        val targetPass = binding.edtTargetPass.text.toString().trim()
        val confirmPass = binding.edtConfirmPass.text.toString().trim()


        var isEmptyField = false
        var isInvalid = false

        if(email.isEmpty()) {
            isEmptyField = true
            binding.edtEmail.error = "Email harus diisi"
            binding.edtEmail.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isInvalid = true
            binding.edtEmail.error = "Tolong masukkan email yang valid"
            binding.edtEmail.requestFocus()
        }else if(email != getEmailUserFromDB()){
            isInvalid = true
            binding.edtEmail.error = "Email tidak sama"
            binding.edtEmail.requestFocus()
        }

        if(targetPass.isEmpty()) {
            isEmptyField = true
            binding.edtTargetPass.error = "Kata sandi baru harus diisi"
        }else if(targetPass.length < 8) {
            isInvalid = true
            binding.edtTargetPass.error = "Kata sandi minimal harus 8 karakter"
            binding.edtTargetPass.requestFocus()
        }

        if(confirmPass.isEmpty()) {
            isEmptyField = true
            binding.edtConfirmPass.error = "Konfirmasi kata sandi harus diisi"
            binding.edtConfirmPass.requestFocus()
        }else if(confirmPass.length < 8) {
            isInvalid = true
            binding.edtConfirmPass.error = "Kata sandi minimal harus 8 karakter"
            binding.edtConfirmPass.requestFocus()
        }else if(confirmPass != targetPass) {
            isInvalid = true
            binding.edtConfirmPass.error = "Kata sandi tidak sama"
            binding.edtConfirmPass.requestFocus()
        }

        if(!isEmptyField && !isInvalid) {
            userService = ApiClient.updatePass()
            val id = sp.getUser()!!.id
            userService.updatePass(id,confirmPass)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        val res = response.body()!!
                        val intent = Intent(this@EditPasswordActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        Toast.makeText(this@EditPasswordActivity, res.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        Log.d("Debug", t.toString())
                    }

                })
        }
    }

    private fun getEmailUserFromDB() : String {
        return sp.getUser()!!.email
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}