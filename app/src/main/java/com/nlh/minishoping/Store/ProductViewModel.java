package com.nlh.minishoping.Store;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.DAO.ProductDao;
import com.nlh.minishoping.DAO.ProductDatabase;
import com.nlh.minishoping.DAO.Product;

// https://c1ctech.com/android-jetpack-paging-example-using-room/

public class ProductViewModel extends AndroidViewModel {
    private ProductDatabase productDatabase;
    public ProductDao productDao;
    public LiveData<PagedList<GeneralInfo>> productList;
    public LiveData<PagedList<GeneralInfo>> otherList;

    public ProductViewModel(@NonNull Application context) {
        super(context);
        productDatabase = ProductDatabase.getInstance(context);
        productDao = productDatabase.productDao();
    }

    public void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(50)
                .build();
        productList = (new LivePagedListBuilder<>(productDao.getPageProducts(), pagedListConfig)).build();
    }

    public void initCategory(String category) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPrefetchDistance(6)
                .setPageSize(10)
                .build();
        productList = (new LivePagedListBuilder<>(productDao.getCategoryProducts(category), pagedListConfig)).build();
    }

    public void initSearch(int[] idList) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPrefetchDistance(6)
                .setPageSize(10)
                .build();
        otherList = (new LivePagedListBuilder<>(productDao.getSearchProductFromIdList(idList), pagedListConfig)).build();
    }
}
