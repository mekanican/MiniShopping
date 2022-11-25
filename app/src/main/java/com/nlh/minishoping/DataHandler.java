package com.nlh.minishoping;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.*;

public class DataHandler {
    public static ArrayList<Product> GetProducts() {
        Path currentRelativePath = Paths.get("");
        ArrayList<Product> ret = new ArrayList<Product>();
        String json = "";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                new InputStreamReader(MainActivity.assetManager.open("data.json"), "UTF-8"));
        
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                json += mLine;
            }
        } catch (IOException e) {
            Log.d("meow", "GetProducts: " + e.getMessage());
        } finally {
            if (reader != null) {
                 try {
                     reader.close();
                 } catch (IOException e) {
                     //log the exception
                 }
            }
        }

        Log.d("meow", "GetProducts: " + json);

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                ret.add(new Product(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("image"),
                    (int) Math.round(obj.getDouble("price"))
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
};
