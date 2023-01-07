package com.nlh.minishoping.Connector;

import static com.nlh.minishoping.Connector.ServerConnector.API_PATH;
import static com.nlh.minishoping.Connector.ServerConnector.HOST_NAME;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("deprecation")
public class SearchTask extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... searchTerms) {
        String searchURL = HOST_NAME + API_PATH + "product?type=search&query=" + searchTerms[0];

        try {
            URL url = new URL(searchURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // get the response body
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                return response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
