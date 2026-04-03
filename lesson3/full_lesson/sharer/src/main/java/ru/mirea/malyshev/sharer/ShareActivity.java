package ru.mirea.malyshev.sharer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView textViewReceived = findViewById(R.id.textViewReceived);

        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (text == null) {
            text = "Данные не получены";
        }

        textViewReceived.setText("Получен текст: " + text);
    }
}