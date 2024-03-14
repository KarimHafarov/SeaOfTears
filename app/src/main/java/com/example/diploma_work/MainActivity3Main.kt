package com.example.diploma_work

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity3Main : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity3_main)
        val maininfo: Button = findViewById<Button>(R.id.button_main)
        val statistics: Button = findViewById<Button>(R.id.button_statistic)
        val profile: Button = findViewById<Button>(R.id.button_profile)
        val adduser : Button = findViewById<Button>(R.id.add_user)

        profile.setOnClickListener {
            val intent = Intent(this, MainActivity4Profile::class.java)
            startActivity(intent)
            finish()
        }

        statistics.setOnClickListener {
            val intent = Intent(this, MainActivity5Statistic::class.java)
            startActivity(intent)
            finish()
        }

        adduser.setOnClickListener {
            val intent = Intent(this, MainActivity3CreateUsers::class.java)
            startActivity(intent)
        }
    }
}