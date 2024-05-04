package com.example.diploma_work

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diploma_work.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var db: UsersDataBaseHelper
    private var userId: Int = -1
    private lateinit var calendarView: CalendarView
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UsersDataBaseHelper(this)
        calendarView = findViewById(R.id.calendarView)

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
        binding.updateEditText3.setText(user.father)
        binding.updateEditText1.setText(user.surname)
        binding.timeEditText1.setText(user.time)
        binding.dutyEditText1.setText(user.duty)
        binding.editTextComment.setText(user.comment)

        binding.updateimageView.setOnClickListener {
            val newRank = binding.updateEditText2.text.toString()
            val newFather = binding.updateEditText3.text.toString()
            val newName = binding.updateEditText.text.toString()
            val newSurname = binding.updateEditText1.text.toString()
            val newTime = binding.timeEditText1.text.toString()
            val newDuty = binding.dutyEditText1.text.toString()
            val newComment = binding.editTextComment.text.toString()
            val updatedUser = User(userId, newRank, newName, newFather, newSurname, newTime, newDuty, newComment)

            db.updateUser(updatedUser)
            finish()
            Toast.makeText(this, "Зміни збережено", Toast.LENGTH_LONG).show()
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            if (startDate == null) {
                startDate = selectedDate
            } else if (endDate == null) {
                endDate = selectedDate
                val formattedDates = "$startDate - $endDate"
                binding.editTextComment.setText(formattedDates)
            } else {
                startDate = null
                endDate = null
                Toast.makeText(this, "Обрано більше двох дат. Оберіть ще раз.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
