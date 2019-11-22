package com.sarah.growaway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecommendedPlants extends AppCompatActivity {
    Button toPref;
    private FirebaseFirestore db;
    private ListView listView;

    private String name;
    private RecommendedPlantsAdapter rpAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_plants);


        db = FirebaseFirestore.getInstance();


        listView = findViewById(R.id.images_list);


        //intent checker - #babak
        Intent it = this.getIntent();


        if (it.getExtras().getString("uid").equals("main")) {
            ArrayList<Plant> plantList = getPlants();
            // Next two lines of code set a new adapter and populate it with the arraylist of plants - #sarah
            rpAdapter = new RecommendedPlantsAdapter(this, plantList);
            listView.setAdapter(rpAdapter);
        }
        // This code sets up button to preferences page - #sarah
        toPref = findViewById(R.id.toPrefButton);
        toPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedPlants.this, Preferences.class);
                startActivityForResult(intent, 1);

            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //reads the data from the preferences intent
            String sun_it = data.getExtras().getString("sun_bar_value");
            String water_it = data.getExtras().getString("water_lvl_value");

            ArrayList<Plant> plantList = get_filtered_Plants(sun_it, water_it);
            rpAdapter = new RecommendedPlantsAdapter(this, plantList);
            listView.setAdapter(rpAdapter);
        } else {
            ArrayList<Plant> plantList = getPlants();
            // Next two lines of code set a new adapter and populate it with the arraylist of plants - #sarah
            rpAdapter = new RecommendedPlantsAdapter(this, plantList);
            listView.setAdapter(rpAdapter);
        }

    }

    // This function gets the plants from the database, puts it in the arraylist plants - #sarah
    // This code is adapted from Rishi's code - Thanks Rishi!
    public ArrayList<Plant> getPlants() {
        ArrayList<Plant> plants = new ArrayList<>();

        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                String image = doc.getString("Image");
                //String description = doc.getString("Description");
                String name = doc.getString("Name");
                String water = doc.getString("Water");
                String sun = doc.getString("Sun");

                plants.add(new Plant(image, name, water, sun));
            }
            rpAdapter.notifyDataSetChanged();
        });

        return plants;
    }

    // apply the filter and retrieve plant from database - #babak
    public ArrayList<Plant> get_filtered_Plants(String sunlvl, String waterlvl) {
        ArrayList<Plant> plants = new ArrayList<>();

        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                if (doc.getString("Water").equals(waterlvl) &&
                        doc.getString("Sun").equals(sunlvl)) {

                    String image = doc.getString("Image");
                    //String description = doc.getString("Description");
                    String name = doc.getString("Name");
                    String water = doc.getString("Water");
                    String sun = doc.getString("Sun");

                    plants.add(new Plant(image, name, water, sun));
                }
            }
            rpAdapter.notifyDataSetChanged();
        });

        return plants;
    }


}
