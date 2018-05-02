package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Luu_TimKiem_Model {
    @Id(autoincrement = true)
    private Long idsql;
    private String tag;
    private String keyword;
    private boolean ischecked;
    @Generated(hash = 1454614638)
    public Luu_TimKiem_Model(Long idsql, String tag, String keyword,
            boolean ischecked) {
        this.idsql = idsql;
        this.tag = tag;
        this.keyword = keyword;
        this.ischecked = ischecked;
    }
    @Generated(hash = 942464159)
    public Luu_TimKiem_Model() {
    }
    public Long getIdsql() {
        return this.idsql;
    }
    public void setIdsql(Long idsql) {
        this.idsql = idsql;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public boolean getIschecked() {
        return this.ischecked;
    }
    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
