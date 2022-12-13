package com.nlh.minishoping;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductCartViewAdapter extends BaseAdapter {

    final ArrayList<CartProduct> listProduct;
    final Callback onChanged;

    public ProductCartViewAdapter(ArrayList<CartProduct> listProduct, Callback onChanged) {
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
        return listProduct.get(i).getID();
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

        CartProduct product = (CartProduct) getItem(i);
        // Modify each "item" in list from data of ith product.
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        setTextByID(productView, R.id.id_, String.format("#%d", product.getID()));
        setTextByID(productView, R.id.name_, product.getName());
        setTextByID(productView, R.id.price_, String.format("%d * %s VND", product.getNumberOfItem(),
                formatter.format(product.getPrice())));

        // setImageByID(productView, R.id.image_, product.image);

        product.getImageToImageView(productView.findViewById(R.id.image_));

        // Handle button U/D
        setHandleProduct(productView, this, R.id.up_cart, product::addItem);
        setHandleProduct(productView, this, R.id.down_cart, product::removeItem);

        return productView;
    }

    private void setTextByID(View view, int ID, String text) {
        ((TextView) view.findViewById(ID)).setText(text);
    }

    private void setHandleProduct(View view, BaseAdapter context, int ID, Callback b) {
        view.findViewById(ID).setOnClickListener(view1 -> {
            b.call();
            context.notifyDataSetChanged();
            onChanged.call();
        });
    }
}
