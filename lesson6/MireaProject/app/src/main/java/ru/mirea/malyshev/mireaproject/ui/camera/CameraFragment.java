package ru.mirea.malyshev.mireaproject.ui.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import ru.mirea.malyshev.mireaproject.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 300;

    private ImageView imageView;
    private EditText editTextNote;
    private TextView textPhotoInfo;

    private Uri imageUri;
    private boolean isCameraPermissionGranted = false;

    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        imageView = view.findViewById(R.id.imageViewCamera);
        editTextNote = view.findViewById(R.id.editTextNote);
        textPhotoInfo = view.findViewById(R.id.textPhotoInfo);

        checkCameraPermission();

        ActivityResultCallback<ActivityResult> callback = result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                imageView.setImageURI(imageUri);
                String note = editTextNote.getText().toString().trim();
                if (note.isEmpty()) {
                    textPhotoInfo.setText("Фото сохранено в папку приложения");
                } else {
                    textPhotoInfo.setText("Фото-заметка: " + note);
                }
            }
        };

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                callback
        );

        imageView.setOnClickListener(v -> {
            if (!isCameraPermissionGranted) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                File photoFile = createImageFile();
                String authorities = requireContext().getPackageName() + ".fileprovider";
                imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                cameraLauncher.launch(cameraIntent);
            } catch (IOException e) {
                Toast.makeText(requireContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void checkCameraPermission() {
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            isCameraPermissionGranted = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (!isCameraPermissionGranted) {
                Toast.makeText(requireContext(), "Разрешение на камеру не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }
}