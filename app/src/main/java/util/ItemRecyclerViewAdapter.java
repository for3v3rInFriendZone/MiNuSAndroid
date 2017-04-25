package util;


import android.com.minus.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import model.Item;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private List<Item> mValues;
    private List<Item> mValuesCopy;

    public ItemRecyclerViewAdapter(List<Item> mValues) {
        this.mValues = mValues;
        this.mValuesCopy = new ArrayList<>(mValues);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billitems_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Double price = mValues.get(position).getPrice();
        Integer quantity = mValues.get(position).getQuantity();

        holder.getName().setText(mValues.get(position).getName());
        holder.getQuantity().setText(quantity.toString());
        holder.getPrice().setText(price.toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
