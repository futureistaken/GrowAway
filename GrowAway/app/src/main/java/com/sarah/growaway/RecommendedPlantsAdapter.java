package com.sarah.growaway;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        water.setText(currentPlant.getWater());


        TextView sun = (TextView)listItem.findViewById(R.id.sun_text);
        sun.setText(currentPlant.getSun());


        return listItem;



    }




}