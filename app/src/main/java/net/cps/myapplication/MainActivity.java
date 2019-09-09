package net.cps.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.entity.greendao.CourseEntityDao;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.model.UserEntity;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserEntityDao userDao;
    private DaoSession daoSession;
    private AccountEntityDao accountDao;
    private CourseEntityDao courseDao;
    public int limit=2;
    public int page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDao();
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
                // 删库
                userDao.deleteAll();
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
