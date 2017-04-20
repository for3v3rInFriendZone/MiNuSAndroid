package fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.com.minus.activities.AddBillActivity;
import android.com.minus.activities.MainActivity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.com.minus.R;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Activity activity = getActivity();
        if(activity instanceof AddBillActivity) {
            ((AddBillActivity) getActivity()).showDate(year, month+1, day);
        } else if(activity instanceof MainActivity) {
            ((MainActivity) getActivity()).showDate(year, month+1, day);
        }
        // Do something with the date chosen by the user

    }

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance() {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
