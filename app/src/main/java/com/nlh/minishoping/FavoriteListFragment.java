package com.nlh.minishoping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.nlh.minishoping.Connector.ServerConnector;
import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.DAO.Product;
import com.nlh.minishoping.DAO.ProductDatabase;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

import java.util.ArrayList;


public class FavoriteListFragment extends Fragment {
    TextView tvNoFavoriteItem;
    String hashValue;
    RecyclerView rvFavoriteList;


    public FavoriteListFragment() {
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

        return inflater.inflate(R.layout.fragment_favorite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<HomeProduct> favoriteList = new ArrayList<>();

        tvNoFavoriteItem = getActivity().findViewById(R.id.tv_no_favorite_item_fragment);

        rvFavoriteList = getActivity().findViewById(R.id.favorite_recycler_view);
        rvFavoriteList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);

        int[] favoriteArray = ServerConnector.GetFavoriteList(hashValue);

        Product[] products = new Product[favoriteArray.length];
        for (int i = 0; i < favoriteArray.length; i++) {
            products[i] = ProductDatabase.getInstance().productDao().getByIDProduct(favoriteArray[i]);
        }

        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
            int itemPosition = rvFavoriteList.getChildAdapterPosition(view1);
            GeneralInfo gi = productViewModel.productList.getValue().get(itemPosition);
            Intent intent = new Intent(getActivity(), ProductDetails.class)
                    .putExtra("ID", gi.id)
                    .putExtra("HASH", hashValue);
            getActivity().startActivity(intent);
        });
        productViewModel.productList.observe(getActivity(), productAdapter::submitList);
        rvFavoriteList.setAdapter(productAdapter);
    }

}