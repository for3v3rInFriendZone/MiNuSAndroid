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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import fragments.DatePickerFragment;
import model.Bill;
import util.MonthYearPickerDialog;
import util.SimpleDividerItemDecoration;
import util.SimpleItemRecyclerViewAdapter;
import util.YearPickerDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SearchView searchInput;
    private Toolbar toolbar;
    private final SimpleItemRecyclerViewAdapter adapter = new SimpleItemRecyclerViewAdapter(Bill.getItems());
    private TextView dayPicker;
    private Calendar calendar;
    private int year, month, day;
    private ImageButton datePicker;
    private FloatingActionButton newBill;
    private RecyclerView recyclerView;
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lista računa");
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dayPicker = (TextView) findViewById(R.id.day_picker_report);
        datePicker = (ImageButton) findViewById(R.id.day_date);
        datePicker.setImageResource(R.mipmap.ic_calendar_range);
        chart = (BarChart) findViewById(R.id.bar_chart);

        searchInput = (SearchView) findViewById(R.id.search_input);
        searchInput.setQueryHint("Pretraga računa...");

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Bill.getItems()));
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
            dayReport();
        } else if (id == R.id.month_report) {
            monthReport();

        } else if (id == R.id.year_report) {
            yearReport();

        }  else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

        } else if(id == R.id.all_bills) {
            all_bills();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void newBillView(View v){
        Intent i = new Intent(this, AddBillActivity.class);
        startActivity(i);
    }

    private void dayReport() {

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
        toolbar.setTitle("Mesečni pregled");
        newBill.setVisibility(View.GONE);

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= 400;
        recyclerView.setLayoutParams(params);


        BarData data = new BarData(getMonthDataSet());
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();

        showDate(month+1, year);

        dayPicker.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.VISIBLE);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });
    }

    private void yearReport() {
        toolbar.setTitle("Godišnji pregled");
        newBill.setVisibility(View.GONE);
        showDate(year);

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= 400;
        recyclerView.setLayoutParams(params);

        BarData data = new BarData(getYearDataSet());
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();

        dayPicker.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.VISIBLE);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearPickerDialog pd = new YearPickerDialog();
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
            }
        });
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

    private ArrayList<IBarDataSet> getMonthDataSet() {
        ArrayList<BarEntry> dataSets = new ArrayList<BarEntry>();

        for (int i = (int) 0; i < 30; i++) {
            float val = (float) (Math.random()*100);
            dataSets.add(new BarEntry(i, val));
        }

        BarDataSet barDataSet1 = new BarDataSet(dataSets, "Vrednosti cena");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        ArrayList<IBarDataSet> idataSets = new ArrayList<IBarDataSet>();
        idataSets.add(barDataSet1);

        return idataSets;
    }

    private ArrayList<IBarDataSet> getYearDataSet() {
        ArrayList<BarEntry> dataSets = new ArrayList<BarEntry>();

        for (int i = (int) 0; i < 12; i++) {
            float val = (float) (Math.random()*1000);
            dataSets.add(new BarEntry(i, val));
        }

        BarDataSet barDataSet1 = new BarDataSet(dataSets, "Vrednosti cena");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        ArrayList<IBarDataSet> idataSets = new ArrayList<IBarDataSet>();
        idataSets.add(barDataSet1);

        return idataSets;
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

}
