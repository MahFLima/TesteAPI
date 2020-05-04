package com.example.testeapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteGeoIP {
    private static String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        finally {
            urlConnection.disconnect();
        }
    }


    public static Localizacao localizar(String ip) throws JSONException, IOException {
        String resposta = request("http://api.ipstack.com/" + ip + "?access_key=f44416291d18da09c5dc6c5a76692003&format=1");
        JSONObject obj = new JSONObject(resposta);
        String code = obj.getString("country_code");
        String name = obj.getString("country_name");
        return new Localizacao(code, name);
    }
}
