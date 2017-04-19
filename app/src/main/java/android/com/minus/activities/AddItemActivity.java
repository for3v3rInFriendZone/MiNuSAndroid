package android.com.minus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.NumberPicker;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //number picker for selecting quantity of item min value is 1 and max value is 999
        NumberPicker np = (NumberPicker)findViewById(R.id.kolicina_broj);
        np.setMinValue(1);
        np.setMaxValue(999);

        np.setWrapSelectorWheel(true);

        Button addItem = (Button) findViewById(R.id.dodajButton);
        Button cancel = (Button) findViewById(R.id.ponistiButton);

        //add item on bill button
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //cancel activity button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
