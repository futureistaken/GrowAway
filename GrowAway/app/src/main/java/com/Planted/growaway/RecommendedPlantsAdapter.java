package com.Planted.growaway;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class RecommendedPlantsAdapter extends ArrayAdapter<Plant> {

    private Context context;
    private List<Plant> plantList = new ArrayList<>();


    public RecommendedPlantsAdapter(Context context, ArrayList<Plant> list){
        super(context, 0, list);
        this.context = context;
        this.plantList = list;


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


        return listItem;



    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}