package com.example.grievienceapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.grievienceapplication.R

class ActivityMainBinding private constructor(
    val root: View,
    val toolbar: Toolbar,
    val recyclerView: RecyclerView,
    val fab: FloatingActionButton
) {
    companion object {
        fun inflate(layoutInflater: LayoutInflater): ActivityMainBinding {
            val root = layoutInflater.inflate(R.layout.activity_main, null, false)
            val toolbar: Toolbar = root.findViewById(R.id.toolbar)
            val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
            val fab: FloatingActionButton = root.findViewById(R.id.fab)

            return ActivityMainBinding(root, toolbar, recyclerView, fab)
        }
    }

    fun setContentView(activity: android.app.Activity) {
        activity.setContentView(root)
    }
}
