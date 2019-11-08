package com.sarah.growaway;

import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;
        import android.content.Context;
//import android.support.annotation.LayoutRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
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
    // databaseHelper dbHelper = new databaseHelper(getContext());


    public RecommendedPlantsAdapter(@NonNull Context context, @LayoutRes ArrayList<Plant> list){
        super(context, 0, list);
        this.context = context;
        this.plantList = list;


    }

    //Have to implement FirebaseListAdapter because regular Adapter is not ideal for Firebase

   /* Query query =
    FirebaseListAdapter<Plant> adapter = new FirebaseListAdapter(this, Plant.class, R.layout.plant_card, query);
*/

/*    Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");
    ListAdapter adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, android.R.layout.two_line_list_item, mRef)
    {
        protected void populateView(View view, ChatMessage chatMessage)
        {
            ((TextView)view.findViewById(android.R.id.text1)).setText(chatMessage.getName());
            ((TextView)view.findViewById(android.R.id.text2)).setText(chatMessage.getMessage());
        }
    };
     listView.setListAdapter(adapter);*/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.plant_card, parent, false);
        }

       // final Plant currentPlant = plantList.get(position);
        Plant currentPlant = plantList.get(position);

        ImageView PlantImage = (ImageView)listItem.findViewById(R.id.plantImage);
        Picasso.get().load(currentPlant.getImage()).into(PlantImage);

        TextView name = (TextView)listItem.findViewById(R.id.name_text);
        name.setText(currentPlant.getName());


        TextView water = (TextView)listItem.findViewById(R.id.water_text);
        water.setText(currentPlant.getWater());


        TextView sun = (TextView)listItem.findViewById(R.id.sun_text);
        sun.setText(currentPlant.getSun());

        Log.d("gotten to list item", "done");

        return listItem;



    }




}