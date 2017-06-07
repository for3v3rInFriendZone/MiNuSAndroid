package adapter;


import android.app.Activity;
import android.com.minus.R;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import model.Item;
import util.ItemViewHolder;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private List<Item> mValues;
    private List<Item> mValuesCopy;
    private SharedPreferences shared_font;
    private Typeface font;

    public ItemRecyclerViewAdapter(List<Item> mValues, Activity activity) {
        this.mValues = mValues;
        this.mValuesCopy = new ArrayList<>(mValues);
        shared_font = activity.getApplicationContext().getSharedPreferences("font", 0);
        String app_font = shared_font.getString("app_font", "");

        if(app_font.equals("serif")) {
            font = Typeface.SERIF;
        } else if(app_font.equals("sans")) {
            font = Typeface.SANS_SERIF;
        } else if(app_font.equals("monospace")) {
            font = Typeface.MONOSPACE;
        } else {
            font = Typeface.createFromAsset(activity.getAssets(), "fonts/" + app_font + ".ttf");
        }

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

        holder.getName().setTypeface(font);
        holder.getPrice().setTypeface(font);
        holder.getQuantity().setTypeface(font);

        holder.getName().setText(mValues.get(position).getName());
        holder.getQuantity().setText(quantity.toString());
        holder.getPrice().setText(price.toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
