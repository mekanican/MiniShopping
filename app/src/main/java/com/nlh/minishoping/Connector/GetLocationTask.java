package com.nlh.minishoping.Connector;

import static com.nlh.minishoping.Connector.ServerConnector.API_PATH;
import static com.nlh.minishoping.Connector.ServerConnector.HOST_NAME;
import static java.net.HttpURLConnection.HTTP_OK;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetLocationTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... hash) {
        String apiURL = HOST_NAME + API_PATH + "location/" + hash[0];

        URL url = null;
        try {
            url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();

            if (responseCode == HTTP_OK) {
                // get the response body
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                Log.i("FAVORITE GETTING RESPONSE", String.valueOf(response));

                return response.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}