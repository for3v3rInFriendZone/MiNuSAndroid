package fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.com.minus.activities.MainActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.com.minus.R;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DAO.BudgetDAO;
import DAO.UserDAO;
import model.Budget;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.RetrofitBuilder;
import util.SharedSession;


public class Day extends Fragment{

    TextView datum;
    ImageButton datePicker;
    private Calendar calendar, selectedDate;
    EditText budzet;
    Button primeni;
    BudgetDAO budgetDAO;
    Retrofit retrofit;
    User logedUser;
    Budget budget = null;
    int day,month,year;
    List<Budget> budgets = new ArrayList<Budget>();
    Activity activity;

    public Day() {
        // Required empty public constructor
    }

    public static Day newInstance(int page) {
        Day fragment = new Day();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        calendar = Calendar.getInstance();
        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        budgetDAO = retrofit.create(BudgetDAO.class);
        logedUser = SharedSession.getSavedObjectFromPreference(getActivity().getApplicationContext(), "userSession", "user", User.class);
        budgetDAO.findUserBudgets(logedUser.getId()).enqueue(new Callback<List<Budget>>() {
            @Override
            public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                if(response.isSuccessful()){
                    budgets = response.body();

                    checkIfBudgetExist(day, month, year);
                }else{
                    Log.e("Poruka : ", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Budget>> call, Throwable t) {
                Log.e("Poruka : ", t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        datum = (TextView) rootView.findViewById(R.id.datumZaBudzet);
        datePicker = (ImageButton) rootView.findViewById(R.id.kalendar);
        budzet = (EditText) rootView.findViewById(R.id.dayBudget);
        primeni = (Button) rootView.findViewById(R.id.primeniBudzet);
        primeni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                primeniBudzet();
            }
        });

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        setDate(day, month, year);

        datePicker.setImageResource(R.mipmap.ic_calendar_range);
        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);*/
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth) {
                        calendar.set(yearSelected, monthOfYear, dayOfMonth);
                        setDate(dayOfMonth, monthOfYear, yearSelected);
                        checkIfBudgetExist(dayOfMonth, monthOfYear, yearSelected);
                    }
                }, year, month, day);
                datePicker.show();
            }
        });
        return rootView;
    }

    public void setDate(int day, int month, int year){
        datum.setText(new StringBuilder().append(day).append(".")
                .append(month + 1).append(".").append(year));
    }

    public void primeniBudzet(){
        if(budget != null){
            budget.setValue(Double.parseDouble(budzet.getText().toString()));
            budgetDAO.update(budget).enqueue(new Callback<Budget>() {
                @Override
                public void onResponse(Call<Budget> call, Response<Budget> response) {
                    Toast.makeText(activity.getApplicationContext(), "Uspesno ste promenili budžet.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Budget> call, Throwable t) {

                }
            });
        }else{
            budget = new Budget(calendar.getTimeInMillis(), calendar.getTimeInMillis(),
                    Double.parseDouble(budzet.getText().toString()), logedUser);
            budgetDAO.save(budget).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(activity.getApplicationContext(), "Dnevni budzet uspesno podesen.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void checkIfBudgetExist(int checkDay, int checkMonth, int checkYear){
        boolean postoji = false;
        Calendar dateFromCalendar = Calendar.getInstance();
        Calendar dateToCalendar = Calendar.getInstance();

        for(Budget b : budgets) {
            dateFromCalendar.setTime(new Date(b.getDateFrom()));
            dateToCalendar.setTime(new Date(b.getDateTo()));
            if ((dateToCalendar.get(Calendar.DAY_OF_MONTH) == dateFromCalendar.get(Calendar.DAY_OF_MONTH)) &&
                    (dateToCalendar.get(Calendar.MONTH) == dateFromCalendar.get(Calendar.MONTH)) &&
                    (dateToCalendar.get(Calendar.YEAR) == dateFromCalendar.get(Calendar.YEAR))){
                if ((checkDay == dateFromCalendar.get(Calendar.DAY_OF_MONTH)) &&
                        (checkMonth == dateFromCalendar.get(Calendar.MONTH)) &&
                        (checkYear == dateFromCalendar.get(Calendar.YEAR))) {
                    postoji = true;
                    budget = b;
                    budzet.setText(String.valueOf(budget.getValue()));
                    break;
                }else{
                    postoji = false;
                }
            }
        }

        if(!postoji){
            budget = null;
            budzet.setText("");
        }

    }
}
