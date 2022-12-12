package com.nlh.minishoping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.*;

public class DataHandler {
    private static final HashMap<String, ArrayList<Product>> categoryMap = new HashMap<>();
    private static final ArrayList<Set<String>> productNames = new ArrayList<>();

    public static ArrayList<Product> GetProducts() {
        ArrayList<Product> ret = new ArrayList<>();
        StringBuilder json = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(WelcomeActivity.assetManager.open("data.json"), StandardCharsets.UTF_8));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                json.append(mLine);
            }
        } catch (IOException ignored) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }

        try {
            JSONArray jsonArray = new JSONArray(json.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                ret.add(new Product(
                        obj.getInt("id"),
                        obj.getString("image"),
                        obj.getString("name"),
                        obj.getString("category"),
                        obj.getString("description"),
                        obj.getInt("price")
                ));
                Set<String> cur = new HashSet<>();
                Collections.addAll(cur, obj.getString("name").toLowerCase().split(" "));
                productNames.add(cur);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Product p : ret) {
            if (!categoryMap.containsKey(p.getCategory())) {
                categoryMap.put(p.getCategory(), new ArrayList<>());
            }
            categoryMap.get(p.getCategory()).add(p);
        }
        return ret;
    }

    public static ArrayList<Product> GetRecommendProducts(Product cur, int num) {
        ArrayList<Product> ret = new ArrayList<>();
        int cnt = 0;
        for (Product p : categoryMap.get(cur.getCategory())) {
            ret.add(p);
            cnt++;
            if (cnt >= num) break;
        }
        return ret;
    }

    public static ArrayList<Integer> GetSearchProducts(String query) {
        String[] words = query.toLowerCase().split(" ");
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 0; i < productNames.size(); i++) {
            boolean found = true;
            Set<String> cur = productNames.get(i);
            for (String q : words) {
                if (!cur.contains(q)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                ret.add(i);
            }
        }
        return ret;
    }
};
