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
    private ListView listView;

    private String name;
    private RecommendedPlantsAdapter rpAdapter;




    private ArrayList<Plant> getPlants() {
        ArrayList<Plant> plants = new ArrayList<>();


        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            //ArrayList<Plant> plants = new ArrayList<>();
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                String image = doc.getString("Image");
                //String description = doc.getString("Description");
                String name = doc.getString("Name");
                String water = doc.getString("Water");
                String sun = doc.getString("Sun");


                plants.add(new Plant(image, name, water, sun));
                Log.d("plant is added", plants.size() + "");
            }

        });


       /* try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Log.d("total plants are added", plants.size() + "");


        return plants;
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_plants);


        db = FirebaseFirestore.getInstance();
        PlantImage = findViewById(R.id.plantImage);
        NameText = findViewById(R.id.name_text);
        WaterText = findViewById(R.id.water_text);
        SunText = findViewById(R.id.sun_text);




        Log.d("gotten to the sticky part", "sticky part");
        listView = (ListView)findViewById(R.id.images_list);
        ArrayList<Plant> plantList = getPlants();

        Log.d("past accessing list view", "past");
        rpAdapter = new RecommendedPlantsAdapter(this, plantList);
        // the line above is the problem!!!
        Log.d("plant list", plantList.size() +"");
        listView.setAdapter(rpAdapter);

        toPref = findViewById(R.id.toPrefButton);
        toPref.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedPlants.this, Preferences.class);
                startActivity(intent);

            }


        });

    }










}


/*toPref = findViewById(R.id.toPrefButton);
        toPref.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendedPlants.this, Preferences.class);
                startActivity(intent);

            }


        });*/