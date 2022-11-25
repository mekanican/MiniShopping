//https://www.geeksforgeeks.org/gridview-in-android-with-example/
package com.nlh.minishoping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductGridViewAdapter extends ArrayAdapter<HomeProduct> {
    public ProductGridViewAdapter(@NonNull Context context, ArrayList<HomeProduct> productArrayList) {
        super(context, 0, productArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        HomeProduct product = getItem(position);
        TextView tvProductName = listItemView.findViewById(R.id.tvProductName);
        ImageView ivProductImage = listItemView.findViewById(R.id.ivProductImage);
        TextView tvProductPrice = listItemView.findViewById(R.id.tvProductPrice);

        tvProductName.setText(product.getName());
        product.getImagetoImageView(getContext(), ivProductImage);
        // ivProductImage.setImageResource(R.drawable.hung);
        tvProductPrice.setText(product.getPrice() + " VND");

        listItemView.setOnClickListener(view -> {

        });

        return listItemView;
    }
}
