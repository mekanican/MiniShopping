package com.nlh.minishoping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    ArrayList<CartProduct> productList;
    ProductCartViewAdapter pcvAdapter;
    ListView lvProductList;
    BottomNavigationView bottomNavigationView;

    public static double VAT = 1.08; // 8%
    public static int DEFAULT_SHIPPING_PRICE = 20000;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup productList
        productList = SharedInfo.getInstance().getProductCart();

        // Setup list view
        pcvAdapter = new ProductCartViewAdapter(productList, this::update);
        lvProductList = getActivity().findViewById(R.id.cart_lv);
        lvProductList.setAdapter(pcvAdapter);

        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        SharedInfo.getInstance().setCallbackUpdateCart(() -> {
            int total = bottomNavigationView.getOrCreateBadge(R.id.cart).getNumber();
            bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(total + 1);
            pcvAdapter.notifyDataSetChanged();
            update();
        });

        resetValue();

        // Handle deleteAll
        (getActivity().findViewById(R.id.delete_)).setOnClickListener(view1 -> {
            removeAll();
        });


        // Handle proceed
        (getActivity().findViewById(R.id.proceed_)).setOnClickListener(view1 -> {
            processCart();
            removeAll();
        });
    }

    private void processCart() {
//        Toast.makeText(getContext(),
//                String.format("Chúc mừng bạn đã tốn %d VND, sản phẩm sẽ được giao trong nay mai :>",
//                        (int) Math.round((totalProductsPrice(productList) + DEFAULT_SHIPPING_PRICE) * VAT)),
//                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), AddressActivity.class);
        startActivity(intent);
    }

    private void removeAll() {
        productList.clear();
        pcvAdapter.notifyDataSetChanged();
        resetValue();
        bottomNavigationView.removeBadge(R.id.cart);
    }

    private void setPriceByID(int ID, int price) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        ((TextView) getActivity().findViewById(ID)).setText(formatter.format(price) + " VND");
    }


    private int totalProductsPrice(ArrayList<CartProduct> products) {
        return products.stream()
                .mapToInt(CartProduct::total)
                .sum();
    }

    private void resetValue() {
        setPriceByID(R.id.product_price, 0);
        setPriceByID(R.id.shipping_price, DEFAULT_SHIPPING_PRICE);
        setPriceByID(R.id.total_price, (int) (DEFAULT_SHIPPING_PRICE * VAT));
    }

    private void update() {
        int total = totalProductsPrice(productList);
        setPriceByID(R.id.product_price, total);
        setPriceByID(R.id.total_price, (int) Math.round((total + DEFAULT_SHIPPING_PRICE) * VAT));
    }
}