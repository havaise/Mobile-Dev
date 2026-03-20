package ru.mirea.malyshev.timedialogapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment {

    public interface OnTimeSelectedListener {
        void onTimeSelected(int hour, int minute);
    }

    private OnTimeSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeSelectedListener) {
            listener = (OnTimeSelectedListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(requireContext(),
                (view, selectedHour, selectedMinute) -> {
                    if (listener != null) {
                        listener.onTimeSelected(selectedHour, selectedMinute);
                    }
                },
                hour, minute, true);
    }
}