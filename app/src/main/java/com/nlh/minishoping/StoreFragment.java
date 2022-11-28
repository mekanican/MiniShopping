package com.nlh.minishoping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Adapted from Home!
public class StoreFragment extends Fragment {
    GridView gvProductList;
    ArrayList<HomeProduct> homeProductArrayList;

    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        homeProductArrayList = SharedInfo.getInstance().getProductHome();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gvProductList = getActivity().findViewById(R.id.grid_view_product_list);
        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(getContext(), homeProductArrayList);
        gvProductList.setAdapter(productGridViewAdapter);
        gvProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeProduct product = (HomeProduct) gvProductList.getItemAtPosition(i);

                String name = product.getName();
                String price = Integer.toString(product.getPrice()) + " VND";
                String imageLink = product.getImageLink();
                String category = product.getCategory();
                String description = product.getDescription();

                Intent intent = new Intent(getContext(), ProductDetails.class);
                intent.putExtra("ID", product.getId());
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("link", imageLink);
                intent.putExtra("category", category);
                intent.putExtra("description", description);

                startActivity(intent);
            }
        });
    }
}