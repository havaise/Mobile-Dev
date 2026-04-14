package ru.mirea.malyshev.mireaproject.ui.background;

import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import ru.mirea.malyshev.mireaproject.R;
import ru.mirea.malyshev.mireaproject.SimpleWorker;

public class BackgroundTaskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_background_task, container, false);

        Button button = view.findViewById(R.id.buttonRunTask);
        TextView text = view.findViewById(R.id.textTaskInfo);

        button.setOnClickListener(v -> {
            WorkRequest work =
                    new OneTimeWorkRequest.Builder(SimpleWorker.class).build();

            WorkManager.getInstance(requireContext()).enqueue(work);

            text.setText("Task enqueued");
        });

        return view;
    }
}