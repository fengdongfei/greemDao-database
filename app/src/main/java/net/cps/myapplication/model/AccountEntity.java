package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.entity.greendao.AccountEntityDao;


@Entity
public class AccountEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private Long account_id;
    @NotNull
    private Long userid;
    // 一个订单对一个一个用户  1-1    userid作为外键与UserEntity中的主键（也就是id）相连。
    @ToOne(joinProperty = "userid")
    private UserEntity userEntity;

    @NotNull
    private String account_name;
    @NotNull
    private Long createTime;
    @NotNull
    private String classfy;
    private String address;
    @NotNull
    private Long money;
    private boolean isdelet;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1708931600)
    private transient AccountEntityDao myDao;
    @Generated(hash = 183914455)
    public AccountEntity(Long id, Long account_id, @NotNull Long userid,
            @NotNull String account_name, @NotNull Long createTime, @NotNull String classfy,
            String address, @NotNull Long money, boolean isdelet) {
        this.id = id;
        this.account_id = account_id;
        this.userid = userid;
        this.account_name = account_name;
        this.createTime = createTime;
        this.classfy = classfy;
        this.address = address;
        this.money = money;
        this.isdelet = isdelet;
    }
    @Generated(hash = 40307897)
    public AccountEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getAccount_id() {
        return this.account_id;
    }
    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }
    public String getAccount_name() {
        return this.account_name;
    }
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
    public Long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getClassfy() {
        return this.classfy;
    }
    public void setClassfy(String classfy) {
        this.classfy = classfy;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getMoney() {
        return this.money;
    }
    public void setMoney(Long money) {
        this.money = money;
    }
    public boolean getIsdelet() {
        return this.isdelet;
    }
    public void setIsdelet(boolean isdelet) {
        this.isdelet = isdelet;
    }
    @Generated(hash = 1473225700)
    private transient Long userEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1776396541)
    public UserEntity getUserEntity() {
        Long __key = this.userid;
        if (userEntity__resolvedKey == null || !userEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserEntityDao targetDao = daoSession.getUserEntityDao();
            UserEntity userEntityNew = targetDao.load(__key);
            synchronized (this) {
                userEntity = userEntityNew;
                userEntity__resolvedKey = __key;
            }
        }
        return userEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 662833523)
    public void setUserEntity(@NotNull UserEntity userEntity) {
        if (userEntity == null) {
            throw new DaoException(
                    "To-one property 'userid' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.userEntity = userEntity;
            userid = userEntity.getId();
            userEntity__resolvedKey = userid;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 442337859)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAccountEntityDao() : null;
    }
    public Long getUserid() {
        return this.userid;
    }
    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
