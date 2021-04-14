package com.example.bookadmin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RatingDetailsAdapter extends ArrayAdapter<RatingDetailsModel> {

    ArrayList<RatingDetailsModel> products;
    Context context;
    int resource;

    public RatingDetailsAdapter(Context context, int resource, ArrayList<RatingDetailsModel> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.rating_info_item, null, true);

        }
        RatingDetailsModel product = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.title); //type casting
        String person_name = product.getReview_person();
        name.setText(person_name);

        TextView date = (TextView) convertView.findViewById(R.id.date); //type casting
        String rev_date = product.getReview_date();
        date.setText(rev_date);

        TextView comment = (TextView) convertView.findViewById(R.id.comment); //type casting
        String com = product.getComment();
        comment.setText(com);

        TextView rating = (TextView) convertView.findViewById(R.id.rating); //type casting
        String total = product.getRating();
        rating.setText(total);
        return convertView;
    }

    ;
}