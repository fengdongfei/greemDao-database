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
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

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
    private Long addressProvinceID;
    private Long addressCityId;
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

}
