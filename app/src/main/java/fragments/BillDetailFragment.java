package fragments;

import android.app.Activity;
import android.com.minus.R;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
    private TextView billIssuer, billLocation, billSumPrice, billDate, artikal, kolicina, cena, ukupna_cena, datum_kupovine;
    private SharedPreferences shared_font;

    public static final String ARG_ITEM_ID = "item_id";

    public BillDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        billDao = retrofit.create(BillDAO.class);
        activity = this.getActivity();
        shared_font = activity.getApplicationContext().getSharedPreferences("font", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_bill_detail, container, false);
        itemRecycler = (RecyclerView) rootView.findViewById(R.id.listViewItemsDetail);
        itemRecycler.addItemDecoration(new SimpleDividerItemDecoration(activity));
        assert itemRecycler != null;
        billIssuer = (TextView) rootView.findViewById(R.id.billIssuer);
        billLocation = (TextView) rootView.findViewById(R.id.billLocation);
        billSumPrice = (TextView) rootView.findViewById(R.id.billSumPrice);
        billDate = (TextView) rootView.findViewById(R.id.billDate);
        artikal = (TextView) rootView.findViewById(R.id.textView9);
        kolicina = (TextView) rootView.findViewById(R.id.textView1);
        cena = (TextView) rootView.findViewById(R.id.textView2);
        ukupna_cena = (TextView) rootView.findViewById(R.id.ukupna_cena_bill);
        datum_kupovine = (TextView) rootView.findViewById(R.id.datum_kupovine);

        String appFont = shared_font.getString("app_font", "");

        if(!appFont.equals("")) {
            setFont(appFont);
        }

        billDao.findOne(getArguments().getLong(ARG_ITEM_ID)).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if(response.isSuccessful()){
                    bill = response.body();
                    activity.setTitle(bill.getName());
                    itemAdapter = new ItemRecyclerViewAdapter(bill.getItems(), activity);
                    setupRecyclerView((RecyclerView) itemRecycler, itemAdapter);

                     billIssuer.setText(bill.getIssuer());
                     billLocation.setText(bill.getLocation());
                     billSumPrice.setText(bill.getPrice().toString());
                     billDate.setText(new SimpleDateFormat("dd.MMM.yyyy").format(new Date(bill.getDate())));

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

    private void setFont(String nameOfFont) {

        if(nameOfFont.equals("serif")) {
            billIssuer.setTypeface(Typeface.SERIF);
            billLocation.setTypeface(Typeface.SERIF);
            billSumPrice.setTypeface(Typeface.SERIF);
            billDate.setTypeface(Typeface.SERIF);
            artikal.setTypeface(Typeface.SERIF);
            kolicina.setTypeface(Typeface.SERIF);
            cena.setTypeface(Typeface.SERIF);
            ukupna_cena.setTypeface(Typeface.SERIF);
            datum_kupovine.setTypeface(Typeface.SERIF);
        } else if(nameOfFont.equals("sans")) {
            billIssuer.setTypeface(Typeface.SANS_SERIF);
            billLocation.setTypeface(Typeface.SANS_SERIF);
            billSumPrice.setTypeface(Typeface.SANS_SERIF);
            billDate.setTypeface(Typeface.SANS_SERIF);
            artikal.setTypeface(Typeface.SANS_SERIF);
            kolicina.setTypeface(Typeface.SANS_SERIF);
            cena.setTypeface(Typeface.SANS_SERIF);
            ukupna_cena.setTypeface(Typeface.SANS_SERIF);
            datum_kupovine.setTypeface(Typeface.SANS_SERIF);
        } else if(nameOfFont.equals("monospace")) {
            billIssuer.setTypeface(Typeface.MONOSPACE);
            billLocation.setTypeface(Typeface.MONOSPACE);
            billSumPrice.setTypeface(Typeface.MONOSPACE);
            billDate.setTypeface(Typeface.MONOSPACE);
            artikal.setTypeface(Typeface.MONOSPACE);
            kolicina.setTypeface(Typeface.MONOSPACE);
            cena.setTypeface(Typeface.MONOSPACE);
            ukupna_cena.setTypeface(Typeface.MONOSPACE);
            datum_kupovine.setTypeface(Typeface.MONOSPACE);
        } else {
            Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/" + nameOfFont + ".ttf");
            billIssuer.setTypeface(font);
            billLocation.setTypeface(font);
            billSumPrice.setTypeface(font);
            billDate.setTypeface(font);
            artikal.setTypeface(font);
            kolicina.setTypeface(font);
            cena.setTypeface(font);
            ukupna_cena.setTypeface(font);
            datum_kupovine.setTypeface(font);
        }
    }

}