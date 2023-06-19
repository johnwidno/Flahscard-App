package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ListView;
import java.util.List;

public class Activitylistesclasse extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylistesclasse);



        QuestionReponseDatabase dbbase= Room.databaseBuilder(getApplicationContext(),QuestionReponseDatabase.class,"mydatabase").allowMainThreadQueries().build();
        List<QuestionReponse> listqueetreponse = dbbase.questionreponseDAO().getAllquetionreponse();

        QuestionReponseAdapter questRepAdapter = new QuestionReponseAdapter(this, listqueetreponse);
        ListView listView = findViewById(R.id.listvue);
        listView.setAdapter(questRepAdapter);







        ImageView closebnt= findViewById(R.id.closebtn);











        closebnt.setOnClickListener(view -> {

            overridePendingTransition(R.anim.right_in,R.anim.left_out);
            finish();
        });
    }
}