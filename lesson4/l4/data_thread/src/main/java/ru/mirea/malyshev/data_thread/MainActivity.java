package ru.mirea.malyshev.data_thread;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;
import ru.mirea.malyshev.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStart.setOnClickListener(v -> {
            binding.tvInfo.setText("Start thread...\n");

            final Runnable runn1 = () ->
                    binding.tvInfo.append("1) runOnUiThread -> runs in UI thread immediately\n");

            final Runnable runn2 = () ->
                    binding.tvInfo.append("2) post -> added to UI queue\n");

            final Runnable runn3 = () ->
                    binding.tvInfo.append("3) postDelayed -> runs with delay\n");

            Thread thread = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);

                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.post(runn2);
                    binding.tvInfo.postDelayed(runn3, 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            thread.start();
        });
    }
}