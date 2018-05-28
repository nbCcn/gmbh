package com.guming.service.products.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.dao.products.ProductsClassifyRepository;
import com.guming.products.dto.ProductsClassifyAddDto;
import com.guming.products.dto.ProductsClassifyUpdateDto;
import com.guming.products.dto.query.ProductsClassifyQuery;
import com.guming.products.entity.Products;
import com.guming.products.entity.ProductsClassify;
import com.guming.products.vo.ProductsClassifyVo;
import com.guming.service.products.ProductsClassifyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/17
 */
@Service
public class ProductsClassifyServiceImpl extends BaseServiceImpl implements ProductsClassifyService {

    @Autowired
    private ProductsClassifyRepository productsClassifyRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.productsClassifyRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam addProductsClassify(ProductsClassifyAddDto productsClassifyAddDto) {
        if (StringUtils.isEmpty(productsClassifyAddDto.getName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_NAME_EMPTY);
        }

        ProductsClassify productsClassify = new ProductsClassify();
        BeanUtils.copyProperties(productsClassifyAddDto,productsClassify,"id");
        productsClassify.setCreatedTime(new Date());
        productsClassify.setUpdatedTime(new Date());
        productsClassifyRepository.save(productsClassify);
        return getSuccessAddResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateProductsClassify(ProductsClassifyUpdateDto productsClassifyUpdateDto) {
        if (productsClassifyUpdateDto.getId()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_ID_EMPTY);
        }

        if (StringUtils.isEmpty(productsClassifyUpdateDto.getName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_NAME_EMPTY);
        }

        ProductsClassify productsClassify = productsClassifyRepository.findOne(productsClassifyUpdateDto.getId());
        if (productsClassify == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_NOT_EXISTS);
        }
        BeanUtils.copyProperties(productsClassifyUpdateDto,productsClassify);
        productsClassify.setUpdatedTime(new Date());
        productsClassifyRepository.save(productsClassify);
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam deleteProductsClassify(List<Long> ids) {
        if (ids==null || ids.isEmpty()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_ID_EMPTY_DELETE);
        }

        //判断商品分类是否被使用，被使用则无法删除
        String usedProductsClassify = "";
        List<Long> needDeleteProductsClassifyId = new ArrayList<Long>();
        for (Long id: ids){
           ProductsClassify productsClassify = productsClassifyRepository.findOne(id);
           List<Products> productsList = productsClassify.getProductsList();
           if (productsList != null && !productsList.isEmpty()){
               usedProductsClassify += productsClassify.getName()+",";
           }else{
               needDeleteProductsClassifyId.add(id);
           }
        }
        if(needDeleteProductsClassifyId!=null && !needDeleteProductsClassifyId.isEmpty()) {
            productsClassifyRepository.deleteProductsClassifiesByIds(needDeleteProductsClassifyId);
        }
        if (!StringUtils.isEmpty(usedProductsClassify)){
            usedProductsClassify = usedProductsClassify.substring(0,usedProductsClassify.length()-1);
            return ResponseParam.error(ErrorMsgConstants.OPTION_FAILED_HELF_CODE,i18nHandler(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_USED,usedProductsClassify));
        }

        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<ProductsClassifyVo>> findPage(ProductsClassifyQuery productsClassifyQuery) {
        Specification<ProductsClassify> specification = new Specification<ProductsClassify>() {
            @Override
            public Predicate toPredicate(Root<ProductsClassify> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(productsClassifyQuery.getName())){
                    Predicate likeName = criteriaBuilder.like(root.get("name").as(String.class),"%" + productsClassifyQuery.getName()+"%");
                    predicates.add(likeName);
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(criteriaBuilder.desc(root.get("order").as(Long.class)),criteriaBuilder.desc(root.get("id").as(Long.class))).getRestriction();
            }
        };

        Pageable pageable = new PageRequest(productsClassifyQuery.getPage(),productsClassifyQuery.getPageSize());
        Page<ProductsClassify> userPage=productsClassifyRepository.findAll(specification,pageable);
        Pagination pagination = new Pagination(userPage.getTotalElements(),userPage.getNumber(),userPage.getSize());
        List<ProductsClassifyVo> result = CovertUtil.copyList(userPage.getContent(), ProductsClassifyVo.class);
        return ResponseParam.success(result,pagination);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<ProductsClassifyVo> findById(Long id) {
        if(id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_ID_EMPTY);
        }
        ProductsClassify productsClassify = productsClassifyRepository.findOne(id);
        if (null == productsClassify){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_NOT_EXISTS);
        }
        ProductsClassifyVo productsClassifyVo = new ProductsClassifyVo();
        BeanUtils.copyProperties(productsClassify,productsClassifyVo);
        return ResponseParam.success(productsClassifyVo);
    }

    @Override
    public ResponseParam<List<MapVo>> findAll() {
        return ResponseParam.success( getProductsClassifyList());
    }

    /**
     * 获取商品分类展示列
     * @return
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<MapVo> getProductsClassifyList(){
        List<ProductsClassify> productsClassifyList = productsClassifyRepository.findProductsClassifyList();
        List<MapVo> mapVoList = new ArrayList<MapVo>();
        for (ProductsClassify productsClassify: productsClassifyList){
            MapVo mapVo = new MapVo();
            mapVo.setId(productsClassify.getId());
            mapVo.setName(productsClassify.getName());
            mapVoList.add(mapVo);
        }
        return mapVoList;
    }
}
