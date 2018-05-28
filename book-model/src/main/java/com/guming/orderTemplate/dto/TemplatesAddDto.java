package com.guming.orderTemplate.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
public class TemplatesAddDto extends BaseDto {
    @ApiModelProperty(value = "模板名称",required = true)
    private String name;

    @ApiModelProperty(value = "模板是否有效(默认true)",required = true)
    private Boolean isActive=true;

    @ApiModelProperty(value = "模板类型id",required = true)
    private Long templatesTypeId;

    @ApiModelProperty(value = "模板所属仓库id",required = true)
    private Long tagwareHouseId;

    @ApiModelProperty(value = "路线id",required = true)
    private List<Long> taglineIdList;

    @ApiModelProperty(value = "包含的商品信息")
    private List<TemplatesProductsDto> templatesProductsDtoList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getTemplatesTypeId() {
        return templatesTypeId;
    }

    public void setTemplatesTypeId(Long templatesTypeId) {
        this.templatesTypeId = templatesTypeId;
    }

    public Long getTagwareHouseId() {
        return tagwareHouseId;
    }

    public void setTagwareHouseId(Long tagwareHouseId) {
        this.tagwareHouseId = tagwareHouseId;
    }

    public List<Long> getTaglineIdList() {
        return taglineIdList;
    }

    public void setTaglineIdList(List<Long> taglineIdList) {
        this.taglineIdList = taglineIdList;
    }

    public List<TemplatesProductsDto> getTemplatesProductsDtoList() {
        return templatesProductsDtoList;
    }

    public void setTemplatesProductsDtoList(List<TemplatesProductsDto> templatesProductsDtoList) {
        this.templatesProductsDtoList = templatesProductsDtoList;
    }
}
