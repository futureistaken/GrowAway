package com.Planted.growaway;

import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class RecommendedPlantsAdapter extends ArrayAdapter<Plant> {

    private Context context;
    private List<Plant> plantList = new ArrayList<>();
    public static final String SHARED_PREFS = "sharedPrefs";

    private List<Plant> favePlants;
    private boolean favorited;


    public RecommendedPlantsAdapter(Context context, ArrayList<Plant> list){
        super(context, 0, list);
        this.context = context;
        this.plantList = list;
    }


    public RecommendedPlantsAdapter(Context context, ArrayList<Plant> list, boolean favorited){
        super(context, 0, list);
        this.context = context;
        this.plantList = list;
        this.favorited = favorited;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.plant_card, parent, false);
        }

        Plant currentPlant = plantList.get(position);

        ImageView PlantImage = (ImageView)listItem.findViewById(R.id.plantImage);
        Picasso.get().load(currentPlant.getImage()).into(PlantImage);

        TextView name = (TextView)listItem.findViewById(R.id.name_text);
        name.setText(currentPlant.getName());


        TextView water = (TextView)listItem.findViewById(R.id.water_text);
        water.setText("Water: " + currentPlant.getWater());


        TextView sun = (TextView)listItem.findViewById(R.id.sun_text);
        sun.setText("Sun: " + currentPlant.getSun());

        ImageButton favoriteStar = (ImageButton)listItem.findViewById(R.id.favoriteStar);



        if (favorited == true) {
            favoriteStar.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
        }

        favoriteStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Favorited!", Toast.LENGTH_SHORT).show();
                loadData();
                favePlants.add(currentPlant);
                saveData();
                favoriteStar.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));

            }
        });

        return listItem;

    }


    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("fave list", null);
        Type type = new TypeToken<ArrayList<Plant>>() {}.getType();
        favePlants = gson.fromJson(json, type);

        if (favePlants == null) {
            favePlants = new ArrayList<>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favePlants);
        editor.putString("fave list", json);
        editor.apply();
    }



}