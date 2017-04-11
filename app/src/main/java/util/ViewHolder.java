package util;

import android.com.minus.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import model.Bill;


public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView name, date, issuer;
    public View mView;

    public ViewHolder(View view) {
        super(view);
        mView = view;
        name = (TextView) view.findViewById(R.id.name);
        date = (TextView) view.findViewById(R.id.date);
        issuer = (TextView) view.findViewById(R.id.issuer);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getIssuer() {
        return issuer;
    }

    public void setIssuer(TextView issuer) {
        this.issuer = issuer;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

}