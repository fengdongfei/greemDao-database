package net.cps.myapplication.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CourseEntity {
    @Id(autoincrement = true)
    private Long courseId;

    @Generated(hash = 239290744)
    public CourseEntity(Long courseId) {
        this.courseId = courseId;
    }

    @Generated(hash = 483818505)
    public CourseEntity() {
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
