package com.example.flashcard;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class listQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_qr);
        TextView listQuetion= findViewById(R.id.quetionlist);


        ActivityResultLauncher <Intent> activitylauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {

                    Log.d("data ", "onActivityResult:  from list quuestyij addactivity");
                });


        listQuetion.setOnClickListener(view -> {

            Intent intent = new Intent( listQR.this, activity_add_card.class);
            activitylauncher.launch(intent);


        });


    }
}