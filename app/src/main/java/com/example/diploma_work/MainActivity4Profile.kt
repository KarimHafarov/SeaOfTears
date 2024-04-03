package com.example.diploma_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity4Profile : AppCompatActivity() {

    private lateinit var textViewNameEmail: TextView
    private lateinit var adminDataBaseHelper: AdminDataBaseHelper
    private var adminId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity4_profile)

        textViewNameEmail = findViewById(R.id.nameemail)
        adminDataBaseHelper = AdminDataBaseHelper(this)

        adminId = intent.getIntExtra("ADMIN_ID", -1)

        val mainInfo: Button = findViewById(R.id.button_main)
        val statistics: Button = findViewById(R.id.button_statistic)
        val profile: Button = findViewById(R.id.button_profile)

        mainInfo.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            finish()
        }

        statistics.setOnClickListener {
            val intent = Intent(this, MainActivity5Statistic::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            finish()
        }

        val login = readLoginFromDatabase()
        if (login != null) {
            textViewNameEmail.text = login
        } else {
            textViewNameEmail.text = "Логін не знайдено"
        }

        val logout: Button = findViewById(R.id.buttonLogOut)
        logout.setOnClickListener {
            Toast.makeText(this, "Ви вийшли з облікового запису ", Toast.LENGTH_LONG).show()
            textViewNameEmail.text = ""
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun readLoginFromDatabase(): String? {
        val adminId = intent.getIntExtra("ADMIN_ID", -1)
        return if (adminId != -1) {
            val admin = adminDataBaseHelper.getAdminById(adminId)
            admin?.login
        } else {
            null
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }
}
