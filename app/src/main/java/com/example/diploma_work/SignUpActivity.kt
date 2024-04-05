package com.example.diploma_work

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diploma_work.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private  lateinit var binding: ActivitySignUpBinding
    private lateinit var dataBaseHelper: AdminDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBaseHelper = AdminDataBaseHelper(this)

        binding.buttonSetIn.setOnClickListener{
            val signupLogin = binding.editTextName.text.toString()
            val signupPassword = binding.editTextPassword.text.toString()



            if(signupLogin.length <8 ){
                Toast.makeText(this, "Логін повинен містити принаймні 8 символів", Toast.LENGTH_SHORT).show()
            }
            else if (signupPassword.length < 8) {
                Toast.makeText(this, "Пароль повинен містити принаймні 8 символів", Toast.LENGTH_SHORT).show()
            }

            else {
                signupDataBase(signupLogin, signupPassword)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding.textViewCreateUser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
            finish()
        }
    }

    private fun signupDataBase(login: String, password: String){
        val insertedRowId = dataBaseHelper.insertAdmin(login, password)
        if (insertedRowId != -1L){
            Toast.makeText(this, "Реєстрація успішна", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Помилка при реєстрації", Toast.LENGTH_LONG).show()
        }
    }

}