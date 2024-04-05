package com.example.diploma_work

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma_work.databinding.ActivityMainActivity5StatisticBinding

class MainActivity5Statistic : AppCompatActivity() {

    private lateinit var binding: ActivityMainActivity5StatisticBinding
    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: Button
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var db: UsersDataBaseHelper
    private var adminId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainActivity5StatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextSearch = binding.editTextSearch
        buttonSearch = binding.buttonSearch
        recyclerViewUsers = binding.recyclerViewUsers
        db = UsersDataBaseHelper(this)

        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        usersAdapter = UsersAdapter(emptyList(), this, adminId)
        recyclerViewUsers.adapter = usersAdapter

        adminId = intent.getIntExtra("ADMIN_ID", -1)

        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchUsers(query)
            } else {
                Toast.makeText(this, "Пусте поле", Toast.LENGTH_SHORT).show()
            }
        }

        val maininfo: Button = findViewById(R.id.button_main)
        val statistics: Button = findViewById(R.id.button_statistic)
        val profile: Button = findViewById(R.id.button_profile)

        profile.setOnClickListener {
            val intent = Intent(this, MainActivity4Profile::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        maininfo.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(usersAdapter, this))
        itemTouchHelper.attachToRecyclerView(recyclerViewUsers)
    }

    private fun searchUsers(query: String) {
        val searchResults = db.searchUsersByAdminId(query, adminId)
        usersAdapter.refreshData(searchResults)
    }
}