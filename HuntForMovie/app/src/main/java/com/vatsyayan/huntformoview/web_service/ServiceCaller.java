package com.vatsyayan.huntformoview.web_service;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;


public class ServiceCaller extends AsyncTask<Integer,Integer,String> {

    private HttpURLConnection mHttpURLConnection = null;
    private ServiceHandler mServiceHandler = null;
    private String mURI;
    private Context mContext;
    private JSONObject mJsonObject;
    private boolean isConnectionSuccessful = true;
    private String mData;


    public void setParams(Context pContext, String pURI, ServiceHandler pServiceHandler, JSONObject pJsonObject){
        mContext = pContext;
        mURI = pURI;
        mServiceHandler = pServiceHandler;
        mJsonObject = pJsonObject;
    }

    public void setParams(Context pContext, String pURI, ServiceHandler pServiceHandler, String pData){
        mContext = pContext;
        mURI = pURI;
        mServiceHandler = pServiceHandler;
        mData = pData;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer[] params) {
        String result = makeServiceCall(params[0]);
        return result;
    }

    private String makeServiceCall(int requestMethodType) {
        String result = null;
        HttpURLConnection connection = null;
        try{
            StringBuilder stringBuilder = new StringBuilder(mURI);
            stringBuilder.append("?");
            stringBuilder.append(mData);
            connection = ConnectionManager.getInstance().getConnection(stringBuilder.toString(),requestMethodType);
            switch (requestMethodType){
                case ServiceHandler.REQUEST_METHOD_POST:
                case ServiceHandler.REQUEST_METHOD_PUT:

                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(mJsonObject.toString());
                    out.close();
                    break;
            }

            int httpResult = connection.getResponseCode();

            if(httpResult == HttpURLConnection.HTTP_ACCEPTED){
                isConnectionSuccessful = true;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                //result = br.readLine();
                result = connection.getHeaderField("returnMessage");
                br.close();
            }else if(httpResult == HttpURLConnection.HTTP_OK){
                isConnectionSuccessful = true;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                result = br.readLine();
                br.close();
            } else{
                System.out.println(connection.getResponseMessage());
            }
        }catch (SocketTimeoutException ex){
            isConnectionSuccessful = false;
        }catch (IOException ex){
            ex.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mServiceHandler.processResponse(result,isConnectionSuccessful);
    }
}
