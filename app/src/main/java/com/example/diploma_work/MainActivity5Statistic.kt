package com.example.diploma_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity5Statistic : AppCompatActivity() {
    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: Button
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var db: UsersDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity5_statistic)

        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        db = UsersDataBaseHelper(this)

        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        usersAdapter = UsersAdapter(emptyList(), this)
        recyclerViewUsers.adapter = usersAdapter

        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchUsers(query)
            } else {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }

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

    private fun searchUsers(query: String) {
        val searchResults = db.searchUsers(query)
        usersAdapter.refreshData(searchResults)
    }
}
