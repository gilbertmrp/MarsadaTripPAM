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
import com.example.marsadatripapp.profile.ProfileActivity
import com.example.marsadatripapp.R
import com.example.marsadatripapp.databinding.ActivityEditProfileBinding
import com.example.marsadatripapp.helper.SharedPref
import com.example.marsadatripapp.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userService: UserService
    private lateinit var sp: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this,R.color.status_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24)
        supportActionBar?.title = Html.fromHtml("<font color='#00000'>Ubah Profile</font>")
        binding.nameUser.text = intent.getStringExtra(ProfileActivity.EXTRA_NAME)
        binding.emailUser.text = intent.getStringExtra(ProfileActivity.EXTRA_EMAIL)
        sp = SharedPref(this)

        binding.btnSave.setOnClickListener {
            ubahProfile()
        }
    }

    private fun ubahProfile() {
        val name = binding.edtFullname.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()

        var isEmptyField = false
        var invalid = false

        if (name.isEmpty()) {
            isEmptyField = true
            binding.edtFullname.error = "Full name is required"
            binding.edtFullname.requestFocus()
        }

        if (email.isEmpty()) {
            isEmptyField = true
            binding.editEmail.error = "Email is required"
            binding.editEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            invalid = true
            binding.editEmail.error = "Please enter a valid email"
            binding.editEmail.requestFocus()
        }

        if(!isEmptyField && !invalid) {
            userService = ApiClient.updateUser()
            val id = SharedPref(this).getUser()!!.id
            userService.update(id, name, email)
                .enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        if(response.isSuccessful) {
                            val res = response.body()!!
                            sp.setUser(res.data)
                            val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this@EditProfileActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        Log.d("Debug", t.toString())
                    }

                })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}