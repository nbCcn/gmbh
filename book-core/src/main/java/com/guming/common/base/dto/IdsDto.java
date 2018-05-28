package com.guming.common.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
@Data
public class IdsDto extends BaseDto {

    @ApiModelProperty(value = "id组")
    private List<Long> ids;
}
