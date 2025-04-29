package com.example.grievienceapplication


import androidx.appcompat.widget.SearchView

object SearchHelper {
    fun setupSearchView(
        searchView: SearchView,
        adapter: GrievanceAdapter,
        originalList: List<Grievance>
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = originalList.filter { grievance ->
                    grievance.title.contains(newText.orEmpty(), ignoreCase = true) ||
                            grievance.category.contains(newText.orEmpty(), ignoreCase = true)
                }
                adapter.updateData(filteredList)
                return true
            }
        })
    }
}