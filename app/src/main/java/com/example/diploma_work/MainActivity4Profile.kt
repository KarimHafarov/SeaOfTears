package com.example.diploma_work

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity4Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity4_profile)

        val maininfo: Button = findViewById<Button>(R.id.button_main)
        val statistics: Button = findViewById<Button>(R.id.button_statistic)
        val profile: Button = findViewById<Button>(R.id.button_profile)

        maininfo.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            startActivity(intent)
            finish()
        }

        statistics.setOnClickListener {
            val intent = Intent(this, MainActivity5Statistic::class.java)
            startActivity(intent)
            finish()
        }

        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        sp.edit().putString("TY", "9").commit()
        var nameemail:TextView = findViewById(R.id.nameemail)
        nameemail.text =  sp.getString("Name", "Не завантажилось")
        var logout:Button = findViewById<Button>(R.id.buttonLogOut)
        logout.setOnClickListener{
            sp.edit().putString("TY", "-9").commit()
            Toast.makeText(this, "Ви вийшли з облікового запису ", Toast.LENGTH_LONG ).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
}