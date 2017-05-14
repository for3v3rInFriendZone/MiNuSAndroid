package util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.com.minus.R;
import android.com.minus.activities.MainActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.util.Calendar;

public class YearPickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final int MAX_YEAR = 2099;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.year_dialog, null);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        onDateSet(null, yearPicker.getValue(), 0, 0);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        YearPickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ((MainActivity) getActivity()).showDate(year);
        ((MainActivity) getActivity()).setSelectedYear(year);
        ((MainActivity)getActivity()).callReport();
    }
}
