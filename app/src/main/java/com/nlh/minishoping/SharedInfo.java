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
    private ArrayList<ProductCart> productCart;

    private SharedInfo() {
        productCart = new ArrayList<>();
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

    public boolean addProductToCart(HomeProduct homeProduct) {
        ProductCart newProduct = new ProductCart(homeProduct);
        if (!productCart.isEmpty() && productCart.stream().anyMatch(a -> a.getID() == newProduct.getID())) {
            return false;
        }
        productCart.add(newProduct);
        return true;
    }

    public ArrayList<ProductCart> getProductCart() {
        return productCart;
    }
}
