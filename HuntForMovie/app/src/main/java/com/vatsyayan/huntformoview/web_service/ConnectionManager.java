package com.vatsyayan.huntformoview.web_service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.vatsyayan.huntformoview.web_service.ServiceHandler.*;


public class ConnectionManager {


    public HttpURLConnection httpURLConnection = null;
    public static ConnectionManager connectionManager;

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public HttpURLConnection getConnection(String uri, int method) {

        try {
            URL url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            switch (method) {
                case REQUEST_METHOD_GET:
                    httpURLConnection.setRequestMethod("GET");
                    break;
                case REQUEST_METHOD_POST:
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    break;
                case REQUEST_METHOD_PUT:
                    httpURLConnection.setRequestMethod("PUT");
                    break;
            }

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            return httpURLConnection;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return httpURLConnection;
    }
}
