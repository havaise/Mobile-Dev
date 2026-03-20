package ru.mirea.malyshev.snackbarapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSnackbar = findViewById(R.id.editTextSnackbar);
    }

    public void showSnackbar(View view) {
        String text = editTextSnackbar.getText().toString();

        Snackbar.make(view,
                "Вы ввели: " + text,
                Snackbar.LENGTH_LONG).show();
    }
}