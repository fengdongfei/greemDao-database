package net.cps.myapplication;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.entity.greendao.CityEntityDao;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.ProvinceEntityDao;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.model.AccountEntity;
import net.cps.myapplication.model.BackupBean;
import net.cps.myapplication.model.CityEntity;
import net.cps.myapplication.model.ProvinceEntity;
import net.cps.myapplication.model.UserEntity;
import net.cps.myapplication.ui.AddDataActivity;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private UserEntityDao userDao;
    private DaoSession daoSession;
    private AccountEntityDao accountDao;
    public int limit=2;
    public int page=0;
    private ProvinceEntityDao provinceDao;
    private CityEntityDao cityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDao();
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddDataActivity.class));
            }
        });

        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoSession.clear();
                List<UserEntity> joes1 = userDao.queryBuilder().list();
                List<ProvinceEntity> joes2 =provinceDao .queryBuilder().list();
                List<CityEntity> joes3 = cityDao.queryBuilder().list();
                List<AccountEntity> joes4 = accountDao.queryBuilder().list();
                Log.d("查询用户信息", ": "+ JSON.toJSONString(joes1,true));
            }
        });

        findViewById(R.id.bt_find_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询用户信息; 查询名字为“name_01”的所有用户，按年纪排序
                List<UserEntity> joes = userDao.queryBuilder()
                        .where(UserEntityDao.Properties.Name.eq("name_02"))
                        .orderAsc(UserEntityDao.Properties.Age)
                        .list();
                // 查询 age>22 && (isvip==true || isbuy_zone==true) && (name=="name_02" && Isbuy_limit_ads==true )
                QueryBuilder<UserEntity> qb = userDao.queryBuilder();
                qb.where(UserEntityDao.Properties.Age.gt(22),
                        qb.or(UserEntityDao.Properties.Isvip.eq(true),UserEntityDao.Properties.Isbuy_zone.eq(true)),
                        qb.and(UserEntityDao.Properties.Name.eq("name_02"), UserEntityDao.Properties.Isbuy_limit_ads.eq(true)));
                List<UserEntity> youngJoes = qb.list();

                Log.d("查询用户信息", ": "+ Arrays.toString(youngJoes.toArray()));
            }
        });
        findViewById(R.id.bt_px_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询 age>22 && (isvip==true || isbuy_zone==true)
                QueryBuilder<UserEntity> qb = userDao.queryBuilder();
                qb.orderDesc(UserEntityDao.Properties.CourseId); // 降序
                qb.orderAsc(UserEntityDao.Properties.Age);// 升序
                List<UserEntity> youngJoes = qb.list();
                Log.d("排序查询用户信息", ": "+ Arrays.toString(youngJoes.toArray()));
            }
        });
        findViewById(R.id.bt_limitoffset_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryBuilder<UserEntity> qb = userDao.queryBuilder();
                qb.limit(limit);
                qb.offset(limit*page);
                List<UserEntity> youngJoes = qb.list();
                page++;
                Log.d("分页查询用户信息", ": "+ String.valueOf(Arrays.asList(youngJoes)));
            }
        });

        findViewById(R.id.bt_zhuan_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用属性转换器 查询
                UserEntity.RoleConverter converter = new UserEntity.RoleConverter();
                List<UserEntity> authors = userDao.queryBuilder()
                        .where(UserEntityDao.Properties.Role.eq(converter.convertToDatabaseValue(UserEntity.Role.AUTHOR)))
                        .list();
                Log.d("查询中的自定义类型-查询用户信息", ": "+ String.valueOf(Arrays.asList(authors)));
            }
        });
        findViewById(R.id.bt_parms_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query<UserEntity> qb = userDao.queryBuilder()
                        .where(
                            UserEntityDao.Properties.Age.gt(21),
                            UserEntityDao.Properties.Isbuy_limit_export.eq(true))
                        .build();
                qb.setParameter(0, 27);
                qb.setParameter(1, false);
                List<UserEntity> youngJoes = qb.list();
                Log.d("传参查询", ": "+ String.valueOf(Arrays.asList(youngJoes)));
            }
        });
        findViewById(R.id.bt_thread_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Query<UserEntity> qb = userDao.queryBuilder()
//                        .where(
//                            UserEntityDao.Properties.Age.gt(21),
//                            UserEntityDao.Properties.Isbuy_limit_export.eq(true))
//                        .build();
//                qb.setParameter(0, 27);
//                qb.setParameter(1, false);
//                List<UserEntity> youngJoes = qb.list();
//                Log.d("多线程查询", ": "+ String.valueOf(Arrays.asList(youngJoes)));
            }
        });
        findViewById(R.id.bt_oristr_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query<UserEntity> query = userDao.queryBuilder().where(
                        new WhereCondition.StringCondition("SELECT ALL FROM AWESOME_USERS")
                ).build();
                List<UserEntity> youngJoes = query.list();
                Log.d("原始StringCondition查询", ": "+ String.valueOf(Arrays.asList(youngJoes)));
            }
        });
        findViewById(R.id.bt_oristr_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //批量删除不会删除单个实体，但会删除符合某些条件的所有实体。要执行批量删除，请创建QueryBuilder，
                // 调用其 buildDelete （）方法，然后执行返回的 DeleteQuery
//                Query<UserEntity> query = userDao.queryBuilder().where(
//                        new WhereCondition.StringCondition("SELECT ALL FROM AWESOME_USERS")
//                ).build();
//                List<UserEntity> youngJoes = query.list();
//                Log.d("原始StringCondition查询", ": "+ String.valueOf(Arrays.asList(youngJoes)));
            }
        });
        findViewById(R.id.bt_update_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新用户信息
//                note.setText("This note has changed.");
//                noteDao.update(note);
            }
        });
        findViewById(R.id.del_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BApp.deleSQL();
            }
        });

        findViewById(R.id.beifei_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Utils.startBackUp(MainActivity.this,Utils.fileName);
                    return;
                }
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(MainActivity.this, 12, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .setRationale(R.string.text_storage_content)
                                .setPositiveButtonText(R.string.text_affirm)
                                .setNegativeButtonText(R.string.text_button_cancel)
                                .build());
            }
        });
        findViewById(R.id.restore_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // 放置crush 清楚缓存
                    daoSession.clear();
                    List<BackupBean> backupBeans = Utils.getBackupFiles();
                    Utils.startRestoreDb(MainActivity.this,backupBeans);
                    return;
                }
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(MainActivity.this, 13, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .setRationale(R.string.text_storage_content)
                                .setPositiveButtonText(R.string.text_affirm)
                                .setNegativeButtonText(R.string.text_button_cancel)
                                .build());
            }
        });
        findViewById(R.id.openfile_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showFileChooser(MainActivity.this);
            }
        });
    }

    private void initDao() {
        daoSession = ((BApp) getApplication()).getDaoSession();
        // 用户
        userDao = daoSession.getUserEntityDao();
        // 账单
        accountDao=daoSession.getAccountEntityDao();
        // chengtshi
        cityDao=daoSession.getCityEntityDao();
        //sheng
        provinceDao=daoSession.getProvinceEntityDao();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case 12:
                Utils.startBackUp(MainActivity.this,Utils.fileName);
                break;
            case 13:
                // 放置crush 清楚缓存
                daoSession.clear();
                List<BackupBean> backupBeans = Utils.getBackupFiles();
                Utils.startRestoreDb(MainActivity.this,backupBeans);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale(R.string.text_storage_permission_tip)
                    .setTitle(R.string.text_storage)
                    .setPositiveButton(R.string.text_affirm)
                    .setNegativeButton(R.string.text_button_cancel)
                    .build()
                    .show();
        }
    }

}
