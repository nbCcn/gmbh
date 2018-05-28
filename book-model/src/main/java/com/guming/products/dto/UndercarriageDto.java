package com.guming.products.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "UndercarriageDto",description = "商品上下架实体")
public class UndercarriageDto extends BaseDto {

    @ApiModelProperty(value = "商品id",required = true)
    private Long id;

    @ApiModelProperty(value = "是否上架（上架：true；下架：false）",required = true)
    private Boolean isUp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(Boolean isUp) {
        this.isUp = isUp;
    }
}
