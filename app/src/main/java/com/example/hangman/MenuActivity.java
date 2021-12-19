package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button play = findViewById(R.id.Play);
        play.setOnClickListener(v -> startPlay());
    }

    void startPlay(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(() -> {
            try {
                URL url = new URL("http://192.168.0.132:8080/words");
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");

                httpConnection.setRequestProperty("Content-Type",
                        "application/json; charset=utf-8");
                httpConnection.setRequestProperty("Accept", "application/json");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                String input = in.readLine();

                StringBuilder strBld = new StringBuilder();

                while (input != null) {
                    strBld.append(input);
                    input = in.readLine();
                }
                JSONObject responseJSON = new JSONObject(strBld.toString());

                JSONArray inputWords = responseJSON.getJSONArray("words");
                String[] words = new String[inputWords.length()];
                for (int i = 0; i < words.length; i++) {
                    words[i] = inputWords.getString(i);
                }
                System.out.println(Arrays.toString(words));

                Random rand = new Random();

                int wordIndex = rand.nextInt(words.length);

                SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("WORD", words[wordIndex]);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                startActivity(intent);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}