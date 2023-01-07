package com.nlh.minishoping.Connector;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ServerConnector {
    public static final  String HOST_NAME = "http://10.0.2.2:8000/";
    public static final String API_PATH = "api/";

    private static final String PRODUCTS_KEY = "products";

    public static int[] GetSearchResults(String name) {
        String result = null;

        SearchTask searchTask = new SearchTask();
        searchTask.execute(name);

        try {
            result = searchTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        // Log.i("RESULT", result);

        int[] arr = getArraysFromJson(result, PRODUCTS_KEY);

        if (arr == null) {
            return null;
        }

        return arr;
    }

    public static String RegisterOrLogin(String email) {
        String result = null;

        RegisterTask registerTask = new RegisterTask();
        registerTask.execute(email);

        try {
            result = registerTask.get();
            // Log.i("RESULT_REGISTER", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        if (result == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");
            Log.i("REGISTER_MESSAGE", message);

            if (message.equals("success")) {
                String stringData = jsonObject.getString("data");
                Log.i("REGISTER_DATA", stringData);

                JSONObject dataJsonObject = new JSONObject(stringData);
                String hash = dataJsonObject.getString("hash");
                Log.i("REGISTER_HASH", hash);

                return hash;
            }

            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[] GetProductsByCategory(String category) {
        String result = null;

        CategoryTask categoryTask = new CategoryTask();
        categoryTask.execute(category);

        try {
            result = categoryTask.get();
            // Log.i("CATEGORY RESULT", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] arr = getArraysFromJson(result, PRODUCTS_KEY);

        return arr;
    }

    public static int AddProductToFavorite(String hash, int productID, boolean already) {
        String result;

        FavoriteAddingTask favoriteAddingTask = new FavoriteAddingTask();
        favoriteAddingTask.execute(hash, String.valueOf(productID), String.valueOf(already));

        Log.i("FAVORITE HASH", hash);
        Log.i("FAVORITE ID", String.valueOf(productID));

        try {
            result = favoriteAddingTask.get();
            Log.i("FAVORITE RESULT", result);

            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");
            Log.i("FAVORITE MESSAGE", message);
            if (message.equals("Product added successfully") || message.equals("Product removed successfully")) {
                return 0;
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int[] GetFavoriteList(String hash) {
        String result = null;

        FavoriteGettingTask favoriteGettingTask = new FavoriteGettingTask();
        favoriteGettingTask.execute(hash);

        try {
            result = favoriteGettingTask.get();
            Log.i("FAVORITE GETTING RESULT", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] arr = getArraysFromJson(result, "favorites");

        return arr;
    }

    private static int[] getArraysFromJson(String result, String key) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String stringArrays = jsonObject.getString(key);
            // Log.i("PRODUCTS", stringArrays);

            JSONArray jsonArray = new JSONArray(stringArrays);
            int[] arr = new int[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                arr[i] = jsonArray.getInt(i);
            }

            for (int i = 0; i < arr.length; i++) {
                // Log.i("ARRAY " + i, String.valueOf(arr[i]));
            }

            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double GetVoucherDiscount(String voucher) {
        String strResult = null;

        VoucherTask voucherTask = new VoucherTask();
        voucherTask.execute(voucher);

        try {
            strResult = voucherTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double result = 0;

        if (strResult == null) {
            return result;
        }

        try {
            JSONObject jsonObject = new JSONObject(strResult);
            result = jsonObject.getDouble("discount");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
