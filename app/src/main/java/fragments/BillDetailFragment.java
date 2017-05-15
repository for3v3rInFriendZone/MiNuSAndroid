package fragments;

import android.app.Activity;
import android.com.minus.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import DAO.BillDAO;
import DAO.UserDAO;
import model.Bill;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import adapter.ItemRecyclerViewAdapter;
import util.RetrofitBuilder;
import util.SimpleDividerItemDecoration;


public class BillDetailFragment extends Fragment {

    private Bill bill;
    private Activity activity;
    private ItemRecyclerViewAdapter itemAdapter;
    private RecyclerView itemRecycler;
    private Retrofit retrofit;
    private BillDAO billDao;

    public static final String ARG_ITEM_ID = "item_id";

    public BillDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        billDao = retrofit.create(BillDAO.class);
        activity = this.getActivity();

       /* bill = (Bill) getArguments().getSerializable("item");

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

        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_bill_detail, container, false);
        itemRecycler = (RecyclerView) rootView.findViewById(R.id.listViewItemsDetail);
        itemRecycler.addItemDecoration(new SimpleDividerItemDecoration(activity));
        assert itemRecycler != null;


        billDao.findOne(getArguments().getLong(ARG_ITEM_ID)).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if(response.isSuccessful()){
                    bill = response.body();
                    activity.setTitle(bill.getName());
                    itemAdapter = new ItemRecyclerViewAdapter(bill.getItems());
                    setupRecyclerView((RecyclerView) itemRecycler, itemAdapter);

                    ((TextView) rootView.findViewById(R.id.billIssuer)).setText(bill.getIssuer());
                    ((TextView) rootView.findViewById(R.id.billLocation)).setText(bill.getLocation());
                    ((TextView) rootView.findViewById(R.id.billSumPrice)).setText(bill.getPrice().toString());
                    ((TextView) rootView.findViewById(R.id.billDate)).setText(new SimpleDateFormat("dd.MMM.yyyy").format(new Date(bill.getDate())));
                } else {

                }

            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {

            }
        });

        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ItemRecyclerViewAdapter adapter) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
