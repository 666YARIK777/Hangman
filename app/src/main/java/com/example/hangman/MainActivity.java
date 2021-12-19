package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    EditText lgn;
    EditText pwd;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button2);
        lgn = findViewById(R.id.login);
        pwd = findViewById(R.id.password);
        error = findViewById(R.id.error);

        btn.setOnClickListener(v -> login(lgn.getText().toString(), pwd.getText().toString()));
        btn2.setOnClickListener(v -> sign_up(lgn.getText().toString(), pwd.getText().toString()));

    }

    private void login(String login, String password) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(() -> {
            try {
                URL url = new URL("http://192.168.0.132:8080");
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("POST");

                httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpConnection.setRequestProperty("Accept", "application/json");
                httpConnection.setDoOutput(true);

                JSONObject requestJSON = new JSONObject();
                requestJSON.put("command", "login");
                requestJSON.put("username", login);
                requestJSON.put("password", password);

                byte[] bytesToSend = requestJSON.toString().getBytes(StandardCharsets.UTF_8);
                httpConnection.getOutputStream().write(bytesToSend);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                String input = in.readLine();

                StringBuilder strBld = new StringBuilder();

                while (input != null) {
                    strBld.append(input);
                    input = in.readLine();
                }
                JSONObject responseJSON = new JSONObject(strBld.toString());
                String resultSI = responseJSON.getString("status");
                String resultM = responseJSON.getString("message");

                if (resultSI.equals("OK")) {
                    System.out.println(resultM);
                    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
                    handler.post(() -> {
                        error.setText("");
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    });
                } else {

                    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
                    handler.post(() -> {
                        error.setText(resultM);
                    });
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void sign_up(String login, String password) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(() -> {
            try {
                URL url = new URL("http://192.168.0.132:8080");
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("POST");

                httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpConnection.setRequestProperty("Accept", "application/json");
                httpConnection.setDoOutput(true);

                JSONObject requestJSON = new JSONObject();
                requestJSON.put("command", "sign_up");
                requestJSON.put("username", login);
                requestJSON.put("password", password);

                byte[] bytesToSend = requestJSON.toString().getBytes(StandardCharsets.UTF_8);
                httpConnection.getOutputStream().write(bytesToSend);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                String input = in.readLine();

                StringBuilder strBld = new StringBuilder();

                while (input != null) {
                    strBld.append(input);
                    input = in.readLine();
                }
                JSONObject responseJSON = new JSONObject(strBld.toString());
                String resultSI = responseJSON.getString("status");
                String resultM = responseJSON.getString("message");

                if (resultSI.equals("OK")) {
                    System.out.println(resultM);
                    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
                    handler.post(() -> {
                        error.setText("");
                    });
                } else {

                    Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
                    handler.post(() -> {
                        error.setText(resultM);
                    });
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
