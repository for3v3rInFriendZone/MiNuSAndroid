package fragments;

import android.app.Activity;
import android.com.minus.R;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.Bill;


public class BillDetailFragment extends Fragment {

    private Bill bill;

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

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(bill.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        if (bill != null) {
            ((TextView) rootView.findViewById(R.id.billIssuer)).setText(bill.getIssuer());
           // ((TextView) rootView.findViewById(R.id.billDate)).setText(bill.getDate());
          //  ((TextView) rootView.findViewById(R.id.textView67)).setText(bill.getPrice().toString());
          //  ((TextView) rootView.findViewById(R.id.billPlace)).setText(bill.getLocation());
        }

        return rootView;
    }


}
