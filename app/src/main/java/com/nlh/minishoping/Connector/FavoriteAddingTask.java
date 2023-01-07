package com.nlh.minishoping.Connector;

import static com.nlh.minishoping.Connector.ServerConnector.API_PATH;
import static com.nlh.minishoping.Connector.ServerConnector.HOST_NAME;
import static java.net.HttpURLConnection.HTTP_OK;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("deprecation")
public class FavoriteAddingTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... values) {
        String apiURL = HOST_NAME + API_PATH + "favorite";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Set the content type of the request
            conn.setRequestProperty("Content-Type", "application/json");
            String already = values[2];
            String requestBody;
            if (already.equals("true")) {
                requestBody = "{\n \"hash\": \"" + values[0] + "\", \n\"product\":" + values[1] + ", \n\"action\": \"remove\"" + "\n}";
            } else {
                requestBody = "{\n \"hash\": \"" + values[0] + "\", \n\"product\":" + values[1] + ", \n\"action\": \"add\"" + "\n}";
            }

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HTTP_OK) {
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
