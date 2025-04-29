package com.example.grievienceapplication

import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grievienceapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GrievanceAdapter
    private var grievanceList = mutableListOf<Grievance>()
    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupRecyclerView()
        setupFab()

        // Check current theme
        isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    override fun onResume() {
        super.onResume()
        loadGrievances()
    }

    private fun setupRecyclerView() {
        adapter = GrievanceAdapter(
            grievances = grievanceList,
            context = this,
            onItemClick = { grievance ->
                val intent = Intent(this, DetailGrievanceActivity::class.java)
                intent.putExtra("grievance", grievance)
                startActivity(intent)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
    private fun setupFab() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddGrievanceActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadGrievances() {
        val fileHelper = FileHelper(this)
        grievanceList = fileHelper.readGrievances().toMutableList()
        adapter.updateData(grievanceList)
    }

    private fun deleteGrievance(grievance: Grievance) {
        val fileHelper = FileHelper(this)
        grievanceList.remove(grievance)
        fileHelper.saveGrievances(grievanceList)
        adapter.updateData(grievanceList)
        Toast.makeText(this, "Grievance deleted successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        SearchHelper.setupSearchView(searchView, adapter, grievanceList)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                adapter.sortByStatus()
                true
            }
            R.id.action_theme -> {
                toggleTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleTheme() {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        isDarkMode = !isDarkMode
        recreate()
    }
}