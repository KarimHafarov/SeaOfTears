package com.example.diploma_work

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diploma_work.databinding.ActivityMainActivity3MainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity3Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainActivity3MainBinding
    private lateinit var db: UsersDataBaseHelper
    private lateinit var usersAdapter: UsersAdapter

    private lateinit var statistics: Button
    private lateinit var profile: Button
    private lateinit var addUser: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainActivity3MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UsersDataBaseHelper(this)
        usersAdapter = UsersAdapter(db.getAllUsers(), this)

        binding.UsersRecycleView.layoutManager = LinearLayoutManager(this)
        binding.UsersRecycleView.adapter = usersAdapter

        addUser = findViewById(R.id.add_user)
        profile = findViewById(R.id.button_profile)
        statistics = findViewById(R.id.button_statistic)

        addUser.setOnClickListener {
            val intent = Intent(this, MainActivity3CreateUsers::class.java)
            startActivity(intent)
        }

        profile.setOnClickListener {
            startActivity(Intent(this, MainActivity4Profile::class.java))
            finish()
        }

        statistics.setOnClickListener {
            startActivity(Intent(this, MainActivity5Statistic::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        usersAdapter.refreshData(db.getAllUsers())
    }
}
