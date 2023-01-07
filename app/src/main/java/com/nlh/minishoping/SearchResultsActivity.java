package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.nlh.minishoping.Connector.ServerConnector;
import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.DAO.Product;
import com.nlh.minishoping.DAO.ProductDatabase;
import com.nlh.minishoping.Store.ProductAdapter;
import com.nlh.minishoping.Store.ProductViewModel;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    TextView tvNoSearchResults;
    String hashValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        intent = getIntent();
        bundle = intent.getExtras();

        tvNoSearchResults = findViewById(R.id.tv_no_search_result);

        String searchQuery = bundle.getString("QUERY");
        hashValue = bundle.getString("HASH");

        int[] searchResults = ServerConnector.GetSearchResults(searchQuery);

        if (searchResults == null) {
            tvNoSearchResults.setVisibility(View.VISIBLE);
            return;
        }

        RecyclerView rvResults = findViewById(R.id.search_result_recycler_view);
        rvResults.setLayoutManager(new GridLayoutManager(this, 2));

        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.initSearch(searchResults);

        ProductAdapter productAdapter = new ProductAdapter(view1 -> {
            int itemPosition = rvResults.getChildAdapterPosition(view1);
            GeneralInfo gi = productViewModel.productList.getValue().get(itemPosition);
            Intent intent = new Intent(this, ProductDetails.class)
                    .putExtra("ID", gi.id)
                    .putExtra("HASH", hashValue);
            this.startActivity(intent);
        });
        productViewModel.productList.observe(this, productAdapter::submitList);
        rvResults.setAdapter(productAdapter);

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}