package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sev_user on 04/05/2018.
 */
@Entity
public class BangXepHangNgay_Model {
    private Long idsql;
    private String tenphim;
    private String tap;
    private String hinhanh;
    private String linkthongtinphim;
    private String nam;
    private String mota;

    private String luotxem; // khac nhung modek khac

    @Generated(hash = 1446890050)
    public BangXepHangNgay_Model(Long idsql, String tenphim, String tap,
            String hinhanh, String linkthongtinphim, String nam, String mota,
            String luotxem) {
        this.idsql = idsql;
        this.tenphim = tenphim;
        this.tap = tap;
        this.hinhanh = hinhanh;
        this.linkthongtinphim = linkthongtinphim;
        this.nam = nam;
        this.mota = mota;
        this.luotxem = luotxem;
    }

    @Generated(hash = 2007523598)
    public BangXepHangNgay_Model() {
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

    public String getLuotxem() {
        return this.luotxem;
    }

    public void setLuotxem(String luotxem) {
        this.luotxem = luotxem;
    }
}
