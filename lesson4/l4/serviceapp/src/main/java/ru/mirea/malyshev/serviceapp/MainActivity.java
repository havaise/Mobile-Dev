package ru.mirea.malyshev.serviceapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.malyshev.serviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textTrack.setText("Step On Up");
        binding.textArtist.setText("The Living Tombstone");

        binding.buttonPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayerService.class);
            intent.putExtra("action", "play");
            startService(intent);
        });

        binding.buttonStop.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayerService.class);
            intent.putExtra("action", "stop");
            startService(intent);
        });
    }
}