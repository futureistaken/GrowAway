package com.sarah.growaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/*
Good reference link
https://github.com/firebase/snippets-android/blob/f21c7a11e775e9423bf00b61ad5588a4bc72cb6a/storage/app/src/main/java/com/google/firebase/referencecode/storage/StorageActivity.java#L64-L64
*/

public class AddaPlant extends AppCompatActivity {




    Button uploadButton;
    Button choosePhotoButton;
    Button takePhotoButton;

    private FirebaseFirestore db;
    FirebaseStorage storage;
    String uid;

    EditText NameInput;

    private String sunValue;
    SeekBar seekBar;

    RadioGroup radioGroup;
    private int radioGroup_selectedID;
    private String water_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_plant);

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();


        // Need to handle opening camera and photos app

        //Take in User input for name
        NameInput = (EditText)findViewById(R.id.NameInput);


        //Take in User input for Water field
        radioGroup = findViewById(R.id.waterInputRadio);
        radioGroup_selectedID =  radioGroup.getCheckedRadioButtonId();
        Log.d("selected ID is", radioGroup_selectedID+"");
        RadioButton rb = findViewById(radioGroup_selectedID);
        Log.d("rb is", rb+"");
        if (rb != null)
            water_string = rb.getText().toString();

        //Set default value
        if (water_string == null) water_string = "Every Day";

        //take in User input for Sun fields
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
        //Set default value
        if (sunValue == null) sunValue = "Medium";




        // Next lines get the current date and time to serve
        // as the uid for the added plant in the Firestore db
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        uid = dtf.format(now);



        //Button to upload, and then return to home activity - SF
        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPhoto();
                UploadText();
                finish();

            }
        });

    }

    private void UploadText() {
        String nameValue = NameInput.getText().toString();

        //add a new document to the collection with name, water, sun
        Map<String, Object> plant = new HashMap<>();
        plant.put("Name", nameValue);
        plant.put("Water", water_string);
        plant.put("Sun", sunValue);

        db.collection("plants").document(uid)
                .set(plant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("done", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("done", "Error writing document", e);
                    }
                });
    }








    private void UploadPhoto() {
        //upload photo to storage, get url, pass that into "Image" field in database


    }

    public String sunBar_StringValue(int i){
        switch (i) {
            case 1:
                sunValue = "Low";
                break;
            case 2:
                sunValue = "Medium";
                break;
            case 3:
                sunValue = "High";
                break;

            default:
                sunValue = "no value selected";
        }

        return sunValue;

    }


}
