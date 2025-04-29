package com.example.grievienceapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ManagementAdapter(
    private val grievances: List<Grievance>,
    private val onRespondClick: (Grievance) -> Unit
) : RecyclerView.Adapter<ManagementAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val complaintText: TextView = itemView.findViewById(R.id.complaintText)
        val respondButton: Button = itemView.findViewById(R.id.respondButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grievance_a, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = grievances.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grievance = grievances[position]
        holder.nameText.text = grievance.title
        holder.complaintText.text = grievance.category
        holder.respondButton.setOnClickListener {
            onRespondClick(grievance)
        }
    }
}