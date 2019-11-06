package com.sarah.growaway;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RecommendedPlants extends AppCompatActivity {
    Button toPref;
    private FirebaseFirestore db;
    private TextView NameText;
    private TextView WaterText;
    private TextView SunText;
    private ImageView PlantImage;

    private String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_plants);

        db = FirebaseFirestore.getInstance();
        PlantImage = findViewById(R.id.plantImage);
        NameText = findViewById(R.id.name_text);
        WaterText = findViewById(R.id.water_text);
        SunText = findViewById(R.id.sun_text);


        toPref = findViewById(R.id.toPrefButton);
        toPref.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedPlants.this, Preferences.class);
                startActivity(intent);

            }


        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        populateRandomProfile();
    }




    private void populateRandomProfile() {
        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Plant> plants = new ArrayList<>();
            for (DocumentSnapshot doc: queryDocumentSnapshots) {
                String name = doc.getString("Name");
                String water = doc.getString("Water");
                String sun = doc.getString("Sun");


              plants.add(new Plant(name, water, sun));
            }

            Random random = new Random();
            Plant randomPlant = plants.get(random.nextInt(plants.size()));

        //    Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
            NameText.setText(randomPlant.getName());
            WaterText.setText(randomPlant.getWater());
            SunText.setText(randomPlant.getSun());
       //     Log.d("Name text is ", randomPlant.getName());
        });
    }


}