package com.example.flashcard;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private int curentquestionreponseposition=1;
    CountDownTimer countDownTimer = null;


    int retour=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        CountDownTimer countDownTimer = new CountDownTimer(16000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView timerTextView = findViewById(R.id.timer);
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // Do something when the countdown finishes
            }
        };

        countDownTimer.cancel();
        countDownTimer.start();

      Animation leftOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out);
      Animation rightInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in);


        TextView txtquestion = findViewById(R.id.questiontextview);
        TextView  txtanswer1 = findViewById(R.id.txtVanswer1);
        TextView  txtanswer2 = findViewById(R.id.txtVanswer2);
        TextView  txtanswer3 = findViewById(R.id.txtVanswer3);



        txtanswer1.setVisibility(View.INVISIBLE);
        txtanswer2.setVisibility(View.INVISIBLE);
        txtanswer3.setVisibility(View.INVISIBLE);





        ImageView previoustbnt = findViewById(R.id.previousbtn);
        ImageView nextbtn = findViewById(R.id.NextBtn);
        ImageView deletebnt = findViewById(R.id.deletebtn);
        deletebnt.setVisibility(View.INVISIBLE);
        ImageView btnadd = findViewById(R.id.btnaddnew);
        ImageView btnedit = findViewById(R.id.btnediter);
        btnedit.setVisibility(View.INVISIBLE);
        ImageView listquetionreponse = findViewById(R.id.listviewbtn);


        Toast.makeText(this, "cliquer sur la question pour trouver la reponse  ", Toast.LENGTH_SHORT).show();
        ActivityResultLauncher<Intent> activityLancher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    Log.d("data ", "onActivityResult:  from addactivity");
                    if(result.getResultCode()==78){
                        Intent intent = result.getData();


                        if(intent!=null){
                            String question = intent.getStringExtra("question");
                            txtquestion.setText(question);

                            String answer1 = intent.getStringExtra("answer1");
                            txtanswer1.setText(answer1);

                            String answer2 = intent.getStringExtra("answer2");
                            txtanswer2.setText(answer2);

                            String answer3 = intent.getStringExtra("answer3");
                            txtanswer3.setText(answer3);

                        }

                    }

                });




        txtquestion.setOnClickListener(view -> {


            txtanswer1.setVisibility(View.VISIBLE);
            txtanswer2.setVisibility(View.VISIBLE);
            txtanswer3.setVisibility(View.VISIBLE);
            btnedit.setVisibility(View.VISIBLE);



            deletebnt.setVisibility(View.VISIBLE);
            Toast.makeText( MainActivity.this, "cliquer sur la question pour trouver la reponse  ", Toast.LENGTH_SHORT).show();



        });

        btnadd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, activity_add_card.class);
            activityLancher.launch(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });


        // This section allows us to displyay the question from the database bry pressing previuous and next button
        QuestionReponseDatabase dbbase= Room.databaseBuilder(getApplicationContext(),QuestionReponseDatabase.class,"mydatabase").allowMainThreadQueries().build();
        /////////////////////////              PREVIOUS BUTTON               /////////////////////////////////////////////////////////
        previoustbnt.setOnClickListener(view -> {
            countDownTimer.cancel();
            countDownTimer.start();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                activityLancher.launch(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                Toast.makeText( MainActivity.this, "Vous n'avez plus de question ", Toast.LENGTH_SHORT).show();
               txtquestion.startAnimation(rightInAnim);
                txtanswer1.startAnimation(rightInAnim);
               txtanswer2.startAnimation(rightInAnim);
              txtanswer3.startAnimation(rightInAnim);



        });

 /////////////////////////                 NEXT BUTTON /////////////////////////////////////////////////////////

        nextbtn.setOnClickListener(view -> {
            // Call the question and answer by getquestionreponsparId created in our DAO CLASS
            QuestionReponse Quetionreponsesuuivant =  dbbase.questionreponseDAO().getquestionreponsparnext(curentquestionreponseposition,curentquestionreponseposition);
            countDownTimer.cancel();
            countDownTimer.start();




            if(Quetionreponsesuuivant != null){

                txtquestion.setText(Quetionreponsesuuivant.Question);
                txtanswer1.setText(Quetionreponsesuuivant.Answer_1);
                txtanswer2.setText(Quetionreponsesuuivant.Answer_2);
                txtanswer3.setText(Quetionreponsesuuivant.Answer_3);

                txtquestion.startAnimation(leftOutAnim);
                txtanswer1.startAnimation(leftOutAnim);
                txtanswer2.startAnimation(leftOutAnim);
                txtanswer3.startAnimation(leftOutAnim);
                curentquestionreponseposition = curentquestionreponseposition + 1;
                findViewById(R.id.questiontextview).animate()
                        .rotationY(90f)
                        .setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                txtquestion.setVisibility(View.VISIBLE);
                                //findViewById(R.id.txtVanswer1).setVisibility(View.VISIBLE);
                                // second quarter turn

                                findViewById(R.id.questiontextview).setRotationY(-90f);
                                findViewById(R.id.questiontextview).animate()
                                        .rotationY(0f)
                                        .setDuration(200)
                                        .start();
                            }
                        })
                        .start();






                Log.d("question", ""+curentquestionreponseposition +" question"+Quetionreponsesuuivant.ID +"retaou id " +retour);


            }else{
                Toast.makeText( MainActivity.this, "Vous etre a la fin de la liste des question", Toast.LENGTH_SHORT).show();
            }

        });

        /////////////////////////              DELETE BUTTON               /////////////////////////////////////////////////////////


        deletebnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbbase.questionreponseDAO().deletequestionReponse(txtquestion.getText().toString());
                Snackbar.make(deletebnt,"Question supprimé avec succès...", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                activityLancher.launch(intent);

            }
        });


        ActivityResultLauncher<Intent> activityLancher2 = registerForActivityResult(



                new ActivityResultContracts.StartActivityForResult() , result ->{



                });





        listquetionreponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, Activitylistesclasse.class);
                overridePendingTransition(R.anim.left_out,R.anim.right_in);
                activityLancher.launch(intent);

            }
        });





    btnedit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, activity_add_card.class);

            intent.putExtra("question",txtquestion.getText().toString());
            intent.putExtra("answer1",txtanswer1.getText().toString());
            intent.putExtra("answer2",txtanswer2.getText().toString());
            intent.putExtra("answer3",txtanswer3.getText().toString());

            setResult(RESULT_OK,intent);
            activityLancher.launch(intent);


        }
    });


    }
}