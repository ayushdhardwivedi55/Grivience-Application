package com.example.grievienceapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grievienceapplication.databinding.ItemGrievanceBinding

class GrievanceAdapter(
    private var grievances: List<Grievance>,
    private val context: Context,
    private val onItemClick: (Grievance) -> Unit
) : RecyclerView.Adapter<GrievanceAdapter.GrievanceViewHolder>() {

    inner class GrievanceViewHolder(val binding: ItemGrievanceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrievanceViewHolder {
        val binding = ItemGrievanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GrievanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrievanceViewHolder, position: Int) {
        val grievance = grievances[position]
        with(holder.binding) {
            textViewTitle.text = grievance.title
            textViewCategory.text = grievance.category
            textViewStatus.text = grievance.status
            root.setOnClickListener {
                onItemClick(grievance)
            }

            // Optional: Set status color
            val statusColor = when (grievance.status) {
                "New" -> context.getColor(R.color.status_new)
                "In Progress" -> context.getColor(R.color.status_in_progress)
                "Resolved" -> context.getColor(R.color.status_resolved)
                "Rejected" -> context.getColor(R.color.status_rejected)
                else -> context.getColor(R.color.status_default)
            }
            textViewStatus.setTextColor(statusColor)
        }
    }

    override fun getItemCount(): Int = grievances.size

    fun updateData(newGrievances: List<Grievance>) {
        grievances = newGrievances
        notifyDataSetChanged()
    }

    fun sortByStatus() {
        grievances = grievances.sortedBy { it.status }
        notifyDataSetChanged()
    }
}