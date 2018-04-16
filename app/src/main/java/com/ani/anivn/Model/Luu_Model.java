package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sev_user on 04/06/2018.
 */

@Entity
public class Luu_Model {
    @Id(autoincrement = true)
    private Long idsql;
    private String TAG;
    private String trang_daluu;
    private int index_choose;
    @Generated(hash = 1603060883)
    public Luu_Model(Long idsql, String TAG, String trang_daluu, int index_choose) {
        this.idsql = idsql;
        this.TAG = TAG;
        this.trang_daluu = trang_daluu;
        this.index_choose = index_choose;
    }
    @Generated(hash = 1275389921)
    public Luu_Model() {
    }
    public Long getIdsql() {
        return this.idsql;
    }
    public void setIdsql(Long idsql) {
        this.idsql = idsql;
    }
    public String getTAG() {
        return this.TAG;
    }
    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
    public String getTrang_daluu() {
        return this.trang_daluu;
    }
    public void setTrang_daluu(String trang_daluu) {
        this.trang_daluu = trang_daluu;
    }
    public int getIndex_choose() {
        return this.index_choose;
    }
    public void setIndex_choose(int index_choose) {
        this.index_choose = index_choose;
    }
}
