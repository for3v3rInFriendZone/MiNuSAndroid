package android.com.minus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DAO.BillDAO;
import DAO.UserDAO;
import adapter.BillItemsAdapter;
import fragments.DatePickerFragment;
import model.Bill;
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
    private TextView dateView, sumPrice;
    private int year, month, day;
    private ArrayList<Item> items;
    private Button addItem, addBill;
    private ImageButton locationButton, datePicker;
    private BillDAO billDao;
    private Retrofit retrofit;
    private EditText billName, locationName, issuerBill;
    private User logedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        billDao = retrofit.create(BillDAO.class);

        logedUser = SharedSession.getSavedObjectFromPreference(getApplicationContext(), "userSession", "user", User.class);

        dateView = (TextView) findViewById(R.id.odabraniDatum);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        sumPrice = (TextView) findViewById(R.id.ukupnaCena);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //Showing currnetly date when open window first time
        showDate(year, month+1, day);

        addItem = (Button) findViewById(R.id.dodajArtikal);
        addBill = (Button) findViewById(R.id.dodajRacun);
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

        //select date button
        datePicker.setImageResource(R.mipmap.ic_calendar_range);
        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
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

    public void saveBill() throws ParseException {

        Bill b = new Bill(billName.getText().toString(),
                locationName.getText().toString(), issuerBill.getText().toString(),
                new SimpleDateFormat("dd.MM.yyyy").parse(dateView.getText().toString()).getTime(),
                Double.parseDouble(sumPrice.getText().toString()), items, logedUser);
        billDao.save(b)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            NavUtils.navigateUpTo(AddBillActivity.this, new Intent(AddBillActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
