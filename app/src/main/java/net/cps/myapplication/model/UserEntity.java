package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.converter.PropertyConverter;

import net.cps.myapplication.entity.greendao.DaoSession;
import net.cps.myapplication.entity.greendao.CourseEntityDao;
import net.cps.myapplication.entity.greendao.UserEntityDao;

import java.util.List;
import net.cps.myapplication.entity.greendao.AccountEntityDao;

@Entity(
//        schema = "userscheme",// schema 名，多个 schema 时设置关联实体。插件产生不支持，需使用产生器
        active = true,// 标记一个实体是否处于活动状态，活动实体有 update、delete、refresh 方法。默认为 false
        nameInDb = "AWESOME_USERS",//表名，默认为类名
        // 定义多列索引
        indexes = {
                @Index(value = "name DESC", unique = true)
        },
        // 标记是否创建表，默认 true。多实体对应一个表或者表已创建，不需要 greenDAO 创建时设置 false
        createInDb = true,
        // 是否产生所有参数构造器。默认为 true。无参构造器必定产生
        generateConstructors = true,
        // 如果没有 get/set 方法，是否生成。默认为 true
        generateGettersSetters = true

)
public class UserEntity {
    // 数据库主键，autoincrement设置自增，只能为 long/ Long 类型
    @Id(autoincrement = true)
    private Long id;

    // 唯一，默认索引。可另定义属性唯一索引设为主键
    //@Unique向数据库列添加UNIQUE约束。请注意，SQLite还隐式为其创建索引
    @Unique
    private Long uid;

    // 列名，默认使用变量名。默认变化：name --> USER_NAME
    @Property(nameInDb = "USER_NAME")
    private String name;

    // 索引，unique设置唯一，courseId设置索引别名 向索引添加UNIQUE约束，强制所有值都是唯一的
    @Index(unique = true)
    private long courseId;

    //@Transient标记要从持久性中排除的属性。将它们用于临时状态等。或者，您也可以使用Java中的transient关键字。
    @Transient
    private int tempUsageCount;

    // 对一关系，实体属性 joinProperty 对应外联实体ID
    // 一个用户对应一个学习课程
    @ToOne(joinProperty = "courseId")
    private CourseEntity coursemodel;

    // 对多。实体ID对应外联实体属性 referencedJoinProperty: 指定目标实体中指向此实体的id的“外键”属性的名称。
    // 一个用户对应多个账单
    @ToMany(referencedJoinProperty = "account_id")
    private List<AccountEntity> accountlist;

    // 非空
    @NotNull
    private String nick;

    //转换注释和属性转换器
    //注意：如果在实体类中定义自定义类型或转换器，则它们必须是静态的。
    //不要忘记正确处理空值 - 通常，如果输入为null，则应返回null。
    @Convert(converter = RoleConverter.class, columnType = Integer.class)
    private Role role;


    private int age;
    private Long create_time;
    private String icon;
    private String phone;
    private String address_province;
    private String address_city;
    private String address;
    private boolean isvip;// 是否是vip
    private boolean isbuy_zone; // 是否购买云空间
    private boolean isbuy_limit_export;// 是否购买导出数据权限
    private boolean isbuy_limit_ads;// 是否购买屏蔽广告权限

    public enum Role {
        DEFAULT(0), AUTHOR(1), ADMIN(2);

        final int id;

        Role(int id) {
            this.id = id;
        }
    }

    public static class RoleConverter implements PropertyConverter<Role, Integer> {
        @Override
        public Role convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (Role role : Role.values()) {
                if (role.id == databaseValue) {
                    return role;
                }
            }
            return Role.DEFAULT;
        }

        @Override
        public Integer convertToDatabaseValue(Role entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}
public long getCourseId() {
    return this.courseId;
}
public void setCourseId(long courseId) {
    this.courseId = courseId;
}
public String getNick() {
    return this.nick;
}
public void setNick(String nick) {
    this.nick = nick;
}
public int getAge() {
    return this.age;
}
public void setAge(int age) {
    this.age = age;
}
public Long getCreate_time() {
    return this.create_time;
}
public void setCreate_time(Long create_time) {
    this.create_time = create_time;
}
public String getIcon() {
    return this.icon;
}
public void setIcon(String icon) {
    this.icon = icon;
}
public String getPhone() {
    return this.phone;
}
public void setPhone(String phone) {
    this.phone = phone;
}
public String getAddress_province() {
    return this.address_province;
}
public void setAddress_province(String address_province) {
    this.address_province = address_province;
}
public String getAddress_city() {
    return this.address_city;
}
public void setAddress_city(String address_city) {
    this.address_city = address_city;
}
public String getAddress() {
    return this.address;
}
public void setAddress(String address) {
    this.address = address;
}
public boolean getIsvip() {
    return this.isvip;
}
public void setIsvip(boolean isvip) {
    this.isvip = isvip;
}
public boolean getIsbuy_zone() {
    return this.isbuy_zone;
}
public void setIsbuy_zone(boolean isbuy_zone) {
    this.isbuy_zone = isbuy_zone;
}
public boolean getIsbuy_limit_export() {
    return this.isbuy_limit_export;
}
public void setIsbuy_limit_export(boolean isbuy_limit_export) {
    this.isbuy_limit_export = isbuy_limit_export;
}
public boolean getIsbuy_limit_ads() {
    return this.isbuy_limit_ads;
}
public void setIsbuy_limit_ads(boolean isbuy_limit_ads) {
    this.isbuy_limit_ads = isbuy_limit_ads;
}
@Generated(hash = 2050378249)
private transient Long coursemodel__resolvedKey;

/** Used for active entity operations. */
@Generated(hash = 1814575071)
private transient UserEntityDao myDao;

@Generated(hash = 1433178141)
public UserEntity() {
}
@Generated(hash = 422024968)
public UserEntity(Long id, Long uid, String name, long courseId, @NotNull String nick, Role role,
        int age, Long create_time, String icon, String phone, String address_province,
        String address_city, String address, boolean isvip, boolean isbuy_zone,
        boolean isbuy_limit_export, boolean isbuy_limit_ads) {
    this.id = id;
    this.uid = uid;
    this.name = name;
    this.courseId = courseId;
    this.nick = nick;
    this.role = role;
    this.age = age;
    this.create_time = create_time;
    this.icon = icon;
    this.phone = phone;
    this.address_province = address_province;
    this.address_city = address_city;
    this.address = address;
    this.isvip = isvip;
    this.isbuy_zone = isbuy_zone;
    this.isbuy_limit_export = isbuy_limit_export;
    this.isbuy_limit_ads = isbuy_limit_ads;
}
/** To-one relationship, resolved on first access. */
@Generated(hash = 737950312)
public CourseEntity getCoursemodel() {
    long __key = this.courseId;
    if (coursemodel__resolvedKey == null
            || !coursemodel__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        CourseEntityDao targetDao = daoSession.getCourseEntityDao();
        CourseEntity coursemodelNew = targetDao.load(__key);
        synchronized (this) {
            coursemodel = coursemodelNew;
            coursemodel__resolvedKey = __key;
        }
    }
    return coursemodel;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 297327591)
public void setCoursemodel(@NotNull CourseEntity coursemodel) {
    if (coursemodel == null) {
        throw new DaoException(
                "To-one property 'courseId' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.coursemodel = coursemodel;
        courseId = coursemodel.getCourseId();
        coursemodel__resolvedKey = courseId;
    }
}
/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 2013377999)
public List<AccountEntity> getAccountlist() {
    if (accountlist == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        AccountEntityDao targetDao = daoSession.getAccountEntityDao();
        List<AccountEntity> accountlistNew = targetDao._queryUserEntity_Accountlist(id);
        synchronized (this) {
            if (accountlist == null) {
                accountlist = accountlistNew;
            }
        }
    }
    return accountlist;
}
/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1410064263)
public synchronized void resetAccountlist() {
    accountlist = null;
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public Long getUid() {
    return this.uid;
}
public void setUid(Long uid) {
    this.uid = uid;
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
@Generated(hash = 287999134)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getUserEntityDao() : null;
}
public Role getRole() {
    return this.role;
}
public void setRole(Role role) {
    this.role = role;
}
}
