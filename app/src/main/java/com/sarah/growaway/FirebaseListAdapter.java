package com.sarah.growaway;


    com.firebase.ui.FirebaseListAdapter<String> myAdapter;


public class FirebaseListAdapter {
}



//Have to implement FirebaseListAdapter because regular Adapter is not ideal for Firebase

   /* Query query =
    FirebaseListAdapter<Plant> adapter = new FirebaseListAdapter(this, Plant.class, R.layout.plant_card, query);
*/

/*    Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");
    ListAdapter adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, android.R.layout.two_line_list_item, mRef)
    {



     Firebase ref = new Firebase("https://plantdatabase-266a7..firebaseio.com";
        ListAdapter adapter = new FirebaseListAdapter<Plant>(this, Plant.class, R.layout.activity_recommended_plants, mRef)
        {
            protected void populateView(View view, Plant plant)
            {
                ((TextView)view.findViewById(android.R.id.text1)).setText("Name");
                ((TextView)view.findViewById(R.id.name_text)).setText(Plant.getName());
            }
        };
        listView.setListAdapter(adapter);

 */