package net.cps.myapplication;

import android.app.Application;

import net.cps.myapplication.entity.greendao.DaoMaster;
import net.cps.myapplication.entity.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class BApp extends Application {

    private DaoSession daoSession;
    public static final boolean ENCRYPTED = true;

    @Override
    public void onCreate() {
        super.onCreate();
        DBOpenHelper mSQLiteOpenHelper = new DBOpenHelper(this,  ENCRYPTED ? "notes-encrypted.db" : "notes.db", null);
        Database db = ENCRYPTED ? mSQLiteOpenHelper.getEncryptedWritableDb("<password1!feifei>") : mSQLiteOpenHelper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    private static BApp INSTANCE;

    public static BApp getInstance() {
        return INSTANCE;
    }

    public static String getDBName(){
        return  ENCRYPTED ? "notes-encrypted.db" : "notes.db";
    }
}
