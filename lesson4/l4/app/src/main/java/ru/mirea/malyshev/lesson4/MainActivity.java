package ru.mirea.malyshev.lesson4;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.malyshev.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTrackTitle.setText("Step On Up");
        binding.tvArtist.setText("The Living Tombstone");

        binding.buttonPlay.setOnClickListener(v ->
                Toast.makeText(this, "Play pressed", Toast.LENGTH_SHORT).show());

        binding.buttonStop.setOnClickListener(v ->
                Toast.makeText(this, "Stop pressed", Toast.LENGTH_SHORT).show());
    }
}