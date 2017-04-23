package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.com.minus.R;
import android.com.minus.activities.AddBillActivity;
import android.com.minus.activities.BillDetailActivity;
import android.com.minus.activities.MainActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adapter.BillItemsAdapter;
import model.Bill;
import model.Item;


public class BillDetailFragment extends Fragment {

    private Bill bill;
    private TextView titleOfToolbar;
    private AlertDialog.Builder dialog;
    private Activity activity;

    public static final String ARG_ITEM_ID = "item_id";

    public BillDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bill = (Bill) getArguments().getSerializable("item");

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            for(int i=0; i<Bill.getItems().size(); i++) {
                if(Bill.getItems().get(i).getName().equals(getArguments().getString(ARG_ITEM_ID))) {
                    bill = Bill.getItems().get(i);
                    break;
                }
            }

            activity = this.getActivity();
            activity.setTitle(bill.getName());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        if (bill != null) {
            ((TextView) rootView.findViewById(R.id.billIssuer)).setText(bill.getIssuer());
            ((TextView) rootView.findViewById(R.id.billLocation)).setText(bill.getLocation());
            ((TextView) rootView.findViewById(R.id.billSumPrice)).setText(bill.getPrice().toString());
            ((TextView) rootView.findViewById(R.id.billDate)).setText(bill.getDate());

            ArrayList<Item> items = new ArrayList<Item>();
            items = Item.getItems();
            ListView listView = (ListView) rootView.findViewById(R.id.listViewItemsDetail);
            BillItemsAdapter billItemsAdapter = new BillItemsAdapter(items, activity);
            listView.setAdapter(billItemsAdapter);
        }

        return rootView;
    }

}
