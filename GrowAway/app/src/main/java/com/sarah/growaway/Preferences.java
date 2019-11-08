package com.sarah.growaway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.RadioGroup;

public class Preferences extends AppCompatActivity {

    SavedFilters savedFilters = new SavedFilters();
    RadioGroup radioGroup = findViewById(R.id.waterRadio);
    SeekBar seekBar = findViewById(R.id.sunBar);
    //Switch maintenanceSwitch = findViewById(R.id.maintenanceSwitch);
    Button saveButton = findViewById(R.id.saveButton);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilters();
            }
        });


    }

    public void saveFilters() {
        savedFilters.sunlightAmount = seekBar.getProgress();
        savedFilters.waterAmount = radioGroup.getCheckedRadioButtonId();
        //savedFilters.maintenanceAmount = maintenanceSwitch.isChecked();
    }

}



