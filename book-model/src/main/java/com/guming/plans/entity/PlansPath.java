package com.guming.plans.entity;


import com.guming.arrangement.entity.PlansArrangement;
import com.guming.tagline.entity.TagLine;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 11:27
 */
@Getter
@Setter
@Entity
@Table(name = "sys_plans_path")
@NamedEntityGraph(
        name = "PlansPath.all",
        attributeNodes = {
                @NamedAttributeNode("pathshopList"),
        }
)
public class PlansPath implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "tag_line_id", insertable = false, updatable = false)
    private Long tagLineId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_line_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private TagLine tagLine;

    @OneToMany(mappedBy = "plansPath", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pathshop> pathshopList;

    @ManyToMany(mappedBy = "plansPathList", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<PlansArrangement> plansArrangementsList;
}
