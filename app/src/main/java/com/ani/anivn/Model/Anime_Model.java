package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sev_user on 04/06/2018.
 */
@Entity
public class Anime_Model {
    @Id(autoincrement = true)
    private Long idsql;
    private String tenphim;
    private String tap;
    private String hinhanh;
    private String linkthongtinphim;
    private String nam;
    private String mota;
    private String trang;
    private int index_list;
    @Generated(hash = 278447460)
    public Anime_Model(Long idsql, String tenphim, String tap, String hinhanh,
            String linkthongtinphim, String nam, String mota, String trang,
            int index_list) {
        this.idsql = idsql;
        this.tenphim = tenphim;
        this.tap = tap;
        this.hinhanh = hinhanh;
        this.linkthongtinphim = linkthongtinphim;
        this.nam = nam;
        this.mota = mota;
        this.trang = trang;
        this.index_list = index_list;
    }
    @Generated(hash = 1237360416)
    public Anime_Model() {
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
    public String getTrang() {
        return this.trang;
    }
    public void setTrang(String trang) {
        this.trang = trang;
    }
    public int getIndex_list() {
        return this.index_list;
    }
    public void setIndex_list(int index_list) {
        this.index_list = index_list;
    }
}
