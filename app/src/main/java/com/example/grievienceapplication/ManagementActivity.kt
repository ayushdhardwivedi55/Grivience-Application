package com.example.grievienceapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManagementActivity : AppCompatActivity() {

    private lateinit var grievanceRecyclerView: RecyclerView
    private lateinit var grievanceList: ArrayList<Grievance>
    private lateinit var adapter: ManagementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)

        grievanceRecyclerView = findViewById(R.id.managementRecyclerView)
        grievanceRecyclerView.layoutManager = LinearLayoutManager(this)

        val fileHelper = FileHelper(this)
        val list = fileHelper.readGrievances()
        grievanceList = if (list != null) ArrayList(list) else arrayListOf()

        adapter = ManagementAdapter(grievanceList) { grievance ->
            val intent = Intent(this, DetailGrievanceActivity::class.java)
            intent.putExtra("grievance", grievance)
            startActivity(intent)
        }

        grievanceRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val fileHelper = FileHelper(this)
        grievanceList.clear()
        grievanceList.addAll(fileHelper.readGrievances())
        adapter.notifyDataSetChanged()
    }
}