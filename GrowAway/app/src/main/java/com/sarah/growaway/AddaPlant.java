package com.sarah.growaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import io.grpc.Context;

/*
Good reference link
https://github.com/firebase/snippets-android/blob/f21c7a11e775e9423bf00b61ad5588a4bc72cb6a/storage/app/src/main/java/com/google/firebase/referencecode/storage/StorageActivity.java#L64-L64
*/

public class AddaPlant extends AppCompatActivity {


    Button uploadButton;
    Button choosePhotoButton;
    Button takePhotoButton;

    private FirebaseFirestore db;
    FirebaseStorage storage;
    String uid;

    EditText NameInput;

    private String sunValue;
    SeekBar seekBar;

    private String water_string;


    ImageView mPhotoImageView;
    Uri photoUri;
    String imageUri;


    String TestURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_plant);

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        mPhotoImageView = findViewById(R.id.mPhotoImageView);


        //handles opening camera
        takePhotoButton = findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open camera app, save photo to be used for UploadPhoto
                takePhotoClick(view);

            }
        });
        //handles choosing a photo from user library
        choosePhotoButton = findViewById(R.id.choosePhotoButton);
        choosePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open up user library, choose photo
                onPickPhoto(view);
            }
        });


        //Take in User input for name
        NameInput = (EditText) findViewById(R.id.NameInput);


        //Take in User input for Water field
        //Using function OnRadioButtonClicked, written below onCreate function
        //Set default value
        if (water_string == null) water_string = "Every Day";

        //take in User input for Sun fields
        seekBar = findViewById(R.id.sunBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sunValue = sunBar_StringValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Set default value
        if (sunValue == null) sunValue = "Medium";


        // Next lines get the current date and time to serve
        // as the uid for the added plant in the Firestore db
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        uid = dtf.format(now);


        //Button to upload, and then return to home activity - SF
        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPhoto();

            }
        });

    }

    private void UploadText() {
        String nameValue = NameInput.getText().toString();

        //add a new document to the collection with name, water, sun
        Map<String, Object> plant = new HashMap<>();
        plant.put("Name", nameValue);
        plant.put("Image", TestURL);
        plant.put("Water", water_string);
        plant.put("Sun", sunValue);

        db.collection("plants").document(uid)
                .set(plant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("done", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("done", "Error writing document", e);
                    }
                });
    }



    public void UploadPhoto() {
        String nameValue = NameInput.getText().toString();
        // Create reference to full path of the file
            StorageReference storageRef = storage.getReference();
            StorageReference plantRef = storageRef.child(nameValue + ".jpg"); //guid random id

            StorageReference plantImageRef = storageRef.child("images/"+nameValue+".jpg");

            plantRef.getName().equals(plantImageRef.getName());
            plantRef.getPath().equals(plantImageRef.getPath());



            // Get the data from an ImageView as bytes
            mPhotoImageView.setDrawingCacheEnabled(true);
            mPhotoImageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) mPhotoImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data2 = baos.toByteArray();

            UploadTask uploadTask = plantRef.putBytes(data2);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddaPlant.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            TestURL = uri.toString();

                        }
                    });



                    //TestURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                    //TestURL = taskSnapshot.getStorage().getDownloadUrl().toString();




                   // TestURL = taskSnapshot.getStorage().getDownloadUrl().getResult().toString();

                    //Need to be aware of async tasks, have to finish UploadPhoto()
                    //before starting UploadText()
                    UploadText();
                    finish();
                }
            });


            //Possible Uri -> URL code?

        //TestURL = storageRef.child("images/"+nameValue+".jpg").getDownloadUrl() + "";

/*        storageRef.child("images/"+nameValue+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png' in uri
                TestURL = uri + "";

                //System.out.println(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*/


    }


    public String sunBar_StringValue(int i) {
        switch (i) {
            case 1:
                sunValue = "Low";
                break;
            case 2:
                sunValue = "Medium";
                break;
            case 3:
                sunValue = "High";
                break;

            default:
                sunValue = "no value selected";
        }

        return sunValue;

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.r0:
                if (checked)
                    water_string = "Every Day";
                break;
            case R.id.r1:
                if (checked)
                    water_string = "Every Other Day";
                break;
            case R.id.r2:
                if (checked)
                    water_string = "Once a Week";
                break;
            case R.id.r3:
                if (checked)
                    water_string = "Once a Month";
                break;
        }
    }
    private final int REQUEST_IMAGE_CAPTURE = 1;

    // handles taking a photo, returning the photo as a bitmap to an image view
    public void takePhotoClick(View view) {

        // Start the activity that can handle the implicit intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //This is onActivityResult for camera photo

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            // Show preview
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPhotoImageView.setImageBitmap(imageBitmap);


        return;
        }
            //This is onActivityResult for photoLibrary


             if (data != null) {
                Uri photoUri = data.getData();
                //imageUri = photoUri + "";
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
                mPhotoImageView.setImageBitmap(selectedImage);
            }


    }

    public final static int PICK_PHOTO_CODE = 1046;

    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }



}