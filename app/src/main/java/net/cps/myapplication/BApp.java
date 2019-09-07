package net.cps.myapplication;

import android.app.Application;

import net.cps.myapplication.entity.greendao.DaoMaster;
import net.cps.myapplication.entity.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class BApp extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置数据库
        //不要在生产中使用DaoMaster.DevOpenHelper它会删除所有关于模式更改的表（在onUpgrade（）中）。改为创建并使用自己的DaoMaster.OpenHelper子类
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");
        daoSession = new DaoMaster(db).newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
