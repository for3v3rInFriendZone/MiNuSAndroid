package util;

import android.com.minus.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView name, quantity, price;
    private View itemView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.name = (TextView) itemView.findViewById(R.id.itemName);
        this.quantity = (TextView) itemView.findViewById(R.id.itemQuantity);
        this.price = (TextView) itemView.findViewById(R.id.itemPrice);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getQuantity() {
        return quantity;
    }

    public void setQuantity(TextView quantity) {
        this.quantity = quantity;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }
}
