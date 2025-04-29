package com.example.grievienceapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.grievienceapplication.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        // Load and apply saved theme
        loadThemePreference()

        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)

        // Set up card click listeners
        binding.postGrievanceCard.setOnClickListener {
            val intent = Intent(this, AddGrievanceActivity::class.java)
            startActivity(intent)
        }

        binding.trackStatusCard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.howToComplainCard.setOnClickListener {
            showHowToComplainDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                toggleTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleTheme() {
        isDarkMode = !isDarkMode
        applyTheme()
        saveThemePreference()
    }

    private fun applyTheme() {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun saveThemePreference() {
        val sharedPref = getPreferences(MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("dark_mode", isDarkMode)
            apply()
        }
    }

    private fun loadThemePreference() {
        val sharedPref = getPreferences(MODE_PRIVATE)
        isDarkMode = sharedPref.getBoolean("dark_mode", false)

        // Apply the saved theme
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun showHowToComplainDialog() {
        AlertDialog.Builder(this)
            .setTitle("How to Complain")
            .setMessage(
                "1. Click on 'POST GRIEVANCE'\n" +
                        "2. Fill in all required details\n" +
                        "3. Submit your grievance\n" +
                        "4. Track status using 'TRACK STATUS'"
            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}