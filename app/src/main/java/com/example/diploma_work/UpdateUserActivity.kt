package com.example.diploma_work

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.diploma_work.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var db: UsersDataBaseHelper
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UsersDataBaseHelper(this)

        userId = intent.getIntExtra("user_id", -1)
        if (userId == -1) {
            finish()
            return
        }

        val user = db.getUserById(userId)
        if (user == null) {
            finish()
            return
        }

        binding.updateEditText2.setText(user.rank)
        binding.updateEditText.setText(user.name)
        binding.updateEditText3.setText(user.fathername)
        binding.updateEditText1.setText(user.surname)
        binding.timeEditText1.setText(user.time)
        binding.dutyEditText1.setText(user.duty)

        binding.updateimageView.setOnClickListener {
            val newRank = binding.updateEditText2.text.toString()
            val newFathername = binding.updateEditText3.text.toString()
            val newName = binding.updateEditText.text.toString()
            val newSurname = binding.updateEditText1.text.toString()
            val newTime = binding.timeEditText1.text.toString()
            val newDuty = binding.dutyEditText1.text.toString()
            val updatedUser = User(userId,newRank,newFathername, newName, newSurname, newTime, newDuty)

            db.updateUser(updatedUser)
            finish()
            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show()
        }
    }
}
