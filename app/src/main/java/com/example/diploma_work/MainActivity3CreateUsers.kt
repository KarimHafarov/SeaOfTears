package com.example.diploma_work

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity3CreateUsers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity3_create_users)

        val backtoMain: Button = findViewById<Button>(R.id.button_close)
        val rankus: EditText = findViewById<EditText>(R.id.editText_rank)
        val nameus: EditText = findViewById<EditText>(R.id.editText_name)
        val fathernameus: EditText = findViewById<EditText>(R.id.editText_fathername)
        val surnameus: EditText = findViewById<EditText>(R.id.editText_surname)
        val button_save: Button = findViewById(R.id.button_save)

        val db = UsersDataBaseHelper(this)

        backtoMain.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            startActivity(intent)
            finish()
        }

        button_save.setOnClickListener {
            val name = nameus.text.toString()
            val surname = surnameus.text.toString()
            val rank = rankus.text.toString()
            val fathername = fathernameus.text.toString()

            when {
                rank.isEmpty() -> {
                    Toast.makeText(this, "Please enter a rank", Toast.LENGTH_SHORT).show()
                }
                fathername.isEmpty() -> {
                    Toast.makeText(this, "Please enter a fathername", Toast.LENGTH_SHORT).show()
                }
                name.isEmpty() -> {
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                }
                surname.isEmpty() -> {
                    Toast.makeText(this, "Please enter a surname", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = User(0, rank, name, fathername, surname,"", "")
                    db.insertUser(user)
                    Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity3Main::class.java))
                    finish()
                }
            }
        }
    }
}
