package com.nlh.minishoping;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

import java.util.Objects;

public class TestingPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_place);

        RecyclerView recyclerView = findViewById(R.id.recommendation_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.init();

        ProductAdapter productAdapter = new ProductAdapter(view -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view);
            GeneralInfo gi = Objects.requireNonNull(productViewModel.productList.getValue()).get(itemPosition);
            // DEBUG
            assert gi != null;
            Toast.makeText(this, gi.id + gi.name, Toast.LENGTH_SHORT).show();
        });
        productViewModel.productList.observe(this, productAdapter::submitList);

        recyclerView.setAdapter(productAdapter);


    }
}