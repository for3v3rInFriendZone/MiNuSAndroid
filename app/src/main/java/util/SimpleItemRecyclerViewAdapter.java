package util;

import android.com.minus.R;
import android.com.minus.activities.BillDetailActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fragments.BillDetailFragment;
import model.Bill;

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Bill> mValues;

    public SimpleItemRecyclerViewAdapter(List<Bill> items) {
        this.mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.getName().setText(mValues.get(position).getName());
        holder.getDate().setText(mValues.get(position).getDate());
        holder.getIssuer().setText(mValues.get(position).getIssuer());

        holder.getmView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, BillDetailActivity.class);
                intent.putExtra(BillDetailFragment.ARG_ITEM_ID, mValues.get(position).getName());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
