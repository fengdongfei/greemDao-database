package net.cps.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.entity.greendao.CityEntityDao;
import net.cps.myapplication.entity.greendao.DaoMaster;
import net.cps.myapplication.entity.greendao.ProvinceEntityDao;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import org.greenrobot.greendao.database.Database;

// 版本升级解决数据丢失问题
// https://blog.csdn.net/qq_35956194/article/details/79167897
public class DBOpenHelper extends DaoMaster.OpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
 
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
 
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中

            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            }, UserEntityDao.class, AccountEntityDao.class,ProvinceEntityDao.class,CityEntityDao.class);
        }
}