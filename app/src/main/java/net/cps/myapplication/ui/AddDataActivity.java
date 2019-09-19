package net.cps.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.cps.myapplication.BApp;
import net.cps.myapplication.R;
import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.ProvinceEntityDao;
import net.cps.myapplication.entity.greendao.UserEntityDao;

public class AddDataActivity extends Activity {

    private UserEntityDao userDao;
    private DaoSession daoSession;
    private AccountEntityDao accountDao;
    private ProvinceEntityDao cityDao;
    private ProvinceEntityDao provinceDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initDao();
        findViewById(R.id.bt_add_provice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDataActivity.this, AddProvinceActivity.class));
            }
        });
        findViewById(R.id.bt_add_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDataActivity.this, AddCityActivity.class));
            }
        });
        findViewById(R.id.bt_add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDataActivity.this, AddUserActivity.class));
            }
        });

        findViewById(R.id.bt_add_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDataActivity.this, AddAccountActivity.class));
            }
        });

    }

    private void initDao() {
        daoSession = ((BApp) getApplication()).getDaoSession();
        // 用户
        userDao = daoSession.getUserEntityDao();
        // 账单
        accountDao=daoSession.getAccountEntityDao();
        // 省
        provinceDao=daoSession.getProvinceEntityDao();
        // 市
        cityDao=daoSession.getProvinceEntityDao();
    }
}
