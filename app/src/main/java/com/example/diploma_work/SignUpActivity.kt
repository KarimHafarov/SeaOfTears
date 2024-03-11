package com.example.diploma_work

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        FirebaseApp.initializeApp(this)
        val sp = getSharedPreferences("PC", Context.MODE_PRIVATE).edit()
        val signin: TextView = findViewById(R.id.textViewCreateUser)
        signin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val name: TextView = findViewById(R.id.editTextName)
        val password: TextView = findViewById(R.id.editTextPassword)
        val button: ConstraintLayout = findViewById(R.id.button_SetIn)

        button.setOnClickListener {
            when {
                name.text.isEmpty() || name.text.contains("@") -> {
                    Toast.makeText(this, "Invalid name", Toast.LENGTH_LONG).show()
                }
                password.text.isEmpty() || password.text.length < 8 -> {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val db = FirebaseFirestore.getInstance()

                    val user = hashMapOf(
                        "name" to name.text.toString(),
                        "password" to password.text.toString()
                    )

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            sp.putString("Name", name.text.toString()).commit()
                            startActivity(Intent(this, MainActivity2::class.java))
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error, please try again", Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
    }
}
