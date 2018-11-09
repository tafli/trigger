package com.example.sphincter;


import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


enum Action {
    open_door,
    close_door,
    update_state
}

public class SphincterRequestHandler extends AsyncTask<Action, Void, String> {

    private OnTaskCompleted listener;
    private SharedPreferences sharedPreferences;

    public SphincterRequestHandler(OnTaskCompleted l, SharedPreferences p){
        this.listener = l;
        this.sharedPreferences = p;
    }

    @Override
    protected String doInBackground(Action... params) {
        String url = sharedPreferences.getString("prefURL", "");

        if (url.isEmpty()) {
            return "";
        }

        switch (params[0]) {
            case open_door:
                url += "?action=open";
                break;
            case close_door:
                url += "?action=close";
                break;
            case update_state:
                url += "?action=state";
        }

        url += "&token=" + sharedPreferences.getString("prefToken", "");

        return CallSphincterAPI(url);
    }

    final String CallSphincterAPI(String urlstr) {
        try {
            // TODO: call on checkbox press
            if (sharedPreferences.getBoolean("prefIgnore", false)) {
                //HttpsTrustManager.allowAllSSL();
                HttpsTrustManager.setVerificationDisable();
            } else {
                HttpsTrustManager.setVerificationEnable();
            }

            URL url = new URL(urlstr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(2000);

            return readStream(con.getInputStream());
        } catch(FileNotFoundException e) {
            System.out.println("[URL-CALL] Server responds with error.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        String result ="";

        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    protected void onPostExecute(String result) {
        listener.onTaskCompleted(result.trim());
    }
}
