package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sev_user on 04/05/2018.
 */

@Entity
public class PhimMoiCapNhat_Model {
    @Id(autoincrement = true)
    private Long idsql;
    private String tenphim;
    private String tap;
    private String hinhanh;
    private String linkthongtinphim;
    private String nam;
    private String mota;
    @Generated(hash = 53306226)
    public PhimMoiCapNhat_Model(Long idsql, String tenphim, String tap,
            String hinhanh, String linkthongtinphim, String nam, String mota) {
        this.idsql = idsql;
        this.tenphim = tenphim;
        this.tap = tap;
        this.hinhanh = hinhanh;
        this.linkthongtinphim = linkthongtinphim;
        this.nam = nam;
        this.mota = mota;
    }
    @Generated(hash = 2079806681)
    public PhimMoiCapNhat_Model() {
    }
    public Long getIdsql() {
        return this.idsql;
    }
    public void setIdsql(Long idsql) {
        this.idsql = idsql;
    }
    public String getTenphim() {
        return this.tenphim;
    }
    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }
    public String getTap() {
        return this.tap;
    }
    public void setTap(String tap) {
        this.tap = tap;
    }
    public String getHinhanh() {
        return this.hinhanh;
    }
    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
    public String getLinkthongtinphim() {
        return this.linkthongtinphim;
    }
    public void setLinkthongtinphim(String linkthongtinphim) {
        this.linkthongtinphim = linkthongtinphim;
    }
    public String getNam() {
        return this.nam;
    }
    public void setNam(String nam) {
        this.nam = nam;
    }
    public String getMota() {
        return this.mota;
    }
    public void setMota(String mota) {
        this.mota = mota;
    }

}
