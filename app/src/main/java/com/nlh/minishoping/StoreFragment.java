package com.nlh.minishoping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

import java.util.ArrayList;

// Adapted from Home!
public class StoreFragment extends Fragment {

    TextView etProductNameToFind;
    String hashValue;

    public StoreFragment() {
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

        MainActivity mainActivity = (MainActivity) getActivity();
        hashValue = mainActivity.getHashValue();
        Log.i("HASH VALUE GOTTEN IN STORE FRAGMENT", hashValue);

        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etProductNameToFind = getActivity().findViewById(R.id.et_product_name);

        RecyclerView recyclerView = getActivity().findViewById(R.id.rview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        productViewModel.init();

        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view1);
            GeneralInfo gi = productViewModel.productList.getValue().get(itemPosition);
            Intent intent = new Intent(getActivity(), ProductDetails.class)
                    .putExtra("ID", gi.id)
                            .putExtra("HASH", hashValue);
            getActivity().startActivity(intent);
        });
        productViewModel.productList.observe(getActivity(), productAdapter::submitList);
        recyclerView.setAdapter(productAdapter);
        getActivity().findViewById(R.id.btn_search).setOnClickListener(view1 -> {
            // TODO: Fix this
//            String productNameForSearching = String.valueOf(etProductNameToFind.getText());
//            ArrayList<Integer> searchResultsIndices = DataHandler.GetSearchProducts(productNameForSearching);
//            ArrayList<HomeProduct> searchResults = new ArrayList<>();
//
//            for (int i = 0; i < searchResultsIndices.size(); i++) {
//                searchResults.add(homeProductArrayList.get(searchResultsIndices.get(i)));
//            }
//
//            Intent intent = new Intent(getContext(), SearchResultsActivity.class);
//            intent.putExtra("Number", (searchResultsIndices.size()));
//
//            for (int i = 0; i < searchResults.size(); i++) {
//                intent.putExtra("ID" + i, searchResults.get(i).getId());
//            }
//            startActivity(intent);
        });
    }

}