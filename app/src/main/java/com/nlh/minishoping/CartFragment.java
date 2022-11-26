package com.nlh.minishoping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link CartFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class CartFragment extends Fragment {

    ArrayList<ProductCart> productList;
    ProductCartViewAdapter pcvAdapter;
    ListView productListView;

    public static double VAT = 1.15; // 15%
    public static int DEFAULT_SHIPPING_PRICE = 20000;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CartFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static CartFragment newInstance(String param1, String param2) {
//        CartFragment fragment = new CartFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        productList = new ArrayList<>();
        productList.add(new ProductCart(getContext(), 0, 1000, "Iphone"));
        productList.add(new ProductCart(getContext(), 1, 3000, "Táo"));
        productList.add(new ProductCart(getContext(), 2, 2000, "Gà"));
        productList.add(new ProductCart(getContext(), 3, 5000, "Gạo"));
        productList.add(new ProductCart(getContext(), 4, 5000, "a"));
        productList.add(new ProductCart(getContext(), 5, 5000, "b"));
        productList.add(new ProductCart(getContext(), 6, 5000, "c"));
        productList.add(new ProductCart(getContext(), 7, 5000, "d"));
        productList.add(new ProductCart(getContext(), 8, 5000, "e"));
        productList.add(new ProductCart(getContext(), 9, 5000, "f"));
        productList.add(new ProductCart(getContext(), 10, 5000, "g"));
        productList.add(new ProductCart(getContext(), 11, 5000, "h"));
        productList.add(new ProductCart(getContext(), 12, 5000, "i"));


        // Setup list view
        pcvAdapter = new ProductCartViewAdapter(productList, () -> update());
        productListView = getActivity().findViewById(R.id.cart_lv);
        productListView.setAdapter(pcvAdapter);

        resetValue();

        // Handle deleteAll
        ((Button) getActivity().findViewById(R.id.delete_)).setOnClickListener(view1 -> {
            productList.clear();
            pcvAdapter.notifyDataSetChanged();
            resetValue();
        });


        // Handle proceed
        ((Button) getActivity().findViewById(R.id.proceed_)).setOnClickListener(view1 -> {
            Toast.makeText(getContext(),
                    String.format("Chúc mừng bạn đã tốn %d VND tiền ngu <(\")",
                            (int) Math.round((totalProductsPrice(productList) + DEFAULT_SHIPPING_PRICE) * VAT)),
                    Toast.LENGTH_LONG).show();
        });
    }

    private void setPriceByID(int ID, int price) {
        ((TextView) getActivity().findViewById(ID)).setText(String.format("%d VND", price));
    }

    // Hacky fp btw <(")
    private int totalProductsPrice(ArrayList<ProductCart> products) {
        return products.stream()
                .mapToInt(a -> a.total())
                .sum();
    }

    private void resetValue() {
        setPriceByID(R.id.product_price, 0);
        setPriceByID(R.id.shipping_price, DEFAULT_SHIPPING_PRICE);
        setPriceByID(R.id.total_price, 0);
    }

    private void update() {
        int total = totalProductsPrice(productList);
        setPriceByID(R.id.product_price, total);
        setPriceByID(R.id.total_price, (int) Math.round((total + DEFAULT_SHIPPING_PRICE) * VAT));
    }


}