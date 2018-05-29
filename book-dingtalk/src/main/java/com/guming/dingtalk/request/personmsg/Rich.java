package com.guming.dingtalk.request.personmsg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/28
 */
@Data
public class Rich {

    @JSONField(name = "num")
    private String num;

    @JSONField(name = "unit")
    private String unit;

}
