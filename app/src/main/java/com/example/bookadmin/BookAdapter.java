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

public class BookAdapter extends ArrayAdapter<BookModel> {

    ArrayList<BookModel> products;
    Context context;
    int resource;

    public BookAdapter(Context context, int resource, ArrayList<BookModel> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.book_item, null, true);

        }
        BookModel product = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.title); //type casting
        String book_name = product.getBook_name();
        name.setText(book_name);

        TextView category = (TextView) convertView.findViewById(R.id.category); //type casting
        String category_title = product.getCategory_name();
        category.setText(category_title);

        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        return convertView;
    }

    ;
}