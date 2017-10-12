package com.od.oh.model.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by wangfacheng on 2017-07-18.
 */

@Entity
@Table(name = "old_driver")
@SequenceGenerator(name = "seq_old_driver", sequenceName = "seq_old_driver")
public class OldDriver {

    //private final Logger logger = LoggerFactory.getLogger(OldDriver.class);

    @Id
    @GeneratedValue(generator = "seq_old_driver", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private LocalDateTime added_at;

    @Column
    private LocalDateTime update_at;

    @Column
    private String remark;


    public OldDriver() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getAdded_at() {
        return added_at;
    }

    public void setAdded_at(LocalDateTime added_at) {
        this.added_at = added_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        //logger.info("Setting the Remark.");
        this.remark = remark;
    }

    public void setDate() {
        this.setAdded_at(LocalDateTime.now());
        this.setUpdate_at(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return super.toString() + " [ID:" + getId() + ", Name:" + getName() + ", Age: " + getAge() + ", Remark:" + getRemark()
                + ", created:" + getAdded_at() + ", Updated:" + getUpdate_at() + "]";
    }


}
