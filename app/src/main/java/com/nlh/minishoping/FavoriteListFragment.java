package com.nlh.minishoping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class FavoriteListFragment extends Fragment {
    TextView tvNoFavoriteItem;
    GridView gvFavoriteList;
    SharedPreferences spFavorite;
    Integer number;
    Integer last_pos;


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
        return inflater.inflate(R.layout.fragment_favorite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<HomeProduct> favoriteList = new ArrayList<>();

        spFavorite = this.requireActivity().getSharedPreferences("FAVORITE", Context.MODE_PRIVATE);
        tvNoFavoriteItem = getActivity().findViewById(R.id.tv_no_favorite_item_fragment);
        gvFavoriteList = getActivity().findViewById(R.id.grid_view_favorite_list_fragment);

        number = 0;
        last_pos = 0;
        noItemNotify();
        reloadFavorite(favoriteList);

        ProductGridViewAdapter favoriteProductsGridViewAdapter = new ProductGridViewAdapter(getActivity(), favoriteList);
        gvFavoriteList.setAdapter(favoriteProductsGridViewAdapter);

        SharedInfo.getInstance().setCallbackUpdateFavorite(() -> {
            noItemNotify();
            reloadFavorite(favoriteList);
            favoriteProductsGridViewAdapter.notifyDataSetChanged();
        });
        gvFavoriteList.setOnItemClickListener((adapterView, view1, i, l) -> {
            HomeProduct product = (HomeProduct) gvFavoriteList.getItemAtPosition(i);
            String productName = product.getName();
            String productPrice = product.getPrice() + " VND";
            String productImageLink = product.getImageLink();
            String category = product.getCategory();
            String description = product.getDescription();

            Intent intent = new Intent(getContext(), ProductDetails.class);
            intent.putExtra("ID", product.getId());
            intent.putExtra("name", productName);
            intent.putExtra("price", productPrice);
            intent.putExtra("link", productImageLink);
            intent.putExtra("category", category);
            intent.putExtra("description", description);

            startActivity(intent);
        });

    }

    private void noItemNotify() {
        if (!spFavorite.contains("Number")) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
        } else {
            number = Integer.parseInt(spFavorite.getString("Number", null));
        }

        if (number == 0) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
        } else {
            tvNoFavoriteItem.setVisibility(View.INVISIBLE);
        }
    }

    private void reloadFavorite(ArrayList<HomeProduct> favoriteList) {
        String name;
        String price;
        String imageLink;
        String category;
        String description;
        String idx;

        for (int i = last_pos + 1; i <= number; i++) {
            idx = spFavorite.getString("ID " + i, null);
            name = spFavorite.getString("Name " + Integer.toString(i), null);
            price = spFavorite.getString("Price " + Integer.toString(i), null);
            imageLink = spFavorite.getString("Image " + Integer.toString(i), null);
            category = spFavorite.getString("Category " + Integer.toString(i), null);
            description = spFavorite.getString("Description " + Integer.toString(i), null);

            StringBuilder priceWithoutSuffix = new StringBuilder();
            for (int j = 0; j < price.length() - 4; j++) {
                priceWithoutSuffix.append(price.charAt(j));
            }

            Product p = new Product(Integer.parseInt(idx), imageLink, name, category, description, Integer.parseInt(priceWithoutSuffix.toString()));
            HomeProduct hp = new HomeProduct(p);

            favoriteList.add(hp);
        }
        last_pos = number;
    }
}