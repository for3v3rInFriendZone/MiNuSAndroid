package android.com.minus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

import model.Item;
import model.User;
import util.SharedSession;

public class AddItemActivity extends AppCompatActivity {

    private NumberPicker np;
    private Button addItem, cancel;
    private EditText itemName, itemPrice;
    private List<Item> items;
    private TextView novi_artikal, kolicina;
    private User logedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //number picker for selecting quantity of item min value is 1 and max value is 999
        np = (NumberPicker)findViewById(R.id.kolicina_broj);
        np.setMinValue(1);
        np.setMaxValue(999);
        np.setWrapSelectorWheel(true);

        logedUser = SharedSession.getSavedObjectFromPreference(getApplicationContext(), "userSession", "user", User.class);

        novi_artikal = (TextView) findViewById(R.id.novi_artikal);
        kolicina = (TextView) findViewById(R.id.kolicina);
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

        setFont(logedUser.getFont());
    }

    public void addItemAndBackToBill() {
        if(isValid()) {
            Intent i = new Intent(this, AddBillActivity.class);

            items.add(new Item(itemName.getText().toString(), np.getValue(), Double.parseDouble(itemPrice.getText().toString())));
            i.putExtra("listOfItems", (Serializable) items);
            startActivity(i);
        }
    }

    private void setFont(String nameOfFont) {

        if(nameOfFont.equals("serif")) {
            novi_artikal.setTypeface(Typeface.SERIF);
            kolicina.setTypeface(Typeface.SERIF);
            addItem.setTypeface(Typeface.SERIF);
            cancel.setTypeface(Typeface.SERIF);
            itemName.setTypeface(Typeface.SERIF);
            itemPrice.setTypeface(Typeface.SERIF);
        } else if(nameOfFont.equals("sans")) {
            novi_artikal.setTypeface(Typeface.SANS_SERIF);
            kolicina.setTypeface(Typeface.SANS_SERIF);
            addItem.setTypeface(Typeface.SANS_SERIF);
            cancel.setTypeface(Typeface.SANS_SERIF);
            itemName.setTypeface(Typeface.SANS_SERIF);
            itemPrice.setTypeface(Typeface.SANS_SERIF);
        } else if(nameOfFont.equals("monospace")) {
            novi_artikal.setTypeface(Typeface.MONOSPACE);
            kolicina.setTypeface(Typeface.MONOSPACE);
            addItem.setTypeface(Typeface.MONOSPACE);
            cancel.setTypeface(Typeface.MONOSPACE);
            itemName.setTypeface(Typeface.MONOSPACE);
            itemPrice.setTypeface(Typeface.MONOSPACE);
        } else {
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + nameOfFont + ".ttf");
            novi_artikal.setTypeface(font);
            kolicina.setTypeface(font);
            addItem.setTypeface(font);
            cancel.setTypeface(font);
            itemName.setTypeface(font);
            itemPrice.setTypeface(font);
        }
    }

    private boolean isValid() {
        if(itemName.getText().toString().length() == 0) {
            itemName.setError("Morate uneti naziv.");
            return false;
        } else if(itemPrice.getText().toString().length() == 0) {
            itemPrice.setError("Morate uneti cenu.");
            return false;
        }

        return true;
    }


}
