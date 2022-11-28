package com.nlh.minishoping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.*;

public class DataHandler {
    private static HashMap<String, ArrayList<Product>> categoryMap = new HashMap<>();

    public static ArrayList<Product> GetProducts() {
        Path currentRelativePath = Paths.get("");
        ArrayList<Product> ret = new ArrayList<Product>();
        String json = "";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(WelcomeActivity.assetManager.open("data.json"), "UTF-8"));
        
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                json += mLine;
            }
        } catch (IOException e) {
            // Log.d("meow", "GetProducts: " + e.getMessage());
        } finally {
            if (reader != null) {
                 try {
                     reader.close();
                 } catch (IOException e) {
                     //log the exception
                 }
            }
        }

        // Log.d("meow", "GetProducts: " + json);

        try {
            JSONArray jsonArray = new JSONArray(json);
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Product p : ret) {
            if (!categoryMap.containsKey(p.getCategory())) {
                categoryMap.put(p.getCategory(), new ArrayList<Product>());
            }
            categoryMap.get(p.getCategory()).add(p);
        }
        return ret;
    }

    public static ArrayList<Product> GetRecommendProducts(Product cur, int num){
        ArrayList<Product> ret = new ArrayList<Product>();
        int cnt = 0;
        for (Product p : categoryMap.get(cur.getCategory())) {
            ret.add(p);
            cnt++;
            if (cnt >= num) break;
        }
        return ret;
    }
};
