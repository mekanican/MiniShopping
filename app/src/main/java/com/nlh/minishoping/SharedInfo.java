package com.nlh.minishoping;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SharedInfo {
    // Singleton
    private static final SharedInfo INSTANCE = new SharedInfo();

    public static SharedInfo getInstance() {
        return INSTANCE;
    }


    private ArrayList<Product> productRaw;
    private ArrayList<CartProduct> cartProduct;

    private Callback updateCart;
    private Callback updateFavorite;
    
    private SharedInfo() {
        cartProduct = new ArrayList<>();
        productRaw = new ArrayList<>();
    }


    public void initData() {
        productRaw = DataHandler.GetProducts();
    }

    public ArrayList<Product> getProductRaw() {
        return productRaw;
    }

    public ArrayList<HomeProduct> getProductHome() {
        return productRaw.stream()
                .map(HomeProduct::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean addProductToCart(int ID) {
        CartProduct newProduct = new CartProduct(new HomeProduct(productRaw.get(ID - 1)));
        if (!cartProduct.isEmpty() && cartProduct.stream().anyMatch(a -> a.getID() == newProduct.getID())) {
            return false;
        }
        cartProduct.add(newProduct);
        updateCart.call();
        return true;
    }

    public void addFavoriteTrigger() {
        updateFavorite.call();
    }

    public HomeProduct getProductByID(int ID) {
        return new HomeProduct(productRaw.get(ID - 1));
    }

    public ArrayList<CartProduct> getProductCart() {
        return cartProduct;
    }

    public void setCallbackUpdateCart(Callback c) {
        updateCart = c;
    }

    public void setCallbackUpdateFavorite(Callback c) {
        updateFavorite = c;
    }
}
