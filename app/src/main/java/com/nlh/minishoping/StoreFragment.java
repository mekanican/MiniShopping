package com.nlh.minishoping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

// Adapted from Home!
public class StoreFragment extends Fragment {
    GridView gvProductList;
    ArrayList<HomeProduct> homeProductArrayList;

    TextView etProductNameToFind;

    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        etProductNameToFind = getActivity().findViewById(R.id.et_product_name);
        gvProductList = getActivity().findViewById(R.id.grid_view_product_list);
        ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(getContext(), homeProductArrayList);
        gvProductList.setAdapter(productGridViewAdapter);
        gvProductList.setOnItemClickListener((adapterView, view12, i, l) -> {
            HomeProduct product = (HomeProduct) gvProductList.getItemAtPosition(i);

            String name = product.getName();
            String price = product.getPrice() + " VND";
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
        });

        ((Button) getActivity().findViewById(R.id.btn_search)).setOnClickListener(view1 -> {
            String productNameForSearching = String.valueOf(etProductNameToFind.getText());
            ArrayList<Integer> searchResultsIndices = DataHandler.GetSearchProducts(productNameForSearching);
            ArrayList<HomeProduct> searchResults = new ArrayList<>();

            for (int i = 0; i < searchResultsIndices.size(); i++) {
                searchResults.add(homeProductArrayList.get(searchResultsIndices.get(i)));
            }

            Intent intent = new Intent(getContext(), SearchResultsActivity.class);
            intent.putExtra("Number", (searchResultsIndices.size()));

            for (int i = 0; i < searchResults.size(); i++) {
                intent.putExtra("ID" + i, searchResults.get(i).getId());
            }
            startActivity(intent);
        });
    }

}