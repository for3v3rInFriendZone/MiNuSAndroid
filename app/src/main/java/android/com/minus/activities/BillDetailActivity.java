package android.com.minus.activities;


import android.com.minus.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import fragments.BillDetailFragment;
import model.Bill;

public class BillDetailActivity extends FragmentActivity {

    BillDetailFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        Bill bill = (Bill) getIntent().getSerializableExtra("item");
        if (savedInstanceState == null) {
            fragmentItemDetail = BillDetailFragment.newInstance(bill);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItemDetail);
            ft.commit();
        }
    }
}
