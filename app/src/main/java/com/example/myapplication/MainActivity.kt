package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.Switch


class MainActivity : AppCompatActivity() {

    var savedFilters = SavedFilters
    val radioGroup = findViewById<RadioGroup>(R.id.waterRadio)
    val seekBar = findViewById<SeekBar>(R.id.sunBar)
    val maintenanceSwitch = findViewById<Switch>(R.id.maintenanceSwitch)
    val saveButton = findViewById<Button>(R.id.saveButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton.setOnClickListener {
            saveFilters()
        }
    }

    fun saveFilters() {
        savedFilters.sunlightAmount = seekBar.progress
        savedFilters.waterAmount = radioGroup.checkedRadioButtonId
        savedFilters.maintenanceAmount = maintenanceSwitch.isChecked
    }
}
