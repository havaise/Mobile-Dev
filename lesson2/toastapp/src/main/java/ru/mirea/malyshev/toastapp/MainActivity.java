package ru.mirea.malyshev.toastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextToast = findViewById(R.id.editTextToast);
    }

    public void showToastMessage(View view) {
        String text = editTextToast.getText().toString();
        int count = text.length();

        Toast.makeText(
                this,
                "СТУДЕНТ № 1 ГРУППА БСБО-08-23 Количество символов - " + count,
                Toast.LENGTH_LONG
        ).show();
    }
}