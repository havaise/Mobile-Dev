package ru.mirea.malyshev.thread;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.Locale;
import ru.mirea.malyshev.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.tvThreadInfo.setText("Current thread: " + mainThread.getName());

        mainThread.setName("Malyshev, номер по списку: 15");
        binding.tvThreadInfo.append("\nNew thread name: " + mainThread.getName());

        Log.d(MainActivity.class.getSimpleName(),
                "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.buttonCalc.setOnClickListener(v -> {
            String lessonsStr = binding.etLessons.getText().toString().trim();
            String daysStr = binding.etDays.getText().toString().trim();

            if (lessonsStr.isEmpty() || daysStr.isEmpty()) {
                binding.tvResult.setText("Fill all fields");
                return;
            }

            int lessons = Integer.parseInt(lessonsStr);
            int days = Integer.parseInt(daysStr);

            if (days == 0) {
                binding.tvResult.setText("Days cannot be 0");
                return;
            }

            new Thread(() -> {
                int numberThread = counter++;

                Log.d("ThreadProject",
                        String.format(Locale.US,
                                "Запущен поток № %d студентом Malyshev, номер по списку № %d",
                                numberThread, 15));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                final double average = (double) lessons / days;

                runOnUiThread(() ->
                        binding.tvResult.setText(
                                String.format(Locale.US,
                                        "Average lessons per day: %.2f", average)));

                Log.d("ThreadProject", "Выполнен поток № " + numberThread);
            }).start();
        });
    }
}