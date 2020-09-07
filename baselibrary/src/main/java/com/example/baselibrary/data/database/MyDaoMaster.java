package com.example.baselibrary.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.baselibrary.utils.LogUtils;
import com.example.kotlinmvpdemo.DaoMaster;
import com.example.kotlinmvpdemo.UserDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import org.greenrobot.greendao.database.Database;

/**
 * 自定义DaoMaster，为数据库升级
 */
public class MyDaoMaster extends DaoMaster.OpenHelper {

    public MyDaoMaster(Context context, String name) {
        super(context, name);
    }

    public MyDaoMaster(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, UserDao.class);
        LogUtils.i("MyDaoMaster", "onUpgrade: " + oldVersion, " newVersion = " + newVersion);
    }
}
