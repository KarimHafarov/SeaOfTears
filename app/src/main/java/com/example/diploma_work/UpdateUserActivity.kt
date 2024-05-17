package com.example.diploma_work

import android.os.Bundle
import android.widget.CalendarView
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
        user?.let {
            binding.updateEditText2.setText(it.rank)
            binding.updateEditText.setText(it.name)
            binding.updateEditText3.setText(it.father)
            binding.updateEditText1.setText(it.surname)
            binding.timeEditText1.setText(it.time)
            binding.dutyEditText1.setText(it.duty)
            binding.editTextComment.setText(it.comment)
        }

        binding.updateimageView.setOnClickListener {
            val updatedUser = User(
                userId,
                binding.updateEditText2.text.toString(),
                binding.updateEditText.text.toString(),
                binding.updateEditText3.text.toString(),
                binding.updateEditText1.text.toString(),
                binding.timeEditText1.text.toString(),
                binding.dutyEditText1.text.toString(),
                binding.editTextComment.text.toString()
            )
            db.updateUser(updatedUser)
            finish()
            Toast.makeText(this, "Зміни збережено", Toast.LENGTH_LONG).show()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            if (startDate == null) {
                startDate = selectedDate
            } else if (endDate == null) {
                endDate = selectedDate
                binding.editTextComment.setText("$startDate - $endDate")
            } else {
                startDate = null
                endDate = null
                Toast.makeText(this, "Обрано більше двох дат. Оберіть ще раз.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
