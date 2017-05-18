package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DAO.UserDAO;
import fragments.DatePickerFragment;
import model.Bill;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.MonthYearPickerDialog;
import util.RetrofitBuilder;
import util.SharedSession;
import util.SimpleDividerItemDecoration;
import adapter.BillRecyclerViewAdapter;
import util.YearPickerDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<List<Bill>> {

    private SearchView searchInput;
    private Toolbar toolbar;
    private TextView dayPicker;
    private Calendar calendar;
    private int year, month, day, selectedYear, selectedMonth;
    private ImageButton datePicker;
    private FloatingActionButton newBill;
    private RecyclerView recyclerView;
    private BarChart mChart;
    private UserDAO userDao;
    private Retrofit retrofit;
    private User logedUser;
    private List<Bill> bills = new ArrayList<Bill>();
    private Report report;

    public enum Report {
        ALL,
        DAY,
        MONTH,
        YEAR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lista računa");
        setSupportActionBar(toolbar);

        logedUser = SharedSession.getSavedObjectFromPreference(getApplicationContext(), "userSession", "user", User.class);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        selectedYear = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        selectedMonth = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dayPicker = (TextView) findViewById(R.id.day_picker_report);
        datePicker = (ImageButton) findViewById(R.id.day_date);
        datePicker.setImageResource(R.mipmap.ic_calendar_range);

        mChart = (BarChart) findViewById(R.id.bar_chart);
        mChart.setDescription(" ");
        mChart.animateXY(2000, 3000);
        mChart.setBackgroundColor(Color.rgb(30,144,255));
        mChart.setGridBackgroundColor(Color.rgb(149, 27, 75));
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
       /* xAxis.setTypeface(mTf);*/
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        mChart.invalidate();
        mChart.setVisibility(View.INVISIBLE);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        userDao = retrofit.create(UserDAO.class);

        searchInput = (SearchView) findViewById(R.id.search_input);
        searchInput.setQueryHint("Pretraga računa...");

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        assert recyclerView != null;
        userDao.findUserBills(logedUser.getId()).enqueue(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        newBill = (FloatingActionButton) findViewById(R.id.fab);
        newBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBillView(v);
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Bill> bills) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BillRecyclerViewAdapter(bills));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.day_report) {
            report = Report.DAY;
            dayReport();
        } else if (id == R.id.month_report) {
            report = Report.MONTH;
            monthReport();
        } else if (id == R.id.year_report) {
            report = Report.YEAR;
            yearReport();
        }  else if (id == R.id.nav_settings) {
            settingsOption();
        } else if (id == R.id.nav_logout) {
            logout_action();
        } else if(id == R.id.all_bills) {
            report = Report.ALL;
            all_bills();
        } else if(id == R.id.cash_control){
            cashControl();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callReport(){
        switch (report){
            case ALL:
                all_bills();
                break;
            case DAY:
                dayReport();
                break;
            case MONTH:
                monthReport();
                break;
            case YEAR:
                yearReport();
                break;
        }
    }

    private void cashControl() {
        Intent i = new Intent(this, CashControlActivity.class);
        startActivity(i);
    }

    private void settingsOption() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void newBillView(View v){
        Intent i = new Intent(this, AddBillActivity.class);
        startActivity(i);
    }

    private void logout_action() {
        Intent i = new Intent(this, LoadingScreenActivity.class);
        startActivity(i);
    }

    private void dayReport() {

        mChart.setVisibility(View.INVISIBLE);
        toolbar.setTitle("Dnevni pregled");
        dayPicker.setVisibility(View.VISIBLE);
        newBill.setVisibility(View.GONE);

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= RecyclerView.LayoutParams.WRAP_CONTENT;
        recyclerView.setLayoutParams(params);

        //Showing currnetly date when open window first time
        showDate(year, month+1, day);
       // adapter.filter("22.03.2017");


        datePicker.setVisibility(View.VISIBLE);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void monthReport() {

        mChart.setVisibility(View.VISIBLE);
        toolbar.setTitle("Mesečni pregled");
        newBill.setVisibility(View.GONE);
        if(year != selectedYear && month+1 != selectedMonth){
            showDate(selectedMonth, selectedYear);
        } else {
            showDate(month+1, year);
        }

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= 400;
        recyclerView.setLayoutParams(params);



        dayPicker.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.VISIBLE);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });

        BarData bardata = new BarData(getMonthXAxisValues(),getMonthDataSet(selectedMonth, selectedYear, bills));
      /*  ...............Static Data for Data ListView..............................*/
        mChart.setData(bardata);
    }

    private void yearReport() {
        toolbar.setTitle("Godišnji pregled");
        newBill.setVisibility(View.GONE);
        if(year != selectedYear){
            showDate(selectedYear);
        } else {
            showDate(year);
        }

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= 400;
        recyclerView.setLayoutParams(params);

        dayPicker.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.VISIBLE);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearPickerDialog pd = new YearPickerDialog();
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });


        BarData bardata = new BarData(getYearXAxisValues(),getYearDataSet(selectedYear, bills));
      /*  ...............Static Data for Data ListView..............................*/

        mChart.setData(bardata);
        mChart.setVisibility(View.VISIBLE);
    }



    private ArrayList<IBarDataSet> getYearDataSet(int year, List<Bill> bills) {

        List<Bill> yearBills = new ArrayList<Bill>();
        double jan = 0.0;
        double feb = 0.0;
        double mar = 0.0;
        double apr = 0.0;
        double maj = 0.0;
        double jun = 0.0;
        double jul = 0.0;
        double avg = 0.0;
        double sep = 0.0;
        double oct = 0.0;
        double nov = 0.0;
        double dec = 0.0;

        for (int i = 0; i < bills.size(); i++){
            calendar.setTime(new Date(bills.get(i).getDate()));
            if(year == calendar.get(Calendar.YEAR)){
                yearBills.add(bills.get(i));
            }
        }

        setupRecyclerView((RecyclerView) recyclerView, yearBills);

        for (Bill bill : yearBills){
            calendar.setTime(new Date(bill.getDate()));
            if(calendar.get(Calendar.MONTH) == Calendar.JANUARY){
                jan += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY){
                feb += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.MARCH){
                mar += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.APRIL){
                apr += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.MAY){
                maj += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.JUNE){
                jun += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.JULY){
                jul += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.AUGUST){
                avg += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER){
                sep += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.OCTOBER){
                oct += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.NOVEMBER){
                nov += bill.getPrice().doubleValue();
            }else if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER){
                dec += bill.getPrice().doubleValue();
            }
        }

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry((float) jan, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry((float) feb, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry((float) mar, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry((float) apr, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry((float) maj, 4); // Maj
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry((float) jun, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry((float) jul, 6); // Jul
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry((float) avg, 7); // Avg
        valueSet1.add(v1e8);
        BarEntry v1e9 = new BarEntry((float) sep, 8); // Sep
        valueSet1.add(v1e9);
        BarEntry v1e10 = new BarEntry((float) oct, 9); // Okt
        valueSet1.add(v1e10);
        BarEntry v1e11 = new BarEntry((float) nov, 10); // Nov
        valueSet1.add(v1e11);
        BarEntry v1e12 = new BarEntry((float) dec, 11); // Dec
        valueSet1.add(v1e12);
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, " ");
        barDataSet1.setColor(Color.rgb(255,255,0));
        barDataSet1.setValueTextColor(Color.WHITE);
        barDataSet1.setHighLightColor(Color.WHITE);
        barDataSet1.setValueTextColor(Color.WHITE);
        barDataSet1.setBarSpacePercent(30f);
        // dataSets = new ArrayList<>();
        // dataSets.add(barDataSet1);
        ArrayList<IBarDataSet> datas = new ArrayList<IBarDataSet>();
        datas.add(barDataSet1);
        return datas;
    }

    private ArrayList<String> getYearXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAJ");
        xAxis.add("JUN");
        xAxis.add("JUL");
        xAxis.add("AVG");
        xAxis.add("SEP");
        xAxis.add("OKT");
        xAxis.add("NOV");
        xAxis.add("DEC");
        return xAxis;
    }

    private ArrayList<String> getMonthXAxisValues(){
        ArrayList<String> xAxis = new ArrayList<>();
        if((selectedMonth % 2 == 1) && (selectedMonth != 2)) {
            for(int i = 1; i <= 31; i++){
                xAxis.add(String.valueOf(i));
            }
        } else if ((selectedMonth % 2 == 0) && (selectedMonth != 2)) {
            for(int i = 1; i <= 30; i++){
                xAxis.add(String.valueOf(i));
            }
        } else if (selectedMonth == 2) {
            if (selectedYear % 4 == 0){
                for(int i = 1; i <= 29; i++){
                    xAxis.add(String.valueOf(i));
                }
            } else {
                for (int i = 1; i <= 28; i++) {
                    xAxis.add(String.valueOf(i));
                }
            }
        }

        return xAxis;
    }

    private ArrayList<IBarDataSet> getMonthDataSet(int month, int year, List<Bill> bills){
        ArrayList<IBarDataSet> datas = new ArrayList<IBarDataSet>();
        ArrayList<Bill> monthBills = new ArrayList<Bill>();

        for(Bill bill : bills){
            calendar.setTime(new Date(bill.getDate()));
            if(year == calendar.get(Calendar.YEAR) && (month - 1) == calendar.get(Calendar.MONTH)){
                monthBills.add(bill);
            }
        }

        setupRecyclerView((RecyclerView) recyclerView, monthBills);

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        if((selectedMonth % 2 == 1) && (selectedMonth != 2)) {
            for(int i = 1; i <= 31; i++){
                double cena = 0.0;
                for (Bill bill : monthBills){
                    calendar.setTime(new Date(bill.getDate()));
                    if (i == calendar.get(Calendar.DAY_OF_MONTH)){
                        cena += bill.getPrice().doubleValue();
                    }
                }
                valueSet1.add(new BarEntry((float) cena, i));
            }
        } else if ((selectedMonth % 2 == 0) && (selectedMonth != 2)) {
            for(int i = 1; i <= 30; i++){
                double cena = 0.0;
                for (Bill bill : monthBills){
                    calendar.setTime(new Date(bill.getDate()));
                    if (i == calendar.get(Calendar.DAY_OF_MONTH)){
                        cena += bill.getPrice().doubleValue();
                    }
                }
                valueSet1.add(new BarEntry((float) cena, i));
            }
        } else if (selectedMonth == 2) {
            if (selectedYear % 4 == 0){
                for(int i = 1; i <= 29; i++){
                    double cena = 0.0;
                    for (Bill bill : monthBills){
                        calendar.setTime(new Date(bill.getDate()));
                        if (i == calendar.get(Calendar.DAY_OF_MONTH)){
                            cena += bill.getPrice().doubleValue();
                        }
                    }
                    valueSet1.add(new BarEntry((float) cena, i));
                }
            } else {
                for (int i = 1; i <= 28; i++) {
                    double cena = 0.0;
                    for (Bill bill : monthBills){
                        calendar.setTime(new Date(bill.getDate()));
                        if (i == calendar.get(Calendar.DAY_OF_MONTH)){
                            cena += bill.getPrice().doubleValue();
                        }
                    }
                    valueSet1.add(new BarEntry((float) cena, i));
                }
            }
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, " ");
        barDataSet1.setColor(Color.rgb(255,255,0));
        barDataSet1.setValueTextColor(Color.WHITE);
        barDataSet1.setHighLightColor(Color.WHITE);
        barDataSet1.setValueTextColor(Color.WHITE);
        barDataSet1.setBarSpacePercent(30f);
        // dataSets = new ArrayList<>();
        // dataSets.add(barDataSet1);
        datas.add(barDataSet1);
        return datas;

    }

    private void all_bills() {
        toolbar.setTitle("Lista računa");
        dayPicker.setVisibility(View.GONE);
        datePicker.setVisibility(View.GONE);
        newBill.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= RecyclerView.LayoutParams.WRAP_CONTENT;
        recyclerView.setLayoutParams(params);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDate(int year, int month, int day) {
        dayPicker.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void showDate(int month, int year) {
        dayPicker.setText(new StringBuilder().append(month).append("/").append(year));
    }

    public void showDate(int year) {
        dayPicker.setText(new StringBuilder().append(year));
    }

    public void setSelectedYear(int year1){
        selectedYear = year1;
    }

    public void  setSelectedMonth(int month1){
        selectedMonth = month1;
    }

    @Override
    public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
        if(response.isSuccessful()) {
            bills = response.body();
            setupRecyclerView((RecyclerView) recyclerView, response.body());
        } else {
            Log.e("sadas", response.message());
        }

    }

    @Override
    public void onFailure(Call<List<Bill>> call, Throwable t) {
        Log.e("sadas", t.getMessage());
    }
}
