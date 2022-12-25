package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

public class TestingPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_place);

        RecyclerView recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.init();

        ProductAdapter productAdapter = new ProductAdapter(view -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view);
            GeneralInfo gi = productViewModel.productList.getValue().get(itemPosition);
            // DEBUG
            Toast.makeText(this, gi.id + gi.name, Toast.LENGTH_SHORT).show();
        });
        productViewModel.productList.observe(this, pageList -> {
            productAdapter.submitList(pageList);
        });

        recyclerView.setAdapter(productAdapter);


    }
}