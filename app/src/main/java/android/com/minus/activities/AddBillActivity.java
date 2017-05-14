package android.com.minus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.BillItemsAdapter;
import fragments.DatePickerFragment;
import model.Item;

public class AddBillActivity extends AppCompatActivity {

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        dateView = (TextView) findViewById(R.id.odabraniDatum);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //Showing currnetly date when open window first time
        showDate(year, month+1, day);

        Button addItem = (Button) findViewById(R.id.dodajArtikal);
        Button addBill = (Button) findViewById(R.id.dodajRacun);
        ImageButton locationButton = (ImageButton)findViewById(R.id.locationButton);
        ImageButton datePicker = (ImageButton) findViewById(R.id.postaviDatum);

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
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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
}
