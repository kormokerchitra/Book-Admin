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

public class RatingAdapter extends ArrayAdapter<BookModel> {

    ArrayList<BookModel> products;
    Context context;
    int resource;

    public RatingAdapter(Context context, int resource, ArrayList<BookModel> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.rating_item, null, true);

        }
        BookModel product = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.title); //type casting
        String book_name = product.getBook_name();
        name.setText(book_name);

        TextView category = (TextView) convertView.findViewById(R.id.category); //type casting
        String category_title = product.getCategory_name();
        category.setText(category_title);

        TextView rating = (TextView) convertView.findViewById(R.id.rating); //type casting
        String total = product.getRating();
        String user = product.getReviewer_number();
        rating.setText(total + " (" + user + ")");

        return convertView;
    }

    ;
}