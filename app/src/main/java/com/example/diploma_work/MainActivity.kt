package com.example.diploma_work

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.diploma_work.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBaseHelper: AdminDataBaseHelper

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (!isGranted) {
                    Toast.makeText(this, "Permission ${it.key} was not granted.", Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataBaseHelper = AdminDataBaseHelper(this)

        requestPermissions()

        val usersDataBaseHelper = UsersDataBaseHelper(this)
        usersDataBaseHelper.writableDatabase

        binding.buttonSetIn.setOnClickListener {
            val loginLogin = binding.editTextName.text.toString()
            val loginPassword = binding.editTextPassword.text.toString()
            loginDatabase(loginLogin, loginPassword)
        }
        binding.textViewCreateUser.setOnClickListener {
            Log.d("MainActivity", "textViewCreateUser clicked")
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down)
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

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }
}
