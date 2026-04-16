package ru.mirea.malyshev.mireaproject.ui.audio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import ru.mirea.malyshev.mireaproject.R;

import java.io.File;
import java.io.IOException;

public class AudioFragment extends Fragment {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 400;

    private Button recordButton;
    private Button playButton;
    private TextView infoTextView;

    private boolean isRecordingStart = true;
    private boolean isPlayingStart = true;
    private boolean isAudioPermissionGranted = false;

    private String recordFilePath;
    private MediaRecorder recorder;
    private MediaPlayer player;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        recordButton = view.findViewById(R.id.buttonRecordAudio);
        playButton = view.findViewById(R.id.buttonPlayAudio);
        infoTextView = view.findViewById(R.id.textAudioInfo);

        File audioFile = new File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "mirea_voice_note_15.3gp"
        );
        recordFilePath = audioFile.getAbsolutePath();

        checkAudioPermission();

        playButton.setEnabled(audioFile.exists() && audioFile.length() > 0);

        recordButton.setOnClickListener(v -> {
            if (!isAudioPermissionGranted) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
                return;
            }

            if (isRecordingStart) {
                startRecording();
                recordButton.setText("Остановить запись");
                playButton.setEnabled(false);
                infoTextView.setText("Идёт запись голосовой заметки...");
            } else {
                stopRecording();
                recordButton.setText("Начать запись. БСБО-08-23. №15");

                File file = new File(recordFilePath);
                playButton.setEnabled(file.exists() && file.length() > 0);
                infoTextView.setText("Запись сохранена");
            }

            isRecordingStart = !isRecordingStart;
        });

        playButton.setOnClickListener(v -> {
            File file = new File(recordFilePath);
            if (!file.exists() || file.length() == 0) {
                Toast.makeText(requireContext(), "Сначала запишите аудио", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isPlayingStart) {
                startPlaying();
                playButton.setText("Остановить воспроизведение");
                recordButton.setEnabled(false);
                infoTextView.setText("Воспроизведение голосовой заметки...");
            } else {
                stopPlaying();
                playButton.setText("Воспроизвести");
                recordButton.setEnabled(true);
                infoTextView.setText("Воспроизведение остановлено");
            }

            isPlayingStart = !isPlayingStart;
        });

        return view;
    }

    private void checkAudioPermission() {
        isAudioPermissionGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED;
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
            Toast.makeText(requireContext(), "Запись началась", Toast.LENGTH_SHORT).show();
        } catch (IOException | RuntimeException e) {
            Toast.makeText(requireContext(), "Ошибка начала записи", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
            } catch (RuntimeException ignored) {
            }
            recorder.release();
            recorder = null;
        }
    }

    private void startPlaying() {
        try {
            player = new MediaPlayer();
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();

            player.setOnCompletionListener(mp -> {
                stopPlaying();
                playButton.setText("Воспроизвести");
                recordButton.setEnabled(true);
                isPlayingStart = true;
                infoTextView.setText("Воспроизведение завершено");
            });
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (recorder != null) {
            stopRecording();
        }

        if (player != null) {
            stopPlaying();
        }

        isRecordingStart = true;
        isPlayingStart = true;

        recordButton.setText("Начать запись. БСБО-08-23. №15");
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

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            isAudioPermissionGranted = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (!isAudioPermissionGranted) {
                Toast.makeText(requireContext(), "Разрешение на микрофон не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }
}