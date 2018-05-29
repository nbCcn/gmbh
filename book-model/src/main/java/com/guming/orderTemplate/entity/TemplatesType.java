package com.guming.orderTemplate.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Getter
@Setter
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
