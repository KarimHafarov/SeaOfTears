package com.example.diploma_work

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity5Statistic : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity5_statistic)

        val maininfo: Button = findViewById<Button>(R.id.button_main)
        val statistics: Button = findViewById<Button>(R.id.button_statistic)
        val profile: Button = findViewById<Button>(R.id.button_profile)

        profile.setOnClickListener {
            val intent = Intent(this, MainActivity4Profile::class.java)
            startActivity(intent)
            finish()
        }

        maininfo.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            startActivity(intent)
            finish()
        }
    }
}