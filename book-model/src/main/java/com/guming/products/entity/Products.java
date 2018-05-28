package com.guming.products.entity;

import com.guming.common.base.entity.BaseEntity;
import com.guming.orderTemplate.entity.TemplateProducts;
import com.guming.tagline.entity.TagLine;
import com.guming.tagrank.entity.TagRank;
import com.guming.tagwareHouse.entity.TagwareHouse;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
@Data
@Entity
@Table(name = "sys_products")
@NamedEntityGraph(
        name = "products.all",
        attributeNodes = {//attributeNodes 来定义需要懒加载的属性
                @NamedAttributeNode("productsClassify"),
                @NamedAttributeNode("tagRankList"),
                @NamedAttributeNode("tagwareHouseList"),
                @NamedAttributeNode("templateProductsList"),
                @NamedAttributeNode("productsStatusList"),
        }
)
public class Products extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "spec")
    private String spec;

    @Column(name = "unit")
    private String unit;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "max_amount")
    private Integer limit;

    @Column(name = "step")
    private Integer step;

    @Column(name = "is_up")
    private Boolean isUp;

    @Column(name = "sort")
    private Long order;

    @Column(name = "need_tag_lines")
    private Boolean needTagLines;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "update_time")
    private Date updatedTime;

    @Column(name = "content")
    private String describe;

    @Column(name = "stock_unit")
    private String stockUnit;

    @Column(name = "classify_id",insertable = false,updatable = false)
    private Long classifyId;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name = "classify_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private ProductsClassify productsClassify;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "product_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private List<ProductsStatus> productsStatusList;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinTable(
            name="sys_products_tagrank",
            joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name="tagrank_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    )
    private List<TagRank> tagRankList;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinTable(
            name="sys_products_tagwarehouse",
            joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name="tagwarehouse_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    )
    private List<TagwareHouse> tagwareHouseList;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinTable(
            name="sys_products_tagline",
            joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name="tagline_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    )
    private List<TagLine> tagLineList;

    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TemplateProducts> templateProductsList;

}
