package com.nlh.minishoping.Connector;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ServerConnector {
    public static final  String HOST_NAME = "http://10.0.2.2:8000/";
    public static final String API_PATH = "api/";

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

        Log.i("RESULT", result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            String products = jsonObject.getString("products");
            Log.i("PRODUCTS", products);

            JSONArray jsonArray = new JSONArray(products);
            int[] arr = new int[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                arr[i] = jsonArray.getInt(i);
            }

            for (int i = 0; i < arr.length; i++) {
                Log.i("ARRAY " + i, String.valueOf(arr[i]));
            }

            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean RegisterOrLogin(String email) {
        String result = null;

        RegisterTask registerTask = new RegisterTask();
        registerTask.execute(email);

        try {
            result = registerTask.get();
            Log.i("RESULT_REGISTER", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        if (result == null) {
            return false;
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");
            Log.i("REGISTER_MESSAGE", message);

            if (message.equals("success")) {
                return true;
            }

            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
