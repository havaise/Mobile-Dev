package ru.mirea.malyshev.datedialogapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements MyDateDialogFragment.OnDateSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDateDialog(View view) {
        MyDateDialogFragment dialogFragment = new MyDateDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "date_dialog");
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        Toast.makeText(this,
                "Вы выбрали дату: " + day + "." + (month + 1) + "." + year,
                Toast.LENGTH_LONG).show();
    }
}