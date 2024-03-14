package com.example.diploma_work

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        if(sp.getString("TY", "-9")!= "-9"){
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }
        else {
            var createuser: TextView = findViewById(R.id.textViewCreateUser)
            createuser.setOnClickListener {
                var intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val name: TextView = findViewById(R.id.editTextName)
        val password: TextView = findViewById(R.id.editTextPassword)
        val button: ConstraintLayout = findViewById(R.id.button_SetIn)
        var db = Firebase.firestore
        var df = false
        button.setOnClickListener{
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.getString("name")==name.text.toString()){
                            if(document.getString("password")== password.text.toString()) {
                                df = true;
                                sp.edit().putString("Name", name.text.toString()).commit()
                                startActivity(Intent(this, MainActivity2::class.java))
                                finish()
                            }
                            else if(document.getString("password")!= password.text){
                                password.text=""
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error, please try again", Toast.LENGTH_LONG).show()
                }
            var h = Handler()
            h.postDelayed({
                if(df == false){
                    Toast.makeText(this, "Not correct name", Toast.LENGTH_LONG).show()
                }
            }, 1600)
        }

    }
}