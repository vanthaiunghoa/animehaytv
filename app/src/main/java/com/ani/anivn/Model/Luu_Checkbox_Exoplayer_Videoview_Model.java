package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Luu_Checkbox_Exoplayer_Videoview_Model {

    @Id(autoincrement = true)
    private Long idsql;
    private String tag;
    private boolean isChecked;
    @Generated(hash = 755415928)
    public Luu_Checkbox_Exoplayer_Videoview_Model(Long idsql, String tag,
            boolean isChecked) {
        this.idsql = idsql;
        this.tag = tag;
        this.isChecked = isChecked;
    }
    @Generated(hash = 2045745461)
    public Luu_Checkbox_Exoplayer_Videoview_Model() {
    }
    public Long getIdsql() {
        return this.idsql;
    }
    public void setIdsql(Long idsql) {
        this.idsql = idsql;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public boolean getIsChecked() {
        return this.isChecked;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
   
}
