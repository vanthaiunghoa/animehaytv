package com.ani.anivn.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ani.anivn.Model.DaoMaster;

/**
 * Created by sev_user on 04/02/2018.
 */

public class DbOpenHelper extends DaoMaster.OpenHelper {
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        switch (oldVersion) {
            case 1:
                //         sql ver 2 thay doi j thy viet vao day
//                Log.d("TAG"," 1 ADD COLUMN");
//                db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN " + UserDao.Properties.Age.columnName + " INTEGER ");
                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " ANIME__LUU_TRANG__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TRANG TEXT," + // 1: trang
                        " TRANGHIENTAI TEXT," + // 2: tranghientai
                        " INDEX_LIST INTEGER NOT NULL );"); // 3: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " ANIME__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM TEXT," + // 4: linkthongtinphim
                        " NAM TEXT," + // 5: nam
                        " MOTA TEXT," + // 6: mota
                        " TRANG TEXT," + // 7: trang
                        " INDEX_LIST INTEGER NOT NULL );"); // 8: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " BANG_XEP_HANG_NGAY__MODEL (" + //
                        " IDSQL INTEGER," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM TEXT," + // 4: linkthongtinphim
                        " NAM TEXT," + // 5: nam
                        " MOTA TEXT," + // 6: mota
                        " LUOTXEM TEXT);"); // 7: luotxem

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " CNANIMATION__LUU_TRANG__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TRANG TEXT," + // 1: trang
                        " TRANGHIENTAI TEXT," + // 2: tranghientai
                        " INDEX_LIST INTEGER NOT NULL );"); // 3: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " CNANIMATION__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM TEXT," + // 4: linkthongtinphim
                        " NAM TEXT," + // 5: nam
                        " MOTA TEXT," + // 6: mota
                        " TRANG TEXT," + // 7: trang
                        " INDEX_LIST INTEGER NOT NULL );"); // 8: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " HOAT_HINH__LUU_TRANG__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TRANG TEXT," + // 1: trang
                        " TRANGHIENTAI TEXT," + // 2: tranghientai
                        " INDEX_LIST INTEGER NOT NULL );"); // 3: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " HOAT_HINH__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM TEXT," + // 4: linkthongtinphim
                        " NAM TEXT," + // 5: nam
                        " MOTA TEXT," + // 6: mota
                        " TRANG TEXT," + // 7: trang
                        " INDEX_LIST INTEGER NOT NULL );"); // 8: index_list
                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " LUU__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TAG TEXT," + // 1: TAG
                        " TRANG_DALUU TEXT," + // 2: trang_daluu
                        " INDEX_CHOOSE INTEGER NOT NULL );"); // 3: index_choose

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " NAM_PHAT_HANH__LUU_TRANG__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TRANG TEXT," + // 1: trang
                        " TRANGHIENTAI TEXT," + // 2: tranghientai
                        " INDEX_LIST INTEGER NOT NULL );"); // 3: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " NAM_PHAT_HANH__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM TEXT," + // 4: linkthongtinphim
                        " NAM TEXT," + // 5: nam
                        " MOTA TEXT," + // 6: mota
                        " TRANG TEXT," + // 7: trang
                        " INDEX_LIST INTEGER NOT NULL );"); // 8: index_list

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " PHIM_MOI_CAP_NHAT__MODEL (" + //
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM TEXT," + // 4: linkthongtinphim
                        " NAM TEXT," + // 5: nam
                        " MOTA TEXT);"); // 6: mota

                db.execSQL("CREATE TABLE " + " IF NOT EXISTS " + " YEU_THICH__MODEL (" + //
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idsql
                        " TENPHIM TEXT," + // 1: tenphim
                        " TAP TEXT," + // 2: tap
                        " HINHANH  TEXT," + // 3: hinhanh
                        " LINKTHONGTINPHIM  TEXT," + // 4: linkthongtinphim
                        " NAM  TEXT," + // 5: nam
                        " MOTA  TEXT);"); // 6: mota

            case 2:
                // Log.d("TAG"," 2 ADD COLUMN");

             
          //  case 3:
//                Log.d("TAG","3 ADD COLUMN");
//            case 4:
//                Log.d("TAG"," 4 ADD COLUMN");
//
//            case 5:
//                Log.d("TAG"," 5 ADD COLUMN");
//            case 6:
//                Log.d("TAG"," 6 ADD COLUMN");
//            case 7:
//                Log.d("TAG"," 7 ADD COLUMN");
//            case 8:
//                Log.d("TAG"," 8 ADD COLUMN");

        }
    }
}
