package ru.mirea.malyshev.simplefragmentapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        setContentView(R.layout.activity_main);

        if (!isLandscape && savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new FirstFragment())
                    .commit();
        }
    }

    public void onClick(View view) {
        if (isLandscape) {
            return;
        }

        Fragment fragment;

        if (view.getId() == R.id.btnFirstFragment) {
            fragment = new FirstFragment();
        } else {
            fragment = new SecondFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}