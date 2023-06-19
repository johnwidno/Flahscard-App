package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class acceuil_activity_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_page);

        Button btnStart =findViewById(R.id.btnStart);


        btnStart.setOnClickListener(v -> {

           Intent intent = new Intent(acceuil_activity_page.this, MainActivity.class)  ;
           startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        });

    }
}