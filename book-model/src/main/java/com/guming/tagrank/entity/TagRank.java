package com.guming.tagrank.entity;


import com.guming.shops.entitiy.ShopsShop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/16 11:26
 */
@Entity
@Table(name = "sys_setups_tagrank")
public class TagRank implements Serializable {
    private Long id;
    private String name;
    private Integer isPrepare;
    private Integer orderCode;
    private Date createdTime;
    private Date updatedTime;
    private List<ShopsShop> shopsShop;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Is_prepare")
    public Integer getIsPrepare() {
        return isPrepare;
    }

    public void setIsPrepare(Integer isPrepare) {
        this.isPrepare = isPrepare;
    }

    @Column(name = "Order_code")
    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "updated_time")
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "TagRank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPrepare=" + isPrepare +
                ", orderCode=" + orderCode +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

    @OneToMany(mappedBy = "tagRank", fetch = FetchType.LAZY)
    public List<ShopsShop> getShopsShop() {
        return shopsShop;
    }

    public void setShopsShop(List<ShopsShop> shopsShop) {
        this.shopsShop = shopsShop;
    }
}
