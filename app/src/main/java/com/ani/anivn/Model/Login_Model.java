package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Admin on 4/16/2018.
 */

public class Login_Model {

    private String idfirebase;
    private String email;
    private String password;
  
    public Login_Model(String idfirebase, String email,String password) {
       this.idfirebase = idfirebase;
        this.email = email;
        this.password=password;
    }
  
    public Login_Model() {
    }

    public String getIdfirebase() {
        return this.idfirebase;
    }
    public void setIdfirebase(String idfirebase) {
        this.idfirebase = idfirebase;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
     public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
