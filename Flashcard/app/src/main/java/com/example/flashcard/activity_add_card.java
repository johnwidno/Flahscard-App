package com.example.flashcard;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class activity_add_card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        TextView btnsave =findViewById(R.id.btnsave);
        ImageView cancelbtn =findViewById(R.id.btncancel);
        TextView edittextquestion = findViewById(R.id.editquestion);
        TextView edtanswer1 = findViewById(R.id.editanswer1);
        TextView edtanswer2 = findViewById(R.id.editanswer2);
        TextView edtanswer3 = findViewById(R.id.editanswer3);


        btnsave.setOnClickListener(view -> {
            if(edittextquestion.getText().toString().isEmpty()){

                Snackbar.make(btnsave,"Desolé !!!, Un ou plusieur champs vide", Snackbar.LENGTH_LONG).show();

            }else if (edtanswer1.getText().toString().isEmpty()){

                Snackbar.make(btnsave,"Desolé !!!, Un ou plusieur champs vide", Snackbar.LENGTH_LONG).show();

            }else if (edtanswer2.getText().toString().isEmpty()){


                Snackbar.make(btnsave,"Desolé !!!, Un ou plusieur champs vide", Snackbar.LENGTH_LONG).show();
            }else if (edtanswer3.getText().toString().isEmpty()){

                Snackbar.make(btnsave,"Desolé !!!, Un ou plusieur champs vide", Snackbar.LENGTH_LONG).show();


            }else {
                Intent intent = new Intent();
                intent.putExtra("question",edittextquestion.getText().toString());
                intent.putExtra("answer1",edtanswer1.getText().toString());
                intent.putExtra("answer2",edtanswer2.getText().toString());
                intent.putExtra("answer3",edtanswer3.getText().toString());

                QuestionReponseDatabase dbbase= Room.databaseBuilder(getApplicationContext(),QuestionReponseDatabase.class,"mydatabase").allowMainThreadQueries().build();

                QuestionReponse questRep = new QuestionReponse(0,
                        edittextquestion.getText().toString(),
                        edtanswer1.getText().toString(),
                        edtanswer2.getText().toString()
                        ,edtanswer3.getText().toString());

                dbbase.questionreponseDAO().InsertAll(questRep);

                List<QuestionReponse> listquestrep= dbbase.questionreponseDAO().getAllquetionreponse();

                for (QuestionReponse list: listquestrep){
                    Log.d("question", list.Question+" "+list.Answer_1 );
                }


                setResult(78,intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();


            }
        });

        cancelbtn.setOnClickListener(view -> {
            edittextquestion.setText("");
            edtanswer1.setText("");
            edtanswer2.setText("");
            edtanswer3.setText("");
        });



        ActivityResultLauncher<Intent> activityLancher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    Log.d("data ", "onActivityResult:  from addactivity");

                        Intent intent = result.getData();
                        if(intent!=null){
                            String question = intent.getStringExtra("question");
                            edittextquestion.setText(question);

                            String answer1 = intent.getStringExtra("answer1");
                            edtanswer1.setText(answer1);

                            String answer2 = intent.getStringExtra("answer2");
                            edtanswer1.setText(answer2);

                            String answer3 = intent.getStringExtra("answer3");
                            edtanswer1.setText(answer3);

                        }else {

                            Log.d("main activity ", "no data fpund: ");


                        }



                });






    }
}





















