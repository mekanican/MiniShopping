package com.nlh.minishoping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;


public class FavoriteListFragment extends Fragment {
    TextView tvNoFavoriteItem;
    GridView gvFavoriteList;
    SharedPreferences spFavorite;
    Integer number;


    public FavoriteListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

        Log.i("Function", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Function", "onCreateView");
        return inflater.inflate(R.layout.fragment_favorite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("Function", "onViewCreated");
        spFavorite = this.requireActivity().getSharedPreferences("FAVORITE" , Context.MODE_PRIVATE);
        tvNoFavoriteItem = getActivity().findViewById(R.id.tv_no_favorite_item_fragment);
        number = 0;
        if (!spFavorite.contains("Number")) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
        } else {
            number = Integer.parseInt(spFavorite.getString("Number", null).toString());
        }


        gvFavoriteList = getActivity().findViewById(R.id.grid_view_favorite_list_fragment);

        // this case does not happen =))
        if (number == 0) {
            tvNoFavoriteItem.setVisibility(View.VISIBLE);
            return;
        }

        Log.i("Number", Integer.toString(number));

        ArrayList<HomeProduct> favoriteList = new ArrayList<HomeProduct>();

        String name;
        String price;
        String imageLink;
        String category;
        String description;

        for (int i = 1; i <= number; i++) {
            name = spFavorite.getString("Name " + Integer.toString(i), null);
            price = spFavorite.getString("Price " + Integer.toString(i), null);
            imageLink = spFavorite.getString("Image " + Integer.toString(i), null);
            category = spFavorite.getString("Category " + Integer.toString(i), null);
            description = spFavorite.getString("Description " + Integer.toString(i), null);

            String priceWithoutSuffix = "";
            for (int j = 0; j < price.length() - 4; j++) {
                priceWithoutSuffix += price.charAt(j);
            }

            Product p = new Product(i, imageLink, name, category, description, Integer.parseInt(priceWithoutSuffix));
            HomeProduct hp = new HomeProduct(p);

            favoriteList.add(hp);
        }
        ProductGridViewAdapter favoriteProductsGridViewAdapter = new ProductGridViewAdapter(getActivity(), favoriteList);
        gvFavoriteList.setAdapter(favoriteProductsGridViewAdapter);
        gvFavoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeProduct product = (HomeProduct) gvFavoriteList.getItemAtPosition(i);
                String productName = product.getName();
                String productPrice = Integer.toString(product.getPrice()) + " VND";
                String productImageLink = product.getImageLink();
                String category = product.getCategory();
                String description = product.getDescription();

                Intent intent = new Intent(getContext(), ProductDetails.class);

                intent.putExtra("name", productName);
                intent.putExtra("price", productPrice);
                intent.putExtra("link", productImageLink);
                intent.putExtra("category", category);
                intent.putExtra("description", description);

                startActivity(intent);
            }
        });

    }
}