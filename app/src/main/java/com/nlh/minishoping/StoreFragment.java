package com.nlh.minishoping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }
}