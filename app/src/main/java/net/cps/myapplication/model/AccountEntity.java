package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.UserEntityDao;
import net.cps.myapplication.entity.greendao.AccountEntityDao;

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

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1708931600)
    private transient AccountEntityDao myDao;

    @Generated(hash = 143891919)
    public AccountEntity(Long id, Long account_id, String account_name,
            Long createTime, String address, Long money, boolean isdelet) {
        this.id = id;
        this.account_id = account_id;
        this.account_name = account_name;
        this.createTime = createTime;
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
    @Generated(hash = 188573205)
    public UserEntity getUserEntity() {
        Long __key = this.account_id;
        if (userEntity__resolvedKey == null
                || !userEntity__resolvedKey.equals(__key)) {
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
    @Generated(hash = 1546757431)
    public void setUserEntity(UserEntity userEntity) {
        synchronized (this) {
            this.userEntity = userEntity;
            account_id = userEntity == null ? null : userEntity.getId();
            userEntity__resolvedKey = account_id;
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
}
