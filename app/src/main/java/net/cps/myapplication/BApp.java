package net.cps.myapplication;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.cps.myapplication.entity.greendao.DaoMaster;
import net.cps.myapplication.entity.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.IOException;

public class BApp extends Application {

    private DaoSession daoSession;
    // 是否加密数据库
    public static final boolean ENCRYPTED = true;
    private static DBOpenHelper mSQLiteOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
         mSQLiteOpenHelper = new DBOpenHelper(this,  ENCRYPTED ? Contents.DB_NAME_ENCRIPT : Contents.DB_NAME, null);
        Database db = ENCRYPTED ? mSQLiteOpenHelper.getEncryptedWritableDb(Contents.DB_KEY) : mSQLiteOpenHelper.getWritableDb();
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

    // 清空数据库
    public static void deleSQL(){
        SQLiteDatabase db=mSQLiteOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoMaster.dropAllTables(daoMaster.getDatabase(),true);
        DaoMaster.createAllTables(daoMaster.getDatabase(),true);
    }

}
