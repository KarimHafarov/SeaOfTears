package com.example.diploma_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.diploma_work.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBaseHelper: AdminDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataBaseHelper = AdminDataBaseHelper(this)

        binding.buttonSetIn.setOnClickListener {
            val loginLogin = binding.editTextName.text.toString()
            val loginPassword = binding.editTextPassword.text.toString()
            loginDatabase(loginLogin, loginPassword)
        }
        binding.textViewCreateUser.setOnClickListener {
            Log.d("MainActivity", "textViewCreateUser clicked")
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginDatabase(login: String, password: String) {
        val adminExists = dataBaseHelper.readAdmin(login, password)
        if (adminExists) {
            Toast.makeText(this, "Авторизація успішна", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("LOGIN", login)

            val adminId = dataBaseHelper.getAdminId(login, password)
            intent.putExtra("ADMIN_ID", adminId)

            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Невірні дані", Toast.LENGTH_LONG).show()
        }
    }
}
