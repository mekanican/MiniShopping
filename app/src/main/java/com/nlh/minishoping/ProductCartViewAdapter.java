package com.nlh.minishoping;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nlh.minishoping.Cart.CartMap;
import com.nlh.minishoping.DAO.GeneralInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductCartViewAdapter extends BaseAdapter {

    final ArrayList<CartMap.Pair<GeneralInfo, Integer>> listProduct;
    final Callback1 increase, decrease;

    public ProductCartViewAdapter(ArrayList<CartMap.Pair<GeneralInfo, Integer>> listProduct,
                                  Callback1 increase,
                                  Callback1 decrease) {
        this.listProduct = listProduct;
        this.increase = increase;
        this.decrease = decrease;
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
        return listProduct.get(i).x.id;
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

        CartMap.Pair<GeneralInfo, Integer> product = (CartMap.Pair<GeneralInfo, Integer>) getItem(i);
        // Modify each "item" in list from data of ith product.
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        setTextByID(productView, R.id.id_, String.format(Locale.ENGLISH, "#%d", product.x.id));
        setTextByID(productView, R.id.name_, product.x.name);
        setTextByID(productView, R.id.price_, String.format(Locale.ENGLISH, "%d * %s VND", product.y,
                formatter.format(product.x.price)));

        // setImageByID(productView, R.id.image_, product.image);

        // product.getImageToImageView(productView.findViewById(R.id.image_));
        // TODO: Handle image

        // Handle button U/D
        productView.findViewById(R.id.up_cart).setOnClickListener(view1 -> increase.call(product.x.id));
        productView.findViewById(R.id.down_cart).setOnClickListener(view1 -> decrease.call(product.x.id));

        return productView;
    }

    private void setTextByID(View view, int ID, String text) {
        ((TextView) view.findViewById(ID)).setText(text);
    }
}
