package com.example.bang.toeichelper.mydata;

/**
 * Created by BANG on 2015-02-11.
 */
public class MEMBER {

    private String pk;
    private String strEmail;
    private String strName;
    private String strPW;

    public MEMBER() {
    }

    public MEMBER(String pk, String strEmail, String strName, String strPW){
        this.pk = pk;
        this.strEmail = strEmail;
        this.strName = strName;
        this.strPW = strPW;
    }

    public String getPk() {
        return pk;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public String getStrName() {
        return strName;
    }

    public String getStrPW() {
        return strPW;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public void setStrPW(String strPW) {
        this.strPW = strPW;
    }
}
