package com.guming.products.model;

import com.guming.common.base.Excel.ImportExportModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/22
 */
@Data
public class ProductsImportExportModel extends ImportExportModel {

    private String code;

    private String name;

    private String productsClassify;

    private String stockUnit;

    private String unit;

    private Integer stock;

    private String spec;

    private BigDecimal price;

    private Integer limit;

    private Integer step;

    private List<String> tagRankList;

    private List<String> statusList;

    private List<String> tagWarehouseList;

    private List<String> tagLineList;

    private Boolean isUp;

    private String describe;
}
