package com.example.grievienceapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {

    private val adminEmail = "admin@startup.com"
    private val adminPassword = "admin123"

    private val employeeEmail = "employee@startup.com"
    private val employeePassword = "emp123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            when {
                email == adminEmail && password == adminPassword -> {
                    Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ManagementActivity::class.java))
                }

                email == employeeEmail && password == employeePassword -> {
                    Toast.makeText(this, "Employee Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }

                else -> {
                    Toast.makeText(this, "Invalid credentials. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}