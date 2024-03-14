package com.example.diploma_work

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity3CreateUsers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity3_create_users)

        val backtoMain : Button = findViewById<Button>(R.id.button_close)
        val nameus: EditText = findViewById<EditText>(R.id.editText_name)
        val surnameus: EditText = findViewById<EditText>(R.id.editText_surname)
        val button_save : Button = findViewById(R.id.button_save)

        backtoMain.setOnClickListener {
            val intent = Intent(this, MainActivity3Main::class.java)
            startActivity(intent)
            finish()
        }

        FirebaseApp.initializeApp(this)
        val sp = getSharedPreferences("PCQ", Context.MODE_PRIVATE).edit()
        button_save.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        button_save.setOnClickListener {
            when {
                nameus.text.isEmpty() || nameus.text.contains("@") -> {
                    Toast.makeText(this, "Invalid name", Toast.LENGTH_LONG).show()
                }
                surnameus.text.isEmpty() || surnameus.text.contains("@") -> {
                    Toast.makeText(this, "Invalid name", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val db = FirebaseFirestore.getInstance()

                    val user_in_table = hashMapOf(
                        "name" to nameus.text.toString(),
                        "surname" to surnameus.text.toString()
                    )

                    db.collection("users_in_table")
                        .add(user_in_table)
                        .addOnSuccessListener { documentReference ->
                            sp.putString("Name", nameus.text.toString()).commit()
                            startActivity(Intent(this, MainActivity3Main::class.java))
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error, please try again", Toast.LENGTH_LONG).show()
                        }
                }
            }
        }

    }


}