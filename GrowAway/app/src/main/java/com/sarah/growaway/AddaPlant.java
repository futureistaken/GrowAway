package com.sarah.growaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class AddaPlant extends AppCompatActivity {


    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_plant);


        //Button to move to Home - SF
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddaPlant.this, MainActivity.class);
                intent.putExtra("uid","main");
                startActivity(intent);



            }

        });


    }
}
