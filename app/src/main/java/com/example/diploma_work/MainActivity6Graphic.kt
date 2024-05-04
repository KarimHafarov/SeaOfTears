package com.example.diploma_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity6Graphic : AppCompatActivity() {

    private lateinit var adminDataBaseHelper: AdminDataBaseHelper
    private var adminId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity6_graphic)

        adminId = intent.getIntExtra("ADMIN_ID", -1)

        val mainInfo: Button = findViewById(R.id.button_main)
        val statistics: Button = findViewById(R.id.button_statistic)
        val profile: Button = findViewById(R.id.button_profile)

        mainInfo.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        statistics.setOnClickListener {
            val intent = Intent(this, MainActivity5Statistic::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        profile.setOnClickListener {
            val intent = Intent(this, MainActivity4Profile::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }


    }
}
