package com.example.grievienceapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.grievienceapplication.databinding.ActivityDetailGrievanceBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailGrievanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGrievanceBinding
    private lateinit var grievance: Grievance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGrievanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get grievance data from intent
        grievance = intent.getSerializableExtra("grievance") as? Grievance ?: run {
            finish()
            return
        }

        setupToolbar()
        displayGrievanceDetails()

        // Button: Provide Resolution
        binding.buttonResolve.setOnClickListener {
            showResolutionDialog()
        }

        // Button: Respond (same functionality as resolve for now)
        binding.buttonRespond.setOnClickListener {
            showResolutionDialog()
        }

        // Button: Back
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Grievance Details"
    }

    private fun displayGrievanceDetails() {
        binding.textViewTitle.text = grievance.title
        binding.textViewCategory.text = grievance.category
        binding.textViewDescription.text = grievance.description
        binding.textViewStatus.text = grievance.status

        // Format timestamp
        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date = Date(grievance.timestamp.toLong())
        binding.textViewTimestamp.text = dateFormat.format(date)

        // Set status color
        val statusColor = when (grievance.status) {
            "New" -> getColor(R.color.status_new)
            "In Progress" -> getColor(R.color.status_in_progress)
            "Resolved" -> getColor(R.color.status_resolved)
            "Rejected" -> getColor(R.color.status_rejected)
            else -> getColor(R.color.status_default)
        }
        binding.textViewStatus.setTextColor(statusColor)
    }

    private fun showResolutionDialog() {
        // Create an AlertDialog for the admin to provide a resolution
        val dialogView = layoutInflater.inflate(R.layout.dialog_resolution, null)
        val resolutionEditText = dialogView.findViewById<EditText>(R.id.editTextResolution)

        AlertDialog.Builder(this)
            .setTitle("Provide Resolution")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val resolution = resolutionEditText.text.toString()
                if (resolution.isNotBlank()) {
                    // Update the grievance status and save the resolution
                    grievance.status = "Resolved"
                    grievance.response = resolution

                    saveUpdatedGrievance()
                    displayGrievanceDetails()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun saveUpdatedGrievance() {
        val fileHelper = FileHelper(this)
        val grievances = fileHelper.readGrievances().toMutableList()
        val index = grievances.indexOfFirst { it.id == grievance.id }
        if (index != -1) {
            grievances[index] = grievance
            fileHelper.saveGrievances(grievances)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when coming back from edit
        val fileHelper = FileHelper(this)
        val grievances = fileHelper.readGrievances()
        val updatedGrievance = grievances.find { it.id == grievance.id }

        if (updatedGrievance != null && updatedGrievance != grievance) {
            grievance = updatedGrievance
            displayGrievanceDetails()
        }
    }
}