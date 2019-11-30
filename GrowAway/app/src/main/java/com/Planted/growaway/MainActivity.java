package com.Planted.growaway;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


// Notes: Have not implemented Description text yet, so it is commented out.

public class MainActivity extends AppCompatActivity {
    Button startButton;
    Button addPlantButton;
    private FirebaseFirestore db;
    static TextView NameText,test;
    private TextView WaterText;
    private TextView SunText;
    private ImageView PlantImage;
    static ImageView icon_img;
    static Context cx;
   // private TextView DescriptionText;


    private SensorManager sM;
    private float acelVal;
    private float acelLast;
    private float shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cx = this;

        test = findViewById(R.id.test_txt);
        icon_img = findViewById(R.id.icon_img_view);
        // start the an AsynkTask to read the weather data
        Weather_data process = new Weather_data();
        process.execute();

        //This code instantiates a sensor for shake to change random plant functionality
        // Adapted from Rishi's code - thanks Rishi!
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



        //Button to move to recommended plants page - SF
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecommendedPlants.class);
                intent.putExtra("uid","main");
                startActivity(intent);


            }

        });


        //Button to move to Add a Plant Page - SF
        addPlantButton = findViewById(R.id.addPlantButton);
        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddaPlant.class);
                intent.putExtra("uid","main");
                startActivity(intent);


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
            if (shake > 7) {
                System.out.println("Shake!");
                populateRandomProfile();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



    //This function actually calls the populate Random Profile function.
    @Override
    protected void onResume() {
        super.onResume();
        populateRandomProfile();
    }


    // This function connects to Firebase table, and populates an array list plants

    private void populateRandomProfile() {
        db.collection("plants").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Plant> plants = new ArrayList<>();
            for (DocumentSnapshot doc: queryDocumentSnapshots) {
                // The line below sometimes throws the error - "Field 'Image' is not a
                // Java.lang.string - just reload the app onto the emulator/device
                // Was told to do this by Vihang Nov 15 - SF
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
            NameText.setText(randomPlant.getName());
            WaterText.setText("Water: " + randomPlant.getWater());
            SunText.setText("Sun: " + randomPlant.getSun());
        });
    }



}

