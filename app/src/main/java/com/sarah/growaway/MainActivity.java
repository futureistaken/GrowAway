package com.sarah.growaway;

import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startButton;
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
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        PlantImage = findViewById(R.id.plantImage);
        NameText = findViewById(R.id.name_text);
        WaterText = findViewById(R.id.water_text);
        SunText = findViewById(R.id.sun_text);




        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecommendedPlants.class);
                startActivity(intent);

                db = FirebaseFirestore.getInstance();
                PlantImage = findViewById(R.id.plantImage);
                NameText = findViewById(R.id.name_text);
                WaterText = findViewById(R.id.water_text);
                SunText = findViewById(R.id.sun_text);


            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //populateRandomProfile();
    }




    private void populateRandomProfile() {
        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Plant> plants = new ArrayList<>();
            for (DocumentSnapshot doc: queryDocumentSnapshots) {
                String image = doc.getString("Image");
                String name = doc.getString("Name");
                String water = doc.getString("Water");
                String sun = doc.getString("Sun");


                plants.add(new Plant(image, name, water, sun));
            }

            Random random = new Random();
            Plant randomPlant = plants.get(random.nextInt(plants.size()));

            Picasso.get().load(randomPlant.getImage()).into(PlantImage);
            NameText.setText(randomPlant.getName());
            WaterText.setText(randomPlant.getWater());
            SunText.setText(randomPlant.getSun());
            //     Log.d("Name text is ", randomPlant.getName());
        });
    }



}

