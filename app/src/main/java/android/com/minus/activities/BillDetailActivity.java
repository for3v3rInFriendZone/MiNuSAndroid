package android.com.minus.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.com.minus.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import fragments.BillDetailFragment;

public class BillDetailActivity extends AppCompatActivity {

    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
        }
        actionBar.setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(BillDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(BillDetailFragment.ARG_ITEM_ID));
            BillDetailFragment fragment = new BillDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_button:
                alertDialogOpen(BillDetailActivity.this);
                break;
        }
        return true;
    }

    private void alertDialogOpen(Context applicationContext) {
        dialog = new AlertDialog.Builder(applicationContext);
        dialog.setMessage("Da li ste sigurni da želite da obrišete ovaj račun?");
        dialog.setCancelable(true);

        dialog.setPositiveButton(
                "Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NavUtils.navigateUpTo(BillDetailActivity.this, new Intent(BillDetailActivity.this, MainActivity.class));
                    }
                });

        dialog.setNegativeButton(
                "Ne",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = dialog.create();
        alert11.show();
    }

}
