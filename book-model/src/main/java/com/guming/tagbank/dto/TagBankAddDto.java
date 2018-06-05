package com.guming.tagbank.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/6/5 14:44
 */
@ApiModel(value = "TagBankAddDto", description = "银行账户实体")
public @Data
class TagBankAddDto extends BaseDto {

    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "银行账号")
    private String num;

    @ApiModelProperty(value = "银行名称")
    private String name;

    @ApiModelProperty(value = "支行名称")
    private String branch;

    @ApiModelProperty(value = "收款人")
    private String owner;


}
