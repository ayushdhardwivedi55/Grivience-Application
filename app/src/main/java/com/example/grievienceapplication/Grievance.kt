package com.example.grievienceapplication

import java.io.Serializable

data class Grievance(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    var status: String,  // Change 'val' to 'var' here
    val timestamp: Long,
    var response: String = "" // Change 'val' to 'var' here
) : Serializable