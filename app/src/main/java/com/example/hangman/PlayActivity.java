package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        GridView letters = findViewById(R.id.Letters);
        LetterAdapter adapter = new LetterAdapter(this);

        letters.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        String word = sharedPreferences.getString("WORD", "контакт");

    }

    public void letterPressed(View view) {
    }
}