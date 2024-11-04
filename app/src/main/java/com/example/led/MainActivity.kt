package com.example.led

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var tvLedStatus: TextView
    private lateinit var btnToggleLed: Button
    private var isLedOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase database reference
        database = Firebase.database.reference.child("ledStatus")

        // Initialize views
        tvLedStatus = findViewById(R.id.tv_led_status)
        btnToggleLed = findViewById(R.id.btn_toggle_led)

        // Read the current LED status from Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isLedOn = snapshot.getValue(Boolean::class.java) ?: false
                updateLedStatus()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors here
            }
        })

        // Toggle LED state when button is clicked
        btnToggleLed.setOnClickListener {
            isLedOn = !isLedOn
            database.setValue(isLedOn)
        }
    }

    private fun updateLedStatus() {
        if (isLedOn) {
            tvLedStatus.text = "LED Status: ON"
            btnToggleLed.text = "Turn Off"
        } else {
            tvLedStatus.text = "LED Status: OFF"
            btnToggleLed.text = "Turn On"
        }
    }

}