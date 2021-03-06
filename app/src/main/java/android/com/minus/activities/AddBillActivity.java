package android.com.minus.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DAO.BillDAO;
import DAO.BudgetDAO;
import DAO.UserDAO;
import adapter.BillItemsAdapter;
import fragments.DatePickerFragment;
import model.Bill;
import model.Budget;
import model.Item;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.RetrofitBuilder;
import util.SharedSession;

public class AddBillActivity extends AppCompatActivity {

    private Calendar calendar;
    private TextView dateView, sumPrice, novi_racun, datum, artikal, kolicina, cena, ukupno, ukupnaCena, valuta;
    private int year, month, day, budgetYear, budgetMonth, budgetDay;
    private ArrayList<Item> items;
    private Button addItem, addBill, returnBill;
    private ImageButton locationButton, datePicker;
    private BillDAO billDao;
    private Retrofit retrofit;
    private EditText billName, locationName, issuerBill;
    private User logedUser;
    private BudgetDAO budgetDAO;
    private PlacePicker.IntentBuilder pl;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        activity = this;

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        billDao = retrofit.create(BillDAO.class);
        budgetDAO = retrofit.create(BudgetDAO.class);

        logedUser = SharedSession.getSavedObjectFromPreference(getApplicationContext(), "userSession", "user", User.class);

        novi_racun = (TextView) findViewById(R.id.novi_racun);
        artikal = (TextView) findViewById(R.id.textView9);
        kolicina = (TextView) findViewById(R.id.textView1);
        cena = (TextView) findViewById(R.id.textView2);
        datum = (TextView) findViewById(R.id.datum);
        ukupno = (TextView) findViewById(R.id.ukupno);
        ukupnaCena = (TextView) findViewById(R.id.ukupnaCena);
        valuta = (TextView) findViewById(R.id.valuta);
        dateView = (TextView) findViewById(R.id.odabraniDatum);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        sumPrice = (TextView) findViewById(R.id.ukupnaCena);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        budgetDay = day;
        budgetMonth = month;
        budgetYear = year;

        pl = new PlacePicker.IntentBuilder();

        //Showing current date when window is opened first time
        showDate(year, month+1, day);

        addItem = (Button) findViewById(R.id.dodajArtikal);
        addBill = (Button) findViewById(R.id.dodajRacun);
        returnBill = (Button) findViewById(R.id.ponistiRacun);
        locationButton = (ImageButton)findViewById(R.id.locationButton);
        datePicker = (ImageButton) findViewById(R.id.postaviDatum);
        billName = (EditText) findViewById(R.id.naziv_racun);
        locationName = (EditText) findViewById(R.id.mesto_kupovine);
        issuerBill = (EditText) findViewById(R.id.issuer_bill);

        //add new item on bill button
        addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newItemView(v);
            }
        });

        //add new bill button
        addBill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    saveBill();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        //location button
        locationButton.setImageResource(R.mipmap.ic_map_marker);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivityForResult(pl.build(activity), 1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        //select date button
        datePicker.setImageResource(R.mipmap.ic_calendar_range);
        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        returnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpTo(AddBillActivity.this, new Intent(AddBillActivity.this, MainActivity.class));
            }
        });

        //temporary data for bill. It saves only when save is clcked.
        items = (ArrayList<Item>) getIntent().getSerializableExtra("listOfItems");
        if(items == null) {
            items = new ArrayList<Item>();
        }
        ListView listView = (ListView) findViewById(R.id.listViewItems);
        BillItemsAdapter billItemsAdapter = new BillItemsAdapter(items, this);
        listView.setAdapter(billItemsAdapter);

        //calculate sum price of bill
        Double price = 0D;
        for(Item i : items) {
            price += i.getPrice() * i.getQuantity();
            sumPrice.setText(price.toString());
        }

        setFont(logedUser.getFont());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                locationName.setText(place.getName());
            }
        }
    }

    /**
     * Function open calendar.
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Function show selected date on screen in dd/MM//yyyy format.
     * @param year
     * @param month
     * @param day
     */
    public void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append(".")
                .append(month).append(".").append(year));
    }

    /**
     * Function open new window for adding new item on bill.
     * @param v
     */
    public void newItemView(View v){
        Intent i = new Intent(this, AddItemActivity.class);
        i.putExtra("listOfItems", (Serializable) items);
        startActivity(i);
    }

    private void setFont(String nameOfFont) {

        if(nameOfFont.equals("serif")) {
            novi_racun.setTypeface(Typeface.SERIF);
            billName.setTypeface(Typeface.SERIF);
            locationName.setTypeface(Typeface.SERIF);
            issuerBill.setTypeface(Typeface.SERIF);
            datum.setTypeface(Typeface.SERIF);
            dateView.setTypeface(Typeface.SERIF);
            addItem.setTypeface(Typeface.SERIF);
            artikal.setTypeface(Typeface.SERIF);
            kolicina.setTypeface(Typeface.SERIF);
            cena.setTypeface(Typeface.SERIF);
            ukupno.setTypeface(Typeface.SERIF);
            ukupnaCena.setTypeface(Typeface.SERIF);
            valuta.setTypeface(Typeface.SERIF);
            addBill.setTypeface(Typeface.SERIF);
            returnBill.setTypeface(Typeface.SERIF);
        } else if(nameOfFont.equals("sans")) {
            novi_racun.setTypeface(Typeface.SANS_SERIF);
            billName.setTypeface(Typeface.SANS_SERIF);
            locationName.setTypeface(Typeface.SANS_SERIF);
            issuerBill.setTypeface(Typeface.SANS_SERIF);
            datum.setTypeface(Typeface.SANS_SERIF);
            dateView.setTypeface(Typeface.SANS_SERIF);
            addItem.setTypeface(Typeface.SANS_SERIF);
            artikal.setTypeface(Typeface.SANS_SERIF);
            kolicina.setTypeface(Typeface.SANS_SERIF);
            cena.setTypeface(Typeface.SANS_SERIF);
            ukupno.setTypeface(Typeface.SANS_SERIF);
            ukupnaCena.setTypeface(Typeface.SANS_SERIF);
            valuta.setTypeface(Typeface.SANS_SERIF);
            addBill.setTypeface(Typeface.SANS_SERIF);
            returnBill.setTypeface(Typeface.SANS_SERIF);
        } else if(nameOfFont.equals("monospace")) {
            novi_racun.setTypeface(Typeface.MONOSPACE);
            billName.setTypeface(Typeface.MONOSPACE);
            locationName.setTypeface(Typeface.MONOSPACE);
            issuerBill.setTypeface(Typeface.MONOSPACE);
            datum.setTypeface(Typeface.MONOSPACE);
            dateView.setTypeface(Typeface.MONOSPACE);
            addItem.setTypeface(Typeface.MONOSPACE);
            artikal.setTypeface(Typeface.MONOSPACE);
            kolicina.setTypeface(Typeface.MONOSPACE);
            cena.setTypeface(Typeface.MONOSPACE);
            ukupno.setTypeface(Typeface.MONOSPACE);
            ukupnaCena.setTypeface(Typeface.MONOSPACE);
            valuta.setTypeface(Typeface.MONOSPACE);
            addBill.setTypeface(Typeface.MONOSPACE);
            returnBill.setTypeface(Typeface.MONOSPACE);
        } else {
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + nameOfFont + ".ttf");
            novi_racun.setTypeface(font);
            billName.setTypeface(font);
            locationName.setTypeface(font);
            issuerBill.setTypeface(font);
            datum.setTypeface(font);
            dateView.setTypeface(font);
            addItem.setTypeface(font);
            artikal.setTypeface(font);
            kolicina.setTypeface(font);
            cena.setTypeface(font);
            ukupno.setTypeface(font);
            ukupnaCena.setTypeface(font);
            valuta.setTypeface(font);
            addBill.setTypeface(font);
            returnBill.setTypeface(font);
        }
    }

    public void saveBill() throws ParseException {
        if(isValid()) {
            final Bill b = new Bill(billName.getText().toString(),
                    locationName.getText().toString(), issuerBill.getText().toString(),
                    new SimpleDateFormat("dd.MM.yyyy").parse(dateView.getText().toString()).getTime(),
                    Double.parseDouble(sumPrice.getText().toString()), items, logedUser);


            billDao.save(b).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()) {
                                budgetDAO.findUserBudgets(logedUser.getId()).enqueue(new Callback<List<Budget>>() {
                                    @Override
                                    public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                                        Calendar dateFrom = Calendar.getInstance();
                                        Calendar dateTo = Calendar.getInstance();

                                        for(Budget budget : response.body()){
                                            dateFrom.setTime(new Date(budget.getDateFrom()));
                                            dateTo.setTime(new Date(budget.getDateTo()));
                                            //dnevni budzet
                                            if(budget.getDateFrom().equals(budget.getDateTo())){
                                                //poklapanje datuma budzeta i datuma racuna
                                                if((dateFrom.get(Calendar.DAY_OF_MONTH) == budgetDay) &&
                                                        (dateFrom.get(Calendar.MONTH) == budgetMonth) &&
                                                        (dateFrom.get(Calendar.YEAR) == budgetYear)){
                                                    double budzet = budget.getCurrentValue();
                                                    budget.setCurrentValue(budzet - b.getPrice());
                                                    budgetDAO.update(budget).enqueue(new Callback<Budget>() {
                                                        @Override
                                                        public void onResponse(Call<Budget> call, Response<Budget> response) {
                                                            Toast.makeText(getApplicationContext(), "Vaš dnevni budžet se promenio.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Budget> call, Throwable t) {

                                                        }
                                                    });
                                                    if(logedUser.isNotification()) {
                                                        if ((budget.getStartValue() / 0.2) > (budzet - b.getPrice())) {
                                                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                                                            mBuilder.setSmallIcon(R.drawable.m);
                                                            mBuilder.setContentTitle("Oprez!!!");
                                                            mBuilder.setContentText("Ostalo vam je još " + String.valueOf(budzet - b.getPrice()) + " od predviđenog dnevnog budžeta.");
                                                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                            mNotificationManager.notify(0, mBuilder.build());
                                                        }
                                                    }
                                                }
                                            }

                                            if(!dateFrom.getTime().equals(dateTo.getTime())){
                                                if((dateFrom.get(Calendar.MONTH) == budgetMonth) &&
                                                        (dateFrom.get(Calendar.YEAR) == budgetYear)){
                                                    double budzet = budget.getCurrentValue();
                                                    budget.setCurrentValue(budzet - b.getPrice());
                                                    budgetDAO.update(budget).enqueue(new Callback<Budget>() {
                                                        @Override
                                                        public void onResponse(Call<Budget> call, Response<Budget> response) {
                                                            Toast.makeText(getApplicationContext(), "Vaš mesečni budžet se promenio.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Budget> call, Throwable t) {

                                                        }
                                                    });
                                                    if(logedUser.isNotification()) {
                                                        if ((budget.getStartValue() / 0.2) > (budzet - b.getPrice())) {
                                                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                                                            mBuilder.setSmallIcon(R.drawable.m);
                                                            mBuilder.setContentTitle("Oprez!!!");
                                                            mBuilder.setContentText("Ostalo vam je još " + String.valueOf(budzet - b.getPrice()) + " od predviđenog mesečnog budžeta.");
                                                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                            mNotificationManager.notify(1, mBuilder.build());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Budget>> call, Throwable t) {

                                    }
                                });
                                NavUtils.navigateUpTo(AddBillActivity.this, new Intent(AddBillActivity.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
        }
    }

    private boolean isValid() {
        if(billName.getText().toString().length() == 0) {
            billName.setError("Morate uneti ime računa.");
            return false;
        } else if(locationName.getText().toString().length() == 0) {
            locationName.setError("Morate uneti lokaciju.");
            return false;
        }

        return true;
    }

    public void setSelectedDate(int day, int month, int year){
        budgetDay = day;
        budgetMonth = month;
        budgetYear = year;
    }
}
