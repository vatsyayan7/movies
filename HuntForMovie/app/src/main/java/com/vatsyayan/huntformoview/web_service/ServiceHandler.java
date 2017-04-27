package com.vatsyayan.huntformoview.web_service;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServiceHandler {
    public static final int REQUEST_METHOD_GET = 1;
    public static final int REQUEST_METHOD_POST = 2;
    public static final int REQUEST_METHOD_PUT = 3;

    private Context mContext;
    private ServiceCallBack mServiceCallBack;
    private Map<String,Object> mParameters = new HashMap<>();
    private RequestId mRequestId;
    private int mRequestType;
    private String mURI;
    private ServiceCaller mServiceCaller;
    private JSONObject mJson = new JSONObject();

    public enum RequestId{
        FETCH_MOVIES
    }
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setmServiceCallBack(ServiceCallBack mServiceCallBack) {
        this.mServiceCallBack = mServiceCallBack;
    }

    public void addParameters(String key,Object value){
        mParameters.put(key,value);
    }

    public void setmRequestId(RequestId mRequestId) {
        this.mRequestId = mRequestId;
    }

    public void makeRequest(int pRequestType){
        mRequestType = pRequestType;
        createURI();
        mServiceCaller = new ServiceCaller();
        switch (pRequestType){
            case REQUEST_METHOD_GET:
                getJSONFromMap();
                makeGetRequest();
                break;
            case REQUEST_METHOD_POST:
            case REQUEST_METHOD_PUT:
                getJSONFromMap();
                makePostRequest();
                break;
        }
        mServiceCaller.execute(mRequestType);
    }

    private void getJSONFromMap() {
        try{
            if(mParameters != null){
                Set<Map.Entry<String,Object>> entrySet = mParameters.entrySet();
                for (Map.Entry<String,Object> entry : entrySet){
                    mJson.put(entry.getKey(), entry.getValue());
                }
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }


    private void createURI() {

        String baseURL = "https://api.themoviedb.org/3/search/";
        String specificURI = "";

        switch (mRequestId) {
            case FETCH_MOVIES:
                specificURI = "movie";
                break;
            default:
                break;
        }

        mURI = baseURL + specificURI;
    }

    private void makeGetRequest() {
        /*if(mParameters != null){
            mURI += "?" + getStringFromMap();
        }
        try{
            String encodedURL = URLEncoder.encode(mURI,"UTF-8");
            String decodeURL = URLDecoder.decode(encodedURL, "UTF-8");
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }*/
        mServiceCaller.setParams(mContext,mURI,this,getStringFromMap());
    }

    private void makePostRequest() {
        mServiceCaller.setParams(mContext,mURI,this,mJson);
    }

    private String getStringFromMap() {
        StringBuffer parameterBuffer = new StringBuffer();
        if(mParameters != null){
            Set<Map.Entry<String,Object>> entrySet = mParameters.entrySet();
            for(Map.Entry<String,Object> entry :entrySet){
                parameterBuffer.append(entry.getKey() + "=" +entry.getValue()).append("&");
            }
        }
        return parameterBuffer.substring(0,parameterBuffer.length() - 1);
    }

    public void processResponse(String result, boolean isConnectionSuccessfull){
        if(isConnectionSuccessfull){
            if(result == null){
                LoadingScreen.dismissProgressDialog();
                mServiceCallBack.error(mRequestId,"Something went wrong on server side");
            }else {
                mServiceCallBack.success(mRequestId,result);
            }
        }else {
            mServiceCallBack.error(mRequestId,"Could not make connection");
        }
    }
}
