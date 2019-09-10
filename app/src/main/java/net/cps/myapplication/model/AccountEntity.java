package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.entity.greendao.AccountEntityDao;
import net.cps.myapplication.model.UserEntity;

@Entity
public class AccountEntity {
    @Id(autoincrement = true)
    private Long id;

    private Long account_id;
    private String account_name;
    private Long createTime;
    private String address;
    private Long money;
    private boolean isdelet;

    @ToOne(joinProperty = "account_id")
    private UserEntity userEntity;

}
