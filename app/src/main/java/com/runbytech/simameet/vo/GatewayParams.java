package com.runbytech.simameet.vo;

/**
 * Created by liwenzhi on 14-9-24.
 */
public class GatewayParams {

    private String appServerIp;
    
    private int appServerPort;

    private String appToken;

    public String getAppServerIp() {
        return appServerIp;
    }

    public void setAppServerIp(String appServerIp) {
        this.appServerIp = appServerIp;
    }

    public int getAppServerPort() {
        return appServerPort;
    }

    public void setAppServerPort(int appServerPort) {
        this.appServerPort = appServerPort;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}