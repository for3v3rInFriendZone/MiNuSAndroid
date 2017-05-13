package android.com.minus.activities;

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
import android.widget.Toast;

import DAO.BillDAO;
import DAO.UserDAO;
import fragments.BillDetailFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.RetrofitBuilder;

public class BillDetailActivity extends AppCompatActivity {

    private AlertDialog.Builder dialog;
    private BillDAO billDao;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        billDao = retrofit.create(BillDAO.class);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(BillDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(BillDetailFragment.ARG_ITEM_ID, 1L));
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
            case android.R.id.home:
                NavUtils.navigateUpTo(BillDetailActivity.this, new Intent(BillDetailActivity.this, MainActivity.class));
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
                        billDao.delete(getIntent().getLongExtra(BillDetailFragment.ARG_ITEM_ID, 1L)).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Brisanje racuna je uspešno izvršeno.", Toast.LENGTH_SHORT).show();
                                    NavUtils.navigateUpTo(BillDetailActivity.this, new Intent(BillDetailActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Brisanje nije uspelo", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Brisanje nije uspelo", Toast.LENGTH_SHORT).show();
                            }
                        });
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
