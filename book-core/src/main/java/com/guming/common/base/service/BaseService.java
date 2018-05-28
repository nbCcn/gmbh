package com.guming.common.base.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 15:28 2018/4/10/010
 */
public interface BaseService{
    /**
     * 导入Excel,如需使用该方法，请重写getImportExportModel方法
     * @param multipartFile
     */
    void importExcel(MultipartFile multipartFile);
}
