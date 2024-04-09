package com.example.diploma_work

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma_work.databinding.ActivityMainActivity3MainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity3Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainActivity3MainBinding
    private lateinit var db: UsersDataBaseHelper
    private lateinit var usersAdapter: UsersAdapter
    private var adminId: Int = -1

    private lateinit var statistics: Button
    private lateinit var profile: Button
    private lateinit var addUser: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainActivity3MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adminId = intent.getIntExtra("ADMIN_ID", -1)

        db = UsersDataBaseHelper(this)

        usersAdapter = UsersAdapter(db.getAllUsers(adminId), this, adminId)

        binding.UsersRecycleView.layoutManager = LinearLayoutManager(this)
        binding.UsersRecycleView.adapter = usersAdapter

        addUser = findViewById(R.id.add_user)
        profile = findViewById(R.id.button_profile)
        statistics = findViewById(R.id.button_statistic)

        val exportExcelButton: Button = findViewById(R.id.export_excel_button)
        exportExcelButton.setOnClickListener {
            exportDataToExcel()
        }

        addUser.setOnClickListener {
            val intent = Intent(this, MainActivity3CreateUsers::class.java)
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

        statistics.setOnClickListener {
            val intent = Intent(this, MainActivity5Statistic::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(usersAdapter, this))
        itemTouchHelper.attachToRecyclerView(binding.UsersRecycleView)
    }

    private fun exportDataToExcel() {
        val userList = db.getAllUsers(adminId)
        if (userList.isNotEmpty()) {
            val filePath = ExcelExporter.exportDataToExcel(this, userList)
            if (filePath != null) {
                val file = File(filePath)
                ExcelExporter.openExcelFile(this, file)
            } else {
                Toast.makeText(this, "Не вдалося експортувати дані в Excel", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Немає даних для експорту", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        usersAdapter.refreshData(db.getAllUsers(adminId))
    }
}
