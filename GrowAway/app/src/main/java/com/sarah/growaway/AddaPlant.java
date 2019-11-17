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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/*
Good reference link
https://github.com/firebase/snippets-android/blob/f21c7a11e775e9423bf00b61ad5588a4bc72cb6a/storage/app/src/main/java/com/google/firebase/referencecode/storage/StorageActivity.java#L64-L64
*/

public class AddaPlant extends AppCompatActivity {




    Button backButton;
    Button uploadButton;
    Button choosePhotoButton;
    Button takePhotoButton;
    EditText NameInput;

    private FirebaseFirestore db;
    FirebaseStorage storage;
    //private String selectedUid = "4365402394";
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_plant);

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();


        // Need to handle opening camera and photos app
        // Also have to take in input from Water and Sun fields



        NameInput = (EditText)findViewById(R.id.NameInput);



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
        plant.put("Water", "James is");
        plant.put("Sun", "The best");

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



}
