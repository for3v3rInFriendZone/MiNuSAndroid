package android.com.minus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.io.Serializable;
import java.util.List;

import model.Item;

public class AddItemActivity extends AppCompatActivity {

    private NumberPicker np;
    private Button addItem;
    private Button cancel;
    private EditText itemName;
    private EditText itemPrice;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //number picker for selecting quantity of item min value is 1 and max value is 999
        np = (NumberPicker)findViewById(R.id.kolicina_broj);
        np.setMinValue(1);
        np.setMaxValue(999);
        np.setWrapSelectorWheel(true);

        addItem = (Button) findViewById(R.id.dodajButton);
        cancel = (Button) findViewById(R.id.ponistiButton);
        itemName = (EditText) findViewById(R.id.naziv_artikla);
        itemPrice = (EditText) findViewById(R.id.cena_artikla);
        items = (List<Item>) getIntent().getSerializableExtra("listOfItems");

        //add item on bill button
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemAndBackToBill();
            }
        });

        //cancel activity button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void addItemAndBackToBill() {
        Intent i = new Intent(this, AddBillActivity.class);

        items.add(new Item(itemName.getText().toString(), np.getValue(), Double.parseDouble(itemPrice.getText().toString())));
        i.putExtra("listOfItems", (Serializable) items);
        startActivity(i);
    }

}
