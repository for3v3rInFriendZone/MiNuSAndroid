package adapter;

import android.app.Activity;
import android.com.minus.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Item;

/**
 * Created by Dejan on 4/18/2017.
 */

public class BillItemsAdapter extends BaseAdapter{

    Activity activity;
    public List<Item> items;

    public BillItemsAdapter(List<Item> items, Activity activity) {
        super();
        this.items = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView name;
        TextView quantity;
        TextView price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater;
        inflater = activity.getLayoutInflater();

        if(convertView == null){
            convertView = inflater.inflate(R.layout.billitems_row, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.itemName);
            holder.quantity = (TextView) convertView.findViewById(R.id.itemQuantity);
            holder.price = (TextView) convertView.findViewById(R.id.itemPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = items.get(position);
        holder.name.setText(item.getName().toString());
        holder.quantity.setText(new Integer(item.getQuantity()).toString());
        holder.price.setText(new Double(item.getPrice()).toString());

        return convertView;
    }
}
