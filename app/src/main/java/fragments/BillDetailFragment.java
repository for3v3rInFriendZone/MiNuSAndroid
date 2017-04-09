package fragments;

import android.com.minus.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.Bill;


public class BillDetailFragment extends Fragment {

    private Bill bill;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bill = (Bill) getArguments().getSerializable("item");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_detail,
                container, false);
        TextView billIssuer = (TextView) view.findViewById(R.id.billIssuer);
        TextView billDate = (TextView) view.findViewById(R.id.billDate);
        billIssuer.setText(bill.getIssuer());
        billDate.setText(bill.getDate());

        return view;
    }

    // ItemDetailFragment.newInstance(item)
    public static BillDetailFragment newInstance(Bill bill) {
        BillDetailFragment fragmentDemo = new BillDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", bill);
        fragmentDemo.setArguments(args);

        return fragmentDemo;
    }

}
