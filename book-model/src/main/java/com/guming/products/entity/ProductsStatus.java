package com.guming.products.entity;

import javax.persistence.*;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/8
 */
@Entity
@Table(name = "sys_products_status")
public class ProductsStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "shop_status")
    private Integer shopStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }
}
