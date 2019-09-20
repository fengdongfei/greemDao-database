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
        MyOpenHelper mSQLiteOpenHelper = new MyOpenHelper(getApplicationContext(), "notes-db", null);
        DaoMaster mDaoMaster = new DaoMaster(mSQLiteOpenHelper.getWritableDatabase());
        daoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
