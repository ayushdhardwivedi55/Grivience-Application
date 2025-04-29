package com.example.grievienceapplication

import android.content.Context
import com.google.gson.Gson

class FileHelper(private val context: Context) {
    private val fileName = "grievances.json"

    fun saveGrievances(grievances: List<Grievance>) {
        val json = Gson().toJson(grievances)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    fun readGrievances(): List<Grievance> {
        return try {
            val file = context.getFileStreamPath(fileName)
            if (file.exists()) {
                val json = file.readText()
                Gson().fromJson(json, Array<Grievance>::class.java).toList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}