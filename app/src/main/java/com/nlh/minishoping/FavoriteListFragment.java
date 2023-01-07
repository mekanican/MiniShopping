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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.Collections;
import java.util.Random;


public class FavoriteListFragment extends Fragment {
    TextView tvNoFavoriteItem;
    String hashValue;
    RecyclerView rvFavoriteList;
    SwipeRefreshLayout srl;

    int[] favoriteArray;


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

        srl = getActivity().findViewById(R.id.favorite_swipe_layout);

        tvNoFavoriteItem = getActivity().findViewById(R.id.tv_no_favorite_item_fragment);

        rvFavoriteList = getActivity().findViewById(R.id.favorite_recycler_view);
        rvFavoriteList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);

        favoriteArray = ServerConnector.GetFavoriteList(hashValue);

        if (favoriteArray == null) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
        }

        productViewModel.initSearch(favoriteArray);
        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
           int itemPosition = rvFavoriteList.getChildAdapterPosition(view1);
            GeneralInfo gi = productViewModel.otherList.getValue().get(itemPosition);
            Intent intent = new Intent(getActivity(), ProductDetails.class)
                    .putExtra("ID", gi.id)
                    .putExtra("HASH", hashValue);
            startActivity(intent);
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                shuffle();
                srl.setRefreshing(false);
            }
        });

        productViewModel.otherList.observe(getActivity(), productAdapter::submitList);
        rvFavoriteList.setAdapter(productAdapter);
    }

    private void shuffle() {
        ProductViewModel productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);

        favoriteArray = ServerConnector.GetFavoriteList(hashValue);

        if (favoriteArray == null) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
        }

        productViewModel.initSearch(favoriteArray);
        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
            int itemPosition = rvFavoriteList.getChildAdapterPosition(view1);
            GeneralInfo gi = productViewModel.otherList.getValue().get(itemPosition);
            Intent intent = new Intent(getActivity(), ProductDetails.class)
                    .putExtra("ID", gi.id)
                    .putExtra("HASH", hashValue);
            startActivity(intent);
        });

        productViewModel.otherList.observe(getActivity(), productAdapter::submitList);
        rvFavoriteList.setAdapter(productAdapter);
    }

}