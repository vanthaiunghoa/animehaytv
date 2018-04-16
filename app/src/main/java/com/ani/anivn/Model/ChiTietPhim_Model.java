package com.ani.anivn.Model;

/**
 * Created by sev_user on 04/05/2018.
 */

public class ChiTietPhim_Model {

    private String tenphim;
    private String linkphim;
    private String hinhanh;
    private String anhnen;
    private String namphathanh;
    private String theloai;
    private String thoiluong;
    private String mota;

    public ChiTietPhim_Model( String tenphim, String linkphim,
            String hinhanh, String anhnen, String namphathanh, String theloai,
            String thoiluong, String mota) {

        this.tenphim = tenphim;
        this.linkphim = linkphim;
        this.hinhanh = hinhanh;
        this.anhnen = anhnen;
        this.namphathanh = namphathanh;
        this.theloai = theloai;
        this.thoiluong = thoiluong;
        this.mota = mota;
    }

    public ChiTietPhim_Model() {
    }

    public String getTenphim() {
        return this.tenphim;
    }
    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }
    public String getLinkphim() {
        return this.linkphim;
    }
    public void setLinkphim(String linkphim) {
        this.linkphim = linkphim;
    }
    public String getHinhanh() {
        return this.hinhanh;
    }
    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
    public String getAnhnen() {
        return this.anhnen;
    }
    public void setAnhnen(String anhnen) {
        this.anhnen = anhnen;
    }
    public String getNamphathanh() {
        return this.namphathanh;
    }
    public void setNamphathanh(String namphathanh) {
        this.namphathanh = namphathanh;
    }
    public String getTheloai() {
        return this.theloai;
    }
    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }
    public String getThoiluong() {
        return this.thoiluong;
    }
    public void setThoiluong(String thoiluong) {
        this.thoiluong = thoiluong;
    }
    public String getMota() {
        return this.mota;
    }
    public void setMota(String mota) {
        this.mota = mota;
    }
}
