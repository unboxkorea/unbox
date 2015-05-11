package com.example.bang.toeichelper.mydata;

/**
 * Created by BANG on 2015-02-23.
 */
public class USER_DATA {
    private String pk;
    private String Email;
    private String PW;
    private String Name;

    public USER_DATA() {

    }

    public USER_DATA(String pk, String Email, String PW, String Name) {
        this.pk = pk;
        this.Email = Email;
        this.PW = PW;
        this.Name = Name;
    }

    public String getPk() {
        return pk;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getPW() {
        return PW;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }
}
