package com.nlh.minishoping.Connector;

import com.nlh.minishoping.Cart.CartMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ServerConnector {
    public static final String HOST_NAME = "http://10.0.2.2:8000/";
    public static final String API_PATH = "api/";

    private static final String PRODUCTS_KEY = "products";

    public static int[] GetSearchResults(String name) {
        String result;

        SearchTask searchTask = new SearchTask();
        searchTask.execute(name);

        try {
            result = searchTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return getArraysFromJson(result, PRODUCTS_KEY);
    }

    public static String RegisterOrLogin(String email) {

        RegisterTask registerTask = new RegisterTask();
        registerTask.execute(email);

        String result;
        try {
            result = registerTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        if (result == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");

            if (message.equals("success")) {
                String stringData = jsonObject.getString("data");

                JSONObject dataJsonObject = new JSONObject(stringData);

                return dataJsonObject.getString("hash");
            }

            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int AddProductToFavorite(String hash, int productID, boolean already) {
        String result;

        FavoriteAddingTask favoriteAddingTask = new FavoriteAddingTask();
        favoriteAddingTask.execute(hash, String.valueOf(productID), String.valueOf(already));

        try {
            result = favoriteAddingTask.get();

            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");
            if (message.equals("Product added successfully") || message.equals("Product removed successfully")) {
                return 0;
            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
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
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return getArraysFromJson(result, "favorites");
    }

    public static CartMap.Pair<Float, Float> getLocation(String hash) throws ExecutionException, InterruptedException, JSONException {
        GetLocationTask getLocationTask = new GetLocationTask();
        getLocationTask.execute(hash);
        String result = getLocationTask.get();
        if (result == null) return null;
        JSONObject jsonObject = new JSONObject(result);
        JSONObject loc = jsonObject.getJSONObject("location");
        float lat = (float) loc.getDouble("lat");
        float lng = (float) loc.getDouble("lng");
        return new CartMap.Pair<>(lat, lng);
    }

    public static void updateLocation(String hash, float lat, float lng) throws JSONException, ExecutionException, InterruptedException {
        JSONObject jsonObject = new JSONObject();
        JSONObject loc = new JSONObject();
        loc.put("lat", lat);
        loc.put("lng", lng);
        jsonObject.put("hash", hash);
        jsonObject.put("location", loc);

        SetLocationTask setLocationTask = new SetLocationTask();
        setLocationTask.execute(jsonObject.toString());
        setLocationTask.get();
    }

    private static int[] getArraysFromJson(String result, String key) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String stringArrays = jsonObject.getString(key);

            JSONArray jsonArray = new JSONArray(stringArrays);
            int[] arr = new int[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                arr[i] = jsonArray.getInt(i);
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
        } catch (ExecutionException | InterruptedException e) {
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

    public static void Purchase(String str) {

        PurchaseTask purchaseTask = new PurchaseTask();
        purchaseTask.execute(str);

        try {
            purchaseTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
