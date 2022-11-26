package com.nlh.minishoping;

import android.widget.ImageView;


public class ProductCart {
    private final HomeProduct wrapped;
    private int numberOfItem;

    public ProductCart(HomeProduct homeProduct) {
        wrapped = homeProduct;
    }

    public int getID() {
        return wrapped.getId();
    }

    public String getName() {
        return wrapped.getName();
    }

    public int getPrice() {
        return wrapped.getPrice();
    }

    public void getImageToImageView(ImageView imageView) {
        wrapped.getImageToImageView(imageView);
    }

    public void addItem() {
        numberOfItem++;
    }

    public void removeItem() {
        if (numberOfItem > 0) {
            numberOfItem--;
        }
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public int total() {
        return numberOfItem * wrapped.getPrice();
    }

}
