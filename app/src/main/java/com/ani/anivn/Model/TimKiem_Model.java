package com.ani.anivn.Model;

/**
 * Created by Admin on 4/7/2018.
 */

public class TimKiem_Model {
    private String tenphim;
    private String tap;
    private String hinhanh;
    private String linkthongtinphim;
    private String nam;
    private String mota;

    public TimKiem_Model() {
    }

    public TimKiem_Model(String tenphim, String tap, String hinhanh, String linkthongtinphim, String nam, String mota) {
        this.tenphim = tenphim;
        this.tap = tap;
        this.hinhanh = hinhanh;
        this.linkthongtinphim = linkthongtinphim;
        this.nam = nam;
        this.mota = mota;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getTap() {
        return tap;
    }

    public void setTap(String tap) {
        this.tap = tap;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getLinkthongtinphim() {
        return linkthongtinphim;
    }

    public void setLinkthongtinphim(String linkthongtinphim) {
        this.linkthongtinphim = linkthongtinphim;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
