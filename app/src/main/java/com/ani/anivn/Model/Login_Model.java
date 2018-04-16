package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Admin on 4/16/2018.
 */

@Entity
public class Login_Model {
    @Id(autoincrement = true)
    private Long idsql;
    private String idfirebase;
    private String email;
    @Generated(hash = 37151470)
    public Login_Model(Long idsql, String idfirebase, String email) {
        this.idsql = idsql;
        this.idfirebase = idfirebase;
        this.email = email;
    }
    @Generated(hash = 1225402378)
    public Login_Model() {
    }
    public Long getIdsql() {
        return this.idsql;
    }
    public void setIdsql(Long idsql) {
        this.idsql = idsql;
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

}
