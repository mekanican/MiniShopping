package com.nlh.minishoping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

import java.util.Objects;

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
        hashValue = Objects.requireNonNull(mainActivity).getHashValue();
        Log.i("HASH VALUE GOTTEN IN STORE FRAGMENT", hashValue);

        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etProductNameToFind = requireActivity().findViewById(R.id.et_product_name);

        RecyclerView recyclerView = requireActivity().findViewById(R.id.recommendation_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ProductViewModel productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.init();

        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view1);
            GeneralInfo gi = Objects.requireNonNull(productViewModel.productList.getValue()).get(itemPosition);
            assert gi != null;
            Intent intent = new Intent(getActivity(), ProductDetails.class)
                    .putExtra("ID", gi.id)
                            .putExtra("HASH", hashValue);
            requireActivity().startActivity(intent);
        });
        productViewModel.productList.observe(requireActivity(), productAdapter::submitList);
        recyclerView.setAdapter(productAdapter);

        requireActivity().findViewById(R.id.btn_search).setOnClickListener(view1 -> {
            String query = String.valueOf(etProductNameToFind.getText());

            Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
            intent.putExtra("QUERY", query);
            intent.putExtra("HASH", hashValue);

            startActivity(intent);
        });
    }

}