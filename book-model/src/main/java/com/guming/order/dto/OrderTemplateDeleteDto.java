package com.guming.order.dto;

import com.guming.common.base.dto.IdsDto;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/28
 */
@Data
public class OrderTemplateDeleteDto extends IdsDto {

    private Long orderId;
}
