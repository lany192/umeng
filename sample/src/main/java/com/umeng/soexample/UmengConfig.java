package com.umeng.soexample;

/**
 * @author lyg
 */
public class UmengConfig {
    private String umengAppKey;
    private String channel;
    private String umengPushSecret;

    private String fileProvider;

    private String wxAppKey;
    private String wxSecret;

    private String qqAppKey;
    private String qqSecret;

    private String wbAppKey;
    private String wbSecret;

    public String getFileProvider() {
        return fileProvider;
    }

    public void setFileProvider(String fileProvider) {
        this.fileProvider = fileProvider;
    }

    public String getUmengAppKey() {
        return umengAppKey;
    }

    public void setUmeng(String umengAppKey, String channel, String umengPushSecret) {
        this.umengAppKey = umengAppKey;
        this.umengPushSecret = umengPushSecret;
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public String getUmengPushSecret() {
        return umengPushSecret;
    }

    public String getWxAppKey() {
        return wxAppKey;
    }

    public String getWxSecret() {
        return wxSecret;
    }

    public void setWeiXin(String wxAppKey, String wxSecret) {
        this.wxAppKey = wxAppKey;
        this.wxSecret = wxSecret;
    }

    public void setQq(String qqAppKey, String qqSecret) {
        this.qqAppKey = qqAppKey;
        this.qqSecret = qqSecret;
    }

    public String getQqSecret() {
        return qqSecret;
    }

    public String getQqAppKey() {
        return qqAppKey;
    }

    public void setWeibo(String wbAppKey, String wbSecret) {
        this.wbAppKey = wbAppKey;
        this.wbSecret = wbSecret;
    }

    public String getWbAppKey() {
        return wbAppKey;
    }

    public String getWbSecret() {
        return wbSecret;
    }
}