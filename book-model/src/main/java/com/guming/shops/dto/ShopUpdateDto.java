package com.guming.shops.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 09:52
 */
@ApiModel(value = "ShopUpdateDto", description = "店铺更新实体")
public class ShopUpdateDto extends ShopAddDto {
    @ApiModelProperty(value = "店铺ID", required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
