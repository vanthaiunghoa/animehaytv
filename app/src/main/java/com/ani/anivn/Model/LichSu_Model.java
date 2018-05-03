
package com.ani.anivn.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LichSu_Model {

    @Id(autoincrement = true)
    private Long idsql;
    private String tenphim;
    private String tap;
    private String hinhanh;
    private String linkthongtinphim;
    private String nam;
    private String mota;
    private String listtapdaxem;
   private Date updatedAt;
   
}
