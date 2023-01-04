package com.nlh.minishoping.Connector;

import static com.nlh.minishoping.Connector.ServerConnector.API_PATH;
import static com.nlh.minishoping.Connector.ServerConnector.HOST_NAME;

import static java.net.HttpURLConnection.HTTP_OK;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterTask extends AsyncTask<String, Void, String> {
    protected String doInBackground(String... email) {
        String apiURL = HOST_NAME + API_PATH + "user";
        Log.i("FUNCTION", "REGISTERTASK_DOINBACKGROUND");

        try {
            URL url = new URL(apiURL);
            Log.i("URL", url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Set the content type of the request
            conn.setRequestProperty("Content-Type", "application/json");

            String requestBody = "{\n \"email\": \"" + email[0] + "\" \n}";
            Log.i("REQUEST BODY", requestBody);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

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

                Log.i("RESPONSE_REGISTER", String.valueOf(response));

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
