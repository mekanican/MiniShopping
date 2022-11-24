package com.nlh.minishoping;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// For callback function
interface Callback {
    void call();
}

public class ProductCartViewAdapter extends BaseAdapter {

    final ArrayList<ProductCart> listProduct;
    final Callback onChanged;

    public ProductCartViewAdapter(ArrayList<ProductCart> listProduct, Callback onChanged) {
        this.listProduct = listProduct;
        this.onChanged = onChanged;
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return listProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listProduct.get(i).ID;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View productView;
        // If view == null -> first created, else, reuse
        if (view == null) {
            productView = View.inflate(viewGroup.getContext(), R.layout.product_cart_view, null);
        } else {
            productView = view;
        }

        ProductCart product = (ProductCart) getItem(i);
        // Modify each "item" in list from data of ith product.
        setTextByID(productView, R.id.id_, String.format("ID = %d", product.ID));
        setTextByID(productView, R.id.name_, String.format("%s", product.name));
        setTextByID(productView, R.id.price_, String.format("%d * %d VND", product.numberOfItem, product.price));

        setImageByID(productView, R.id.image_, product.image);

        // Handle button U/D
        setHandleProduct(productView, this, R.id.up_cart, () -> product.addItem());
        setHandleProduct(productView, this, R.id.down_cart, () -> product.removeItem());

        return productView;
    }

    private void setTextByID(View view, int ID, String text) {
        ((TextView) view.findViewById(ID)).setText(text);
    }

    private void setImageByID(View view, int ID, Drawable d) {
        ((ImageView) view.findViewById(ID)).setImageDrawable(d);
    }

    private void setHandleProduct(View view, BaseAdapter context, int ID, Callback b) {
        ((Button) view.findViewById(ID)).setOnClickListener(view1 -> {
            b.call();
            context.notifyDataSetChanged();
            onChanged.call();
        });
    }
}
