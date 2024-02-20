package com.example.spl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.spl.Activites.DashboardActivity
import com.example.spl.Activites.MainActivity
import com.google.firebase.auth.FirebaseAuth



class RegisterActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    lateinit var etEmailRegister : EditText
    lateinit var etEmailPassword : EditText
    lateinit var etConfirmPassword : EditText
    lateinit var btnRegisterMain : Button
    lateinit var btnSignInMain : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        etEmailRegister = findViewById(R.id.etEmailRegister)
        etEmailPassword = findViewById(R.id.etEmailPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegisterMain = findViewById(R.id.btnRegisterMain)
        btnSignInMain = findViewById(R.id.btnSignInMain)

        firebaseAuth = FirebaseAuth.getInstance()

        btnRegisterMain.setOnClickListener {
            signUpUser()
        }

        btnSignInMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser(){
        val email = etEmailRegister.text.toString()
        val password = etEmailPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword){
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
                }
            }
    }
}