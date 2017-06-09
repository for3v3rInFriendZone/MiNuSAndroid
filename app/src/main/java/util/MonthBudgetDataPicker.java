package util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.com.minus.R;
import android.com.minus.activities.CashControlActivity;
import android.com.minus.activities.MainActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.util.Calendar;
import java.util.List;

import fragments.Month;

public class MonthBudgetDataPicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private static final int MAX_YEAR = 2099;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_year_dialog, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonthBudgetDataPicker.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(getActivity() instanceof CashControlActivity){
            List<Fragment> fragments = ((CashControlActivity) getActivity()).getSupportFragmentManager().getFragments();
            for(Fragment f : fragments){
                if (f instanceof Month){
                    ((Month) f).showDate(month, year);
                    ((Month) f).checkIfBudgetExist(month-1, year);
                    ((Month) f).setSelectedMonth(month - 1);
                    ((Month) f).setSelectedYear(year);
                }
            }
        }
    }
}
