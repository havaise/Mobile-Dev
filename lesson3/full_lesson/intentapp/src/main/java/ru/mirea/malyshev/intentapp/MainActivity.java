package ru.mirea.malyshev.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TIME_KEY = "time_key";
    public static final String SQUARE_KEY = "square_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendTime(View view) {
        long dateInMillis = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String dateString = sdf.format(new Date(dateInMillis));

        int myNumberInGroup = 15; // поставь свой номер по списку
        int square = myNumberInGroup * myNumberInGroup;

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(TIME_KEY, dateString);
        intent.putExtra(SQUARE_KEY, square);
        startActivity(intent);
    }
}