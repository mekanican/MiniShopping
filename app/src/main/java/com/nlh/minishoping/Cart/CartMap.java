package com.nlh.minishoping.Cart;

import androidx.annotation.NonNull;

import com.nlh.minishoping.Callback;
import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.DAO.ProductDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CartMap {
    private final HashMap<Integer, Integer> hashMap;
    private Callback update;

    private static final CartMap INSTANCE = new CartMap();

    public static CartMap getInstance() {
        return INSTANCE;
    }

    private CartMap() {
        hashMap = new HashMap<>();
    }

    public void setCallback(Callback c) {
        update = c;
    }

    public boolean addItem(int ID) {
        // Check duplicated
        if (!hashMap.containsKey(ID)) {
            hashMap.put(ID, 1);
            update.call();
            return true;
        }
        return false;
    }

    public void clearCart() {
        hashMap.clear();
        update.call();
    }

    public boolean increaseItemQuantity(int ID) {
        if (!hashMap.containsKey(ID)) return false;
        hashMap.compute(ID, (k, v) -> v + 1);
        update.call();
        return true;
    }

    public boolean decreaseItemQuantity(int ID) {
        if (!hashMap.containsKey(ID)) return false;
        hashMap.compute(ID, (k, v) -> {
            if (v > 0) return v - 1;
            return 0;
        });

        if (hashMap.get(ID) == 0) {
            hashMap.remove(ID);
        }
        update.call();
        return true;
    }

    public int getNumberOfItem() {
        return hashMap.size();
    }

    // For serialization to server
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"products\": [\n");
        hashMap.forEach((k, v) -> {
            stringBuilder
                    .append("{\n \"id\": ")
                    .append(k)
                    .append(",\n \"quantity\": ")
                    .append(v)
                    .append("\n},\n");
        });
        return stringBuilder.toString();
    }

    // For getting data from id -> list -> adapter for listview
    public Pair<ArrayList<Pair<GeneralInfo, Integer>>, Integer> generateArrayListWithTotal() {
        ArrayList<Pair<GeneralInfo, Integer>> result = new ArrayList<>();
        int totalPrice = 0;

        hashMap.forEach((k, v) -> {
            GeneralInfo generalInfo = ProductDatabase
                    .getInstance()
                    .productDao()
                    .getInfoByIDProduct(k);
            result.add(new Pair<>(generalInfo, v));
        });

        totalPrice = result.stream()
                .mapToInt(e -> (int) e.x.price * e.y)
                .sum();

        return new Pair<>(result, totalPrice);
    }

    public static class Pair<A, B> {
        public final A x;
        public final B y;

        public Pair(A x, B y) {
            this.x = x;
            this.y = y;
        }
    }
}
