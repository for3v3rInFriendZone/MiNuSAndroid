package android.com.minus.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.com.minus.R;
import android.widget.Button;

public class CashControlActivity extends AppCompatActivity {

    private Button dodaj_budzet;
    private Button ponisti_budzet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_control);

        dodaj_budzet = (Button) findViewById(R.id.dodaj_budzet);
        ponisti_budzet = (Button) findViewById(R.id.ponistiButton);

        /*dodaj_budzet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        ponisti_budzet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });*/

    }

}
