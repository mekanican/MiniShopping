package com.nlh.minishoping.Cart;

import androidx.annotation.NonNull;

import com.nlh.minishoping.Callback;
import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.DAO.ProductDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CartMap {
    private final HashMap<Integer, Integer> hashMap;
    private final Callback update;

    public CartMap(Callback c) {
        hashMap = new HashMap<>();
        update = c;
    }

    public void addItem(int ID) {
        // Check duplicated
        if (!hashMap.containsKey(ID)) {
            hashMap.put(ID, 0);
            update.call();
        }
    }

    public void clearCart() {
        hashMap.clear();
        update.call();
    }

    public boolean increaseItemQuantity(int ID) {
        if (!hashMap.containsKey(ID)) return false;
        hashMap.compute(ID, (k, v) -> v + 1);
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
            update.call();
        }
        return true;
    }

    public int getNumberOfItem() {
        return hashMap.size();
    }

    // For serialization to server
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        hashMap.forEach((k, v) -> {
            stringBuilder
                    .append(k)
                    .append(':')
                    .append(v)
                    .append(',');
        });
        return stringBuilder.toString();
    }

    // For getting data from id -> list -> adapter for listview
    public ArrayList<Pair<GeneralInfo, Integer>> generateArrayList() {
        ArrayList<Pair<GeneralInfo, Integer>> result = new ArrayList<>();

        hashMap.forEach((k, v) -> {
            GeneralInfo generalInfo = ProductDatabase
                    .getInstance()
                    .productDao()
                    .getInfoByIDProduct(k);
            result.add(new Pair<>(generalInfo, v));
        });
        return result;
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
