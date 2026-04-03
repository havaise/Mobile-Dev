package ru.mirea.malyshev.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewResult = findViewById(R.id.textViewResult);

        Intent intent = getIntent();
        String time = intent.getStringExtra(MainActivity.TIME_KEY);
        int square = intent.getIntExtra(MainActivity.SQUARE_KEY, 0);

        String result = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ " +
                "СОСТАВЛЯЕТ ЧИСЛО " + square + ", а текущее время " + time;

        textViewResult.setText(result);
    }
}