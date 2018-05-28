package com.guming.plans.entity;


import com.guming.arrangement.entity.PlansArrangement;
import com.guming.tagline.entity.TagLine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 11:27
 */
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

    public List<Pathshop> getPathshopList() {
        return pathshopList;
    }

    public void setPathshopList(List<Pathshop> pathshopList) {
        this.pathshopList = pathshopList;
    }

    public List<PlansArrangement> getPlansArrangementsList() {
        return plansArrangementsList;
    }

    public void setPlansArrangementsList(List<PlansArrangement> plansArrangementsList) {
        this.plansArrangementsList = plansArrangementsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTagLineId() {
        return tagLineId;
    }

    public void setTagLineId(Long tagLineId) {
        this.tagLineId = tagLineId;
    }

    public TagLine getTagLine() {
        return tagLine;
    }

    public void setTagLine(TagLine tagLine) {
        this.tagLine = tagLine;
    }
}
