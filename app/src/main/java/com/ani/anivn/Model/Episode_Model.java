package com.ani.anivn.Model;

/**
 * Created by sev_user on 04/06/2018.
 */

public class Episode_Model {

    private String ten_tap;
    private String link_tap;

    public Episode_Model() {
    }

    public Episode_Model(String ten_tap, String link_tap) {
        this.ten_tap = ten_tap;
        this.link_tap = link_tap;
    }

    public String getTen_tap() {
        return ten_tap;
    }

    public void setTen_tap(String ten_tap) {
        this.ten_tap = ten_tap;
    }

    public String getLink_tap() {
        return link_tap;
    }

    public void setLink_tap(String link_tap) {
        this.link_tap = link_tap;
    }
}
