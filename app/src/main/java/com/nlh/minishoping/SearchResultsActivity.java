package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    TextView tvNoSearchResults;
    GridView gvSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        intent = getIntent();
        bundle = intent.getExtras();

        tvNoSearchResults = findViewById(R.id.tv_no_search_result);

        ArrayList<HomeProduct> productArrayList = setupProductArrayList();
        if (productArrayList.size() > 0) {
            gvSearchResults = findViewById(R.id.grid_view_product_list_search_results);

            ProductGridViewAdapter productGridViewAdapter = new ProductGridViewAdapter(this, productArrayList);
            gvSearchResults.setAdapter(productGridViewAdapter);
            gvSearchResults.setOnItemClickListener((adapterView, view, i, l) -> {
                HomeProduct product = (HomeProduct) gvSearchResults.getItemAtPosition(i);

                String name = product.getName();
                String price = product.getPrice() + " VND";
                String imageLink = product.getImageLink();
                String category = product.getCategory();
                String description = product.getDescription();

                Intent intent = new Intent(SearchResultsActivity.this, ProductDetails.class);
                intent.putExtra("ID", product.getId());
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("link", imageLink);
                intent.putExtra("category", category);
                intent.putExtra("description", description);

                startActivity(intent);
            });
        }

        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
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

    private ArrayList<HomeProduct> setupProductArrayList() {
        ArrayList<HomeProduct> result = new ArrayList<>();
        int number = bundle.getInt("Number");
        if (number == 0) {
            tvNoSearchResults.setVisibility(View.VISIBLE);
            return result;
        }
        for (int i = 0; i < number; i++) {
            int id = bundle.getInt("ID" + i);
            HomeProduct homeProduct = SharedInfo.getInstance().getProductByID(id);
            result.add(homeProduct);
        }
        return result;
    }

}