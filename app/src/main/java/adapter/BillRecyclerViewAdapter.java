package util;

import android.com.minus.R;
import android.com.minus.activities.BillDetailActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fragments.BillDetailFragment;
import model.Bill;
import util.BillViewHolder;

public class BillRecyclerViewAdapter extends RecyclerView.Adapter<BillViewHolder> {

    private List<Bill> mValues;
    private List<Bill> mValuesCopy;

    public BillRecyclerViewAdapter(List<Bill> items) {
        this.mValues = items;
        this.mValuesCopy = new ArrayList<Bill>(mValues);
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_content, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BillViewHolder holder, final int position) {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        holder.getName().setText(mValues.get(position).getName());
        holder.getDate().setText(new SimpleDateFormat("dd.MMM.yyyy").format(new Date(mValues.get(position).getDate())));
        holder.getIssuer().setText(mValues.get(position).getIssuer());

        holder.getmView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, BillDetailActivity.class);
                intent.putExtra(BillDetailFragment.ARG_ITEM_ID, mValues.get(position).getId());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void filter(String text) {

        mValues.clear();
        if(text.isEmpty()){
            mValues.addAll(mValuesCopy);
        } else{
            text = text.toLowerCase();
            for(Bill item: mValuesCopy){
                if(item.getName().toLowerCase().contains(text) || item.getIssuer().toLowerCase().contains(text)){
                    mValues.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }


}