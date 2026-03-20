package ru.mirea.malyshev.timedialogapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements MyTimeDialogFragment.OnTimeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showTimeDialog(View view) {
        MyTimeDialogFragment dialogFragment = new MyTimeDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "time_dialog");
    }

    @Override
    public void onTimeSelected(int hour, int minute) {
        Toast.makeText(this,
                "Вы выбрали время: " + hour + ":" + minute,
                Toast.LENGTH_LONG).show();
    }
}