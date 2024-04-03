package com.example.diploma_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {

    private lateinit var textViewNameEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textViewNameEmail = findViewById(R.id.nameemail)

        val login = intent.getStringExtra("LOGIN")
        if (login != null) {
            textViewNameEmail.text = login
        } else {
            textViewNameEmail.text = "Логін не знайдено"
        }

        val adminId = intent.getIntExtra("ADMIN_ID", -1)

        Handler().postDelayed({
            if (adminId != -1) {
                val intent = Intent(this, MainActivity3Main::class.java)
                intent.putExtra("ADMIN_ID", adminId)
                startActivity(intent)
            } else {

                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 2000)
    }
}