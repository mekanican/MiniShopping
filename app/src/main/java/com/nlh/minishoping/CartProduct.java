package com.nlh.minishoping;

public class CartProduct {
    private final HomeProduct wrapped;
    private int numberOfItem;

    public CartProduct(HomeProduct homeProduct) {
        wrapped = homeProduct;
        numberOfItem = 1;
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

    public void addItem() {
        numberOfItem++;
    }

    public String getEmail() {
        return wrapped.getEmail();
    }

    public int total() {
        return numberOfItem * wrapped.getPrice();
    }

}
