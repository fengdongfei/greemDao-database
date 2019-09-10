package net.cps.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.entity.greendao.CourseEntityDao;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.model.UserEntity;

public class AddDataActivity extends Activity {

    private UserEntityDao userDao;
    private DaoSession daoSession;
    private AccountEntityDao accountDao;
    private CourseEntityDao courseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViewById(R.id.bt_add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 插入----添加用户
                UserEntity ueneity = new UserEntity(Long.parseLong("1000000001"),Long.parseLong("1000000002"),
                        "name_01",3000001,"nick_01",null,28,
                        Long.parseLong("1000000003"),"icon01","18298192053",
                        "安徽省","霍邱县","安徽临水镇",
                        false,false,false,false);
                userDao.insert(ueneity);
            }
        });
        findViewById(R.id.bt_add_user2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 插入----添加用户
                UserEntity ueneity = new UserEntity(Long.parseLong("1000000003"),Long.parseLong("1000000004"),
                        "name_02",3000002,"nick_02",UserEntity.Role.ADMIN,29,
                        Long.parseLong("1000000003"),"icon01","18298192054",
                        "安徽省111","霍邱县111","安徽111临水镇",
                        true,true,true,true);
                userDao.insert(ueneity);
            }
        });
        findViewById(R.id.bt_add_user3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 插入----添加用户
                UserEntity ueneity = new UserEntity(Long.parseLong("1000000005"),Long.parseLong("1000000006"),
                        "name_03",3000003,"nick_03",UserEntity.Role.AUTHOR,38,
                        Long.parseLong("1000000003"),"icon03","18298192054",
                        "安徽省","霍邱县222","安徽临水镇222",
                        true,true,false,false);
                userDao.insert(ueneity);
            }
        });
        findViewById(R.id.bt_add_user4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 插入----添加用户
                UserEntity ueneity = new UserEntity(Long.parseLong("1000000007"),Long.parseLong("1000000008"),
                        "name_04",3000004,"nick_04", null,21,
                        Long.parseLong("1000000003"),"icon0111","18298192057",
                        "安徽省333","霍邱县333","安徽临水镇333",
                        false,false,true,true);
                userDao.insert(ueneity);
            }
        });

    }

    private void initDao() {
        daoSession = ((BApp) getApplication()).getDaoSession();
        // 用户
        userDao = daoSession.getUserEntityDao();
        // 账单
        accountDao=daoSession.getAccountEntityDao();
        // 课程
        courseDao=daoSession.getCourseEntityDao();
    }
}
