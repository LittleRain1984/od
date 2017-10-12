package com.od.oh.model.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by wangfacheng on 2017-07-18.
 */

@Entity
@Table(name = "car")
@SequenceGenerator(name = "seq_car", sequenceName = "seq_car")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car {

    //private final Logger logger = LoggerFactory.getLogger(OldDriver.class);

    @Id
    @GeneratedValue(generator = "seq_car", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private LocalDateTime added_at;

    @Column
    private LocalDateTime update_at;

    @Column
    private String remark;


    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
        this.remark = remark;
    }
}