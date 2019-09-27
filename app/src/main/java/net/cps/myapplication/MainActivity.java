package net.cps.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import net.cps.myapplication.backup.Cont;
import net.cps.myapplication.backup.Utils;
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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ColourUtil;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;
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
        findViewById(R.id._excel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // 参考资料: https://github.com/zhouzhuo810/ZzExcelCreator
                    if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        createExcel();
                        return;
                    }
                    EasyPermissions.requestPermissions(
                            new PermissionRequest.Builder(MainActivity.this, 14, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    .setRationale(R.string.text_daochu_content)
                                    .setPositiveButtonText(R.string.text_affirm)
                                    .setNegativeButtonText(R.string.text_button_cancel)
                                    .build());
            }
        });
    }

    private void createExcel() {
        // 创建Excel文件和工作表
        try {
            new AsyncTask<String, Void, Integer>() {

                @Override
                protected Integer doInBackground(String... params) {
                    try {
                        // 创建excel
                        ZzExcelCreator
                                .getInstance()
                                .createExcel(Cont.EXCEL_PATH, params[0]) // todo 动态excel名称
                                .createSheet(params[1]) // todo 动态sheet
                                .close();
                        // addSheet
                        ZzExcelCreator
                                .getInstance()
                                .openExcel(new File(Cont.EXCEL_PATH + params[0] + ".xls"))  //如果不想覆盖文件，注意是openExcel
                                .createSheet(params[1]+"_new") // todo 动态sheet
                                .close();
                        // 添加excel内容--string
                        ZzExcelCreator zzExcelCreator = ZzExcelCreator
                                .getInstance()
                                .openExcel(new File(Cont.EXCEL_PATH + params[0] + ".xls"))
                                .openSheet(0);//todo 打开第1个sheet
                        WritableCellFormat cellFormat = ZzFormatCreator
                                .getInstance()
                                .createCellFont(WritableFont.ARIAL)
                                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                                .setFontSize(30)
                                .setFontBold(true)
                                .setUnderline(true)
                                .setItalic(true)
                                .setWrapContent(true, 100)
                                .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                                .getCellFormat();
                        zzExcelCreator
                                .fillContent(Integer.parseInt("0"), Integer.parseInt("0"), "内容", cellFormat) // todo 添加行列对应的内容 列行string内容
                                .close();
                        // 添加excel内容--int
                        ZzExcelCreator zzExcelCreator1 = ZzExcelCreator
                                .getInstance()
                                .openExcel(new File(Cont.EXCEL_PATH +  params[0] + ".xls"))
                                .openSheet(0);//todo 打开第1个sheet
                        WritableCellFormat cellFormat1 = ZzFormatCreator
                                .getInstance()
                                .createCellFont(WritableFont.ARIAL)
                                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                                .setFontSize(20)
                                .setFontColor(Colour.WHITE)
                                .setBackgroundColor(ColourUtil.getCustomColor1("#99cc00"))
                                .setBorder(Border.ALL, BorderLineStyle.THIN, ColourUtil.getCustomColor2("#dddddd"))
                                .getCellFormat();
                        zzExcelCreator1.fillNumber(Integer.parseInt("0"), Integer.parseInt("1"), Double.parseDouble("100"), cellFormat1)// todo 添加行列对应的内容 列行int内容
                                .close();
                        // 获取cell内容
                        ZzExcelCreator zzExcelCreator2 = ZzExcelCreator
                                .getInstance()
                                .openExcel(new File(Cont.EXCEL_PATH +  params[0] + ".xls"))
                                .openSheet(0);   //todo 打开第1个sheet
                        String content = zzExcelCreator2.getCellContent(Integer.parseInt("0"), Integer.parseInt("0"));// todo 获取行列对应的内容 列行string
                        zzExcelCreator2.close();
                        Log.e("getcell ", "doInBackground: "+content );
                        // 合并
                        ZzExcelCreator
                                .getInstance()
                                .openExcel(new File(Cont.EXCEL_PATH +  params[0]  + ".xls"))
                                .openSheet(0) // todo 打开第1个sheet
                                .merge(Integer.parseInt("0"), Integer.parseInt("0"), Integer.parseInt("1"), Integer.parseInt("0")) // todo 合并起始行号,结束列号,结束行号,结束列号
                                .close();

                        return 1;
                    } catch (IOException | WriteException | BiffException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }

                @Override
                protected void onPostExecute(Integer aVoid) {
                    super.onPostExecute(aVoid);
                    if (aVoid == 1) {
                        Toast.makeText(MainActivity.this, "数据导出成功！请到" + Cont.EXCEL_PATH + "路径下查看~", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "数据导出失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute("mitu_excel_backup", "user_sheet");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            case 14:
                createExcel();
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
