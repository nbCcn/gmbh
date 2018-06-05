package com.guming.tagwareHouse.entity;

import com.guming.products.entity.Products;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.tagbank.entity.TagBank;
import com.guming.tagline.entity.TagLine;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sys_setups_tagwarehouse")
public class TagwareHouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "webhook")
    private String webhook;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @ManyToMany(mappedBy = "tagwareHouseList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Products> productsList;

    @OneToMany(mappedBy = "tagwareHouse", fetch = FetchType.LAZY)
    private List<TagLine> tagLines;

    @ManyToMany(mappedBy = "tagwareHouseSet", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<ShopsShop> shopsShops;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "sys_setups_tagbank_tagwarehouse",
            joinColumns = @JoinColumn(name = "tagwarehouse_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "tagbank_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    )
    private List<TagBank> tagBankList;
}