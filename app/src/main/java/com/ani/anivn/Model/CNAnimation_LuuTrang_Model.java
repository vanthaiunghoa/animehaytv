package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sev_user on 04/06/2018.
 */

@Entity
public class CNAnimation_LuuTrang_Model {
    @Id(autoincrement = true)
    private Long idsql;
    private String trang; // vi du cac trang 1 2 3 4 5 6 
    private String tranghientai;  // vi du trang 1
    private int index_list;
    @Generated(hash = 1146886805)
    public CNAnimation_LuuTrang_Model(Long idsql, String trang, String tranghientai,
            int index_list) {
        this.idsql = idsql;
        this.trang = trang;
        this.tranghientai = tranghientai;
        this.index_list = index_list;
    }
    @Generated(hash = 1714586030)
    public CNAnimation_LuuTrang_Model() {
    }
    public Long getIdsql() {
        return this.idsql;
    }
    public void setIdsql(Long idsql) {
        this.idsql = idsql;
    }
    public String getTrang() {
        return this.trang;
    }
    public void setTrang(String trang) {
        this.trang = trang;
    }
    public String getTranghientai() {
        return this.tranghientai;
    }
    public void setTranghientai(String tranghientai) {
        this.tranghientai = tranghientai;
    }
    public int getIndex_list() {
        return this.index_list;
    }
    public void setIndex_list(int index_list) {
        this.index_list = index_list;
    }

}
