package com.sarah.growaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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


        if (it.getExtras().getString("uid").equals("filter")){

            //reads the data from the preferences intent
            String sun_it = it.getExtras().getString("sun_bar_value");
            String water_it = it.getExtras().getString("water_lvl_value");



            ArrayList<Plant> plantList = get_filtered_Plants(sun_it,water_it);
            rpAdapter = new RecommendedPlantsAdapter(this, plantList);
            listView.setAdapter(rpAdapter);



        } else if (it.getExtras().getString("uid").equals("main")) {
            ArrayList<Plant> plantList = getPlants();
            // Next two lines of code set a new adapter and populate it with the arraylist of plants - #sarah
            rpAdapter = new RecommendedPlantsAdapter(this, plantList);
            listView.setAdapter(rpAdapter);
        }
        else Toast.makeText(this,"not intent has been called!",Toast.LENGTH_SHORT).show();

    // This code sets up button to preferences page - #sarah
        toPref = findViewById(R.id.toPrefButton);
        toPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedPlants.this, Preferences.class);
                startActivity(intent);
                

            }


        });

    }

    // This function gets the plants from the database, puts it in the arraylist plants - #sarah
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
    public ArrayList<Plant> get_filtered_Plants(String sunlvl,String waterlvl) {
        ArrayList<Plant> plants = new ArrayList<>();

        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots)
            {
                if (doc.getString("Water") == waterlvl &&
                        doc.getString("Sun") == sunlvl) {

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
