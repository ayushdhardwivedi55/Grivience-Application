package com.example.grievienceapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grievienceapplication.databinding.ActivityEditGrievanceBinding

class EditGrievanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditGrievanceBinding
    private lateinit var grievance: Grievance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGrievanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        grievance = intent.getSerializableExtra("grievance") as? Grievance ?: run {
            Toast.makeText(this, "Error: Grievance data not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupSpinner()
        populateFields()
        setupButtons()
    }

    private fun setupSpinner() {
        val statuses = arrayOf("New", "In Progress", "Resolved", "Rejected")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.statusSpinner.adapter = adapter
    }

    private fun populateFields() {
        binding.editTextTitle.setText(grievance.title)
        binding.editTextCategory.setText(grievance.category)
        binding.editTextDescription.setText(grievance.description)

        val statuses = arrayOf("New", "In Progress", "Resolved", "Rejected")
        val position = statuses.indexOf(grievance.status)
        if (position != -1) {
            binding.statusSpinner.setSelection(position)
        }
    }

    private fun setupButtons() {
        binding.buttonUpdate.setOnClickListener {
            updateGrievance()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun updateGrievance() {
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val category = binding.editTextCategory.text.toString()
        val status = binding.statusSpinner.selectedItem.toString()

        if (title.isEmpty() || description.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedGrievance = grievance.copy(
            title = title,
            description = description,
            category = category,
            status = status
        )

        val fileHelper = FileHelper(this)
        val grievances = fileHelper.readGrievances().toMutableList()

        val index = grievances.indexOfFirst { it.id == grievance.id }
        if (index != -1) {
            grievances[index] = updatedGrievance
            fileHelper.saveGrievances(grievances)
            Toast.makeText(this, "Grievance updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error: Grievance not found", Toast.LENGTH_SHORT).show()
        }
    }
}