package com.vatsyayan.huntformoview.web_service;



public interface ServiceCallBack {

    void success(ServiceHandler.RequestId serviceName, Object data);

    void error(ServiceHandler.RequestId serviceName, String message);

}