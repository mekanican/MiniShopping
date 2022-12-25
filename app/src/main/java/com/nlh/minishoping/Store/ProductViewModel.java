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

// https://c1ctech.com/android-jetpack-paging-example-using-room/

public class ProductViewModel extends AndroidViewModel {
    private ProductDatabase productDatabase;
    public ProductDao productDao;
    public LiveData<PagedList<GeneralInfo>> productList;

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
}