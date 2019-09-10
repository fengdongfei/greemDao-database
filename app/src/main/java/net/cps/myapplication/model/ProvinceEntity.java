package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ProvinceEntity {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private Long pid;

    @NotNull
    private String pname;

    @Generated(hash = 102876631)
    public ProvinceEntity(Long id, Long pid, @NotNull String pname) {
        this.id = id;
        this.pid = pid;
        this.pname = pname;
    }

    @Generated(hash = 1419486807)
    public ProvinceEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
