package ru.mirea.malyshev.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import ru.mirea.malyshev.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 200;
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    private boolean isWork = false;
    private String recordFilePath;

    private Button recordButton;
    private Button playButton;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    private boolean isStartRecording = true;
    private boolean isStartPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recordButton = binding.recordButton;
        playButton = binding.playButton;

        recordButton.setText("Начать запись. БСБО-08-23. 15");
        playButton.setText("Воспроизвести");
        playButton.setEnabled(false);

        File audioFile = new File(
                getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "audiorecordtest.3gp"
        );
        recordFilePath = audioFile.getAbsolutePath();

        int audioRecordPermissionStatus =
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_CODE_PERMISSION
            );
        }

        recordButton.setOnClickListener(v -> {
            if (!isWork) {
                Toast.makeText(this, "Нет разрешения на запись аудио", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isStartRecording) {
                recordButton.setText("Остановить запись");
                playButton.setEnabled(false);
                startRecording();
            } else {
                recordButton.setText("Начать запись. БСБО-08-23. 15");
                stopRecording();

                File file = new File(recordFilePath);
                playButton.setEnabled(file.exists() && file.length() > 0);
            }

            isStartRecording = !isStartRecording;
        });

        playButton.setOnClickListener(v -> {
            File file = new File(recordFilePath);

            if (!file.exists() || file.length() == 0) {
                Toast.makeText(this, "Сначала запишите аудио", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isStartPlaying) {
                playButton.setText("Остановить воспроизведение");
                recordButton.setEnabled(false);
                startPlaying();
            } else {
                playButton.setText("Воспроизвести");
                recordButton.setEnabled(true);
                stopPlaying();
            }

            isStartPlaying = !isStartPlaying;
        });
    }

    private void startRecording() {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(recordFilePath);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();

            Log.d(TAG, "startRecording called");
            Toast.makeText(this, "Запись началась", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed", e);
            Toast.makeText(this, "Ошибка начала записи", Toast.LENGTH_SHORT).show();
        } catch (RuntimeException e) {
            Log.e(TAG, "startRecording() runtime error", e);
            Toast.makeText(this, "Не удалось начать запись", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
                Log.d(TAG, "stopRecording called");
                Toast.makeText(this, "Запись сохранена", Toast.LENGTH_SHORT).show();
            } catch (RuntimeException e) {
                Log.e(TAG, "stopRecording() failed", e);
                Toast.makeText(this, "Ошибка остановки записи", Toast.LENGTH_SHORT).show();
            } finally {
                recorder.release();
                recorder = null;
            }
        }
    }

    private void startPlaying() {
        try {
            player = new MediaPlayer();
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();

            Log.d(TAG, "startPlaying called");
            Toast.makeText(this, "Воспроизведение началось", Toast.LENGTH_SHORT).show();

            player.setOnCompletionListener(mp -> {
                stopPlaying();
                playButton.setText("Воспроизвести");
                recordButton.setEnabled(true);
                isStartPlaying = true;
                Toast.makeText(this, "Воспроизведение завершено", Toast.LENGTH_SHORT).show();
            });

        } catch (IOException e) {
            Log.e(TAG, "startPlaying() failed", e);
            Toast.makeText(this, "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            try {
                if (player.isPlaying()) {
                    player.stop();
                }
            } catch (RuntimeException e) {
                Log.e(TAG, "stopPlaying() failed", e);
            } finally {
                player.release();
                player = null;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (recorder != null) {
            stopRecording();
        }

        if (player != null) {
            stopPlaying();
        }

        isStartRecording = true;
        isStartPlaying = true;

        recordButton.setText("Начать запись. БСБО-08-23. 15");
        playButton.setText("Воспроизвести");
        recordButton.setEnabled(true);

        File file = new File(recordFilePath);
        playButton.setEnabled(file.exists() && file.length() > 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

        if (isWork) {
            Toast.makeText(this, "Разрешение на микрофон получено", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Без разрешения запись невозможна", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}