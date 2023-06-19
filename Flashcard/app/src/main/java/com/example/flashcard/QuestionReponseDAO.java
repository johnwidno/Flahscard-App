package com.example.flashcard;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionReponseDAO {
    @Insert
    void InsertAll(QuestionReponse ... questionreponse);

    @Query("SELECT * FROM questionreponse")
    List< QuestionReponse > getAllquetionreponse();

    @Query("SELECT * FROM questionreponse LIMIT :limit OFFSET :offsett")
    QuestionReponse getquestionreponsparnext( int limit , int offsett);

    @Query("DELETE FROM  QuestionReponse WHERE Quetion = :questiondiplay")
    void deletequestionReponse(String questiondiplay);

/*
    @Query("UPDATE  QuestionReponse set Quetion=:questiondiplay ,Answer_1 =:ans1 , Answer_2 =:ans2 ,Answer_3 =:ans3  where Quetion= :questiondiplay")
    void UpdatequestionReponse(String questiondiplay ,String ans1,String ans2,String ans3);
*/
}
