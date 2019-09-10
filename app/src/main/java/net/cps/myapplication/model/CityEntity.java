package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CityEntity {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private Long cid;

    @NotNull
    private String cname;

    @Generated(hash = 891823382)
    public CityEntity(Long id, Long cid, @NotNull String cname) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
    }

    @Generated(hash = 2001321047)
    public CityEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return this.cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
