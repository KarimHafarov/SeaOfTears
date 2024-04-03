package com.example.diploma_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity3CreateUsers : AppCompatActivity() {
    private lateinit var db: UsersDataBaseHelper
    private var adminId: Int = -1 // Поле для збереження ідентифікатора адміна

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity3_create_users)

        db = UsersDataBaseHelper(this)

        val backtoMain: Button = findViewById(R.id.button_close)
        val rankus: EditText = findViewById(R.id.editText_rank)
        val nameus: EditText = findViewById(R.id.editText_name)
        val fathernameus: EditText = findViewById(R.id.editText_fathername)
        val surnameus: EditText = findViewById(R.id.editText_surname)
        val button_save: Button = findViewById(R.id.button_save)

        adminId = intent.getIntExtra("ADMIN_ID", -1)

        backtoMain.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
            finish()
        }

        button_save.setOnClickListener {
            val name = nameus.text.toString()
            val surname = surnameus.text.toString()
            val rank = rankus.text.toString()
            val fathername = fathernameus.text.toString()
            when {
                rank.isEmpty() -> Toast.makeText(this, "Введіть звання", Toast.LENGTH_SHORT).show()
                fathername.isEmpty() -> Toast.makeText(this, "Введіть по батькові", Toast.LENGTH_SHORT).show()
                name.isEmpty() -> Toast.makeText(this, "Введіть ім'я", Toast.LENGTH_SHORT).show()
                surname.isEmpty() -> Toast.makeText(this, "Введіть прізвище", Toast.LENGTH_SHORT).show()
                else -> {
                    val user = User(0, rank, name, fathername, surname, "", "")
                    db.insertUser(user, adminId)
                    Toast.makeText(this, "Користувач збережений успішно", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity3Main::class.java)
                    intent.putExtra("ADMIN_ID", adminId)
                    startActivity(intent)

                    finish()
                }
            }
        }
    }
}