package com.guming.arrangement.dto.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:58
 */
@ApiModel(value = "ArrangementQueryDto", description = "路线安排查询实体")
public class ArrangementQueryDto {

    @ApiModelProperty(value = "路线安排日期Str")
    private String dateStr;

    @ApiModelProperty(value = "仓库Id")
    private Long tagwareHouseId;

    @Override
    public String toString() {
        return "ArrangementQueryDto{" +
                "dateStr='" + dateStr + '\'' +
                ", tagwareHouseId='" + tagwareHouseId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrangementQueryDto that = (ArrangementQueryDto) o;
        return Objects.equals(dateStr, that.dateStr) &&
                Objects.equals(tagwareHouseId, that.tagwareHouseId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateStr, tagwareHouseId);
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Long getTagwareHouseId() {
        return tagwareHouseId;
    }

    public void setTagwareHouseId(Long tagwareHouseId) {
        this.tagwareHouseId = tagwareHouseId;
    }
}
