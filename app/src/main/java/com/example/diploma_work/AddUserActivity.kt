package com.example.diploma_work

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.diploma_work.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var db: UsersDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UsersDataBaseHelper(this)

        binding.imageView.setOnClickListener {
            val name = binding.EditText.text.toString()
            val rank = binding.EditText2.text.toString()
            val fathername = binding.EditText3.text.toString()
            val surname = binding.EditText1.text.toString()
            val timeString = binding.timeEditText1.text.toString()

            val user = User(0, name,rank, fathername, surname, timeString)
            db.insertUser(user)
            finish()
            Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show()
        }

    }
}