package com.example.diploma_work

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.os.Handler


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        sp.edit().putString("TY", "9").commit()
        var nameemail:TextView = findViewById(R.id.nameemail)
        nameemail.text =  sp.getString("Name", "Не завантажилось")
        Handler().postDelayed({
            val intent = Intent(this, MainActivity3Main::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}