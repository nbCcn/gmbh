package com.guming.orderTemplate.entity;


import com.guming.tagline.entity.TagLine;
import com.guming.tagwareHouse.entity.TagwareHouse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Getter
@Setter
@Entity
@Table(name = "sys_templates")
@NamedEntityGraph(name = "templates.all",
        attributeNodes = {//attributeNodes 来定义需要懒加载的属性
                @NamedAttributeNode("templatesType"),
                @NamedAttributeNode("tagwareHouse"),
                @NamedAttributeNode("tagLineList"),
                @NamedAttributeNode(value = "templateProductsList",//要懒加载productsList属性中的products元素
                        subgraph = "products"),
        },
        subgraphs = {//subgraphs 来定义关联对象的属性
                @NamedSubgraph(name = "products",//一层延伸
                        attributeNodes = @NamedAttributeNode("products"))
        }
        )
public class Templates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "temp_type_id",insertable = false, updatable = false)
    private Long tempTypeId;

    @Column(name = "tag_warehouse_id",insertable = false, updatable = false)
    private Long tagWarehouseId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "temp_type_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private TemplatesType templatesType;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "tag_warehouse_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private TagwareHouse tagwareHouse;

    @OneToMany(mappedBy = "templates",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TemplateProducts> templateProductsList;

    @ManyToMany
    @JoinTable(
            name = "sys_templates_tagline",
            joinColumns = {@JoinColumn(name ="templates_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = { @JoinColumn(name = "tagline_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)) }
    )
    private List<TagLine> tagLineList;
}
