package com.Planted.growaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class Preferences extends AppCompatActivity {

    RadioGroup radioGroup;
    SeekBar seekBar;
    Button saveButton;
    Switch favoritesSwitch;
    Button clearFavoritesButton;

    private String sunValue;

    private int radioGroup_selectedID;
    private String water_string;

    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        // gets the sun and reads the value from it
        seekBar = findViewById(R.id.sunBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sunValue = sunBar_StringValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        favoritesSwitch = (Switch)findViewById(R.id.favoritesSwitch);
        // This sets the clear favorites button which removes the list from local data
        clearFavoritesButton = findViewById(R.id.clearFavoritesButton);
        clearFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                Toast.makeText(getApplicationContext(), "Favorites Cleared!", Toast.LENGTH_SHORT).show();
            }
        });


        saveButton = findViewById(R.id.saveButton);
        // This save button will apply the filters
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets the water and reads the value from it
                radioGroup = findViewById(R.id.waterRadio);
                radioGroup_selectedID =  radioGroup.getCheckedRadioButtonId();
                RadioButton rb = findViewById(radioGroup_selectedID);
                if (rb != null)
                    water_string = rb.getText().toString();

                if (sunValue == null) sunValue = "Medium";
                if (water_string == null) water_string = "Every Day";

                if (favoritesSwitch.isChecked()) {
                    Intent it = new Intent(Preferences.this, RecommendedPlants.class);
                    it.putExtra("uid", "favorites");
                    setResult(1, it);
                    finish();
                }
                else {
                    Intent it  = new Intent(Preferences.this,RecommendedPlants.class);
                    it.putExtra("sun_bar_value",sunValue);
                    it.putExtra("water_lvl_value",water_string);
                    it.putExtra("uid","filter");
                    setResult(1,it);
                    finish();
                }

            }
        });


        }

        public String sunBar_StringValue(int i){
        switch (i) {
            case 0:
                sunValue = "Low";
                break;
            case 1:
                sunValue = "Medium";
                break;
            case 2:
                sunValue = "High";
                break;

            default:
                sunValue = "no value selected";
        }

        return sunValue;

        }

}







