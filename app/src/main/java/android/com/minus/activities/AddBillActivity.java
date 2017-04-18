package android.com.minus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.com.minus.R;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import adapter.BillItemsAdapter;
import fragments.DatePickerFragment;
import model.Item;

public class AddBillActivity extends AppCompatActivity {

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        dateView = (TextView) findViewById(R.id.odabraniDatum);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDate(year, month+1, day);

        Button addItem = (Button) findViewById(R.id.dodajArtikal);
        Button addBill = (Button) findViewById(R.id.dodajRacun);
        Button datePicker = (Button) findViewById(R.id.postaviDatum);

        addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newItemView(v);
            }
        });

        addBill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        ArrayList<Item> items = new ArrayList<Item>();
        items = Item.getItems();
        ListView listView = (ListView) findViewById(R.id.listViewItems);
        BillItemsAdapter billItemsAdapter = new BillItemsAdapter(items,this);
        listView.setAdapter(billItemsAdapter);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void newItemView(View v){
        Intent i = new Intent(this,AddIdemActivity.class);
        startActivity(i);
    }
}
