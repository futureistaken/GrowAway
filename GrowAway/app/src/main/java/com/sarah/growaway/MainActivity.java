package com.sarah.growaway;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
   // private TextView DescriptionText;

    private String name;

    private SensorManager sM;
    private float acelVal;
    private float acelLast;
    private float shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sM.registerListener(sensorListener, sM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;


        db = FirebaseFirestore.getInstance();
        PlantImage = findViewById(R.id.plantImage);
        NameText = findViewById(R.id.name_text);
        WaterText = findViewById(R.id.water_text);
        SunText = findViewById(R.id.sun_text);
      //  DescriptionText = findViewById(R.id.description_text);




        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecommendedPlants.class);
                startActivity(intent);

            /*    db = FirebaseFirestore.getInstance();
                PlantImage = findViewById(R.id.plantImage);
                NameText = findViewById(R.id.name_text);
                WaterText = findViewById(R.id.water_text);
                SunText = findViewById(R.id.sun_text);*/
               // DescriptionText = findViewById(R.id.description_text);


            }

        });
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;



            //have to shake phone up and down hard
            if (shake > 12) {
                System.out.println("Shake!");
                populateRandomProfile();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };




    @Override
    protected void onResume() {
        super.onResume();
        populateRandomProfile();
    }




    private void populateRandomProfile() {
        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Plant> plants = new ArrayList<>();
            for (DocumentSnapshot doc: queryDocumentSnapshots) {
                String image = doc.getString("Image");
               // String description = doc.getString("Description");
                String name = doc.getString("Name");
                String water = doc.getString("Water");
                String sun = doc.getString("Sun");


                plants.add(new Plant(image, name, water, sun));
            }

            Random random = new Random();
            Plant randomPlant = plants.get(random.nextInt(plants.size()));

            Picasso.get().load(randomPlant.getImage()).into(PlantImage);
            NameText.setText("Name: " + randomPlant.getName());
            //DescriptionText.setText(randomPlant.getDescription());

            WaterText.setText("Water: " + randomPlant.getWater());
            SunText.setText("Sun: " + randomPlant.getSun());
        });
    }



}

