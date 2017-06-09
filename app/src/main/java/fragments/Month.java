package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.com.minus.R;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
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
import util.MonthBudgetDataPicker;
import util.MonthYearPickerDialog;
import util.RetrofitBuilder;
import util.SharedSession;

public class Month extends Fragment {

    TextView datum;
    ImageButton datePicker;
    private Calendar calendar;
    EditText budzet;
    Button primeni;
    BudgetDAO budgetDAO;
    Retrofit retrofit;
    User logedUser;
    Budget budget = null;
    int month, year, selectedMonth, selectedYear ;
    List<Budget> budgets = new ArrayList<Budget>();
    Activity activity;

    public Month() {

    }

    public static Month newInstance() {
        Month fragment = new Month();
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
        checkIfBudgetExist(month, year);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        datum = (TextView) rootView.findViewById(R.id.izabraniMesec);
        datePicker = (ImageButton) rootView.findViewById(R.id.monthPicker);
        budzet = (EditText) rootView.findViewById(R.id.monthBudget);
        primeni = (Button) rootView.findViewById(R.id.primeniMesecniBudzet);
        primeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primeniBudzet();
            }
        });

        selectedMonth = calendar.get(Calendar.MONTH);
        selectedYear = calendar.get(Calendar.YEAR);


        showDate(selectedMonth + 1, selectedYear);

        datePicker.setImageResource(R.mipmap.ic_calendar_range);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthBudgetDataPicker pd = new MonthBudgetDataPicker();
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });

        return rootView;
    }

    public void showDate(int month, int year){
        datum.setText(new StringBuilder().append(month).append(".").append(year));
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
            Calendar dateFromCalendar = Calendar.getInstance();
            Calendar dateToCalendar = Calendar.getInstance();
            dateFromCalendar.set(selectedYear, selectedMonth, 1);
            dateToCalendar.set(selectedYear, selectedMonth, dateFromCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            budget = new Budget(dateFromCalendar.getTimeInMillis(), dateToCalendar.getTimeInMillis(),
                    Double.parseDouble(budzet.getText().toString()), logedUser);
            budgetDAO.save(budget).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(activity.getApplicationContext(), "Mesečni budžet uspešno postavljen.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void checkIfBudgetExist(final int month, final int year){
        budgetDAO.findUserBudgets(logedUser.getId()).enqueue(new Callback<List<Budget>>() {
            @Override
            public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                if(response.isSuccessful()){

                    for(Budget b : response.body()){
                        if(!b.getDateFrom().equals(b.getDateTo())){
                            budgets.add(b);
                        }
                    }

                    boolean postoji = false;
                    Calendar dateFromCalendar = Calendar.getInstance();
                    Calendar dateToCalendar = Calendar.getInstance();

                    for(Budget b : budgets){
                        dateFromCalendar.setTime(new Date(b.getDateFrom()));
                        dateToCalendar.setTime(new Date(b.getDateTo()));
                        if((dateFromCalendar.get(Calendar.MONTH) == dateToCalendar.get(Calendar.MONTH)) &&
                                (dateFromCalendar.get(Calendar.YEAR) == dateToCalendar.get(Calendar.YEAR))){
                            if((dateFromCalendar.get(Calendar.MONTH) == month) && (dateFromCalendar.get(Calendar.YEAR) == year)){
                                postoji = true;
                                budget = b;
                                budzet.setText(String.valueOf(b.getValue()));
                                break;
                            }
                        }
                    }

                    if(!postoji){
                        budget = null;
                        budzet.setText("");
                    }
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

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }
}
