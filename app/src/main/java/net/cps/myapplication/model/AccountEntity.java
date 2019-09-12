package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;


@Entity
public class AccountEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private Long account_id;
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
    @Generated(hash = 44405844)
    public AccountEntity(Long id, Long account_id, @NotNull String account_name,
            @NotNull Long createTime, @NotNull String classfy, String address,
            @NotNull Long money, boolean isdelet) {
        this.id = id;
        this.account_id = account_id;
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
    public String getClassfy() {
        return this.classfy;
    }
    public void setClassfy(String classfy) {
        this.classfy = classfy;
    }
}
