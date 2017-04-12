package android.com.minus.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;
import android.widget.NumberPicker;

public class AddIdemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idem);

        NumberPicker np = (NumberPicker)findViewById(R.id.kolicina_broj);
        np.setMinValue(1);
        np.setMaxValue(999);

        np.setWrapSelectorWheel(true);

        Button addItem = (Button) findViewById(R.id.dodajButton);
        Button cancel = (Button) findViewById(R.id.ponistiButton);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
