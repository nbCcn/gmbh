package com.guming.orderTemplate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Data
@Entity
@Table(name = "sys_templates_type")
public class TemplatesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,mappedBy = "templatesType")
    private List<Templates> templates;
}
