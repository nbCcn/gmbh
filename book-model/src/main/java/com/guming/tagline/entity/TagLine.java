package com.guming.tagline.entity;


import com.guming.plans.entity.PlansPath;
import com.guming.tagwareHouse.entity.TagwareHouse;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 09:54
 */
@Entity
@Table(name = "sys_setups_tagline")
public class TagLine implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "ftype")
    private Integer ftype;
    @Column(name = "manager")
    private String manager;
    @Column(name = "phone")
    private String phone;
    @Column(name = "order_code")
    private Integer orderCode;
    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "tag_warehouse_id",insertable = false,updatable = false)
    private Long tagWarehouseId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_warehouse_id", referencedColumnName = "id")
    private TagwareHouse tagwareHouse;

    @OneToOne(mappedBy = "tagLine")
    private PlansPath plansPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public TagwareHouse getTagwareHouse() {
        return tagwareHouse;
    }

    public void setTagwareHouse(TagwareHouse tagwareHouse) {
        this.tagwareHouse = tagwareHouse;
    }

    public Long getTagWarehouseId() {
        return tagWarehouseId;
    }

    public void setTagWarehouseId(Long tagWarehouseId) {
        this.tagWarehouseId = tagWarehouseId;
    }

    public PlansPath getPlansPath() {
        return plansPath;
    }

    public void setPlansPath(PlansPath plansPath) {
        this.plansPath = plansPath;
    }
}
