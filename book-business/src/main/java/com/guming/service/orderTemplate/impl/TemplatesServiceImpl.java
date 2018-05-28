package com.guming.service.orderTemplate.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.OrderStatus;
import com.guming.common.constants.business.ShopStatus;
import com.guming.common.constants.business.TemplateType;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.dao.order.OrderSubmissionRepository;
import com.guming.dao.orderTemplate.TemplatesRepository;
import com.guming.dao.orderTemplate.TemplatesTypeRepository;
import com.guming.dao.products.ProductsRepository;
import com.guming.dao.shops.ShopRepository;
import com.guming.dao.tagline.TagLineRepository;
import com.guming.dao.tagwareHouse.TagwareHouseRepository;
import com.guming.order.entity.OrderSubmission;
import com.guming.orderTemplate.dto.TemplatesActiveDto;
import com.guming.orderTemplate.dto.TemplatesAddDto;
import com.guming.orderTemplate.dto.TemplatesProductsDto;
import com.guming.orderTemplate.dto.TemplatesUpdateDto;
import com.guming.orderTemplate.dto.query.TemplatesQuery;
import com.guming.orderTemplate.entity.TemplateProducts;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.orderTemplate.vo.TemplateProductsVo;
import com.guming.orderTemplate.vo.TemplatesVo;
import com.guming.plans.entity.Pathshop;
import com.guming.products.entity.Products;
import com.guming.service.orderTemplate.TemplatesService;
import com.guming.service.products.ProductsService;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.tagline.entity.TagLine;
import com.guming.tagwareHouse.entity.TagwareHouse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Service
public class TemplatesServiceImpl extends BaseServiceImpl implements TemplatesService {

    @Autowired
    private TemplatesRepository templatesRepository;

    @Autowired
    private TemplatesTypeRepository templatesTypeRepository;

    @Autowired
    private TagwareHouseRepository tagwareHouseRepository;

    @Autowired
    private TagLineRepository tagLineRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private OrderSubmissionRepository orderSubmissionRepository;

    @Override
    protected BaseRepository getRepository() {
        return templatesRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam add(TemplatesAddDto templatesAddDto) {
        validateParam(templatesAddDto);
        Templates templates = new Templates();
        covertTemplatesDtoToTemplates(templatesAddDto,templates,false);
        templatesRepository.save(templates);
        return getSuccessAddResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam update(TemplatesUpdateDto templatesUpdateDto) {
        if(templatesUpdateDto.getId()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        validateParam(templatesUpdateDto);

        Templates templates = templatesRepository.findOne(templatesUpdateDto.getId());
        if (templates==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
        }
//        validTemplateShopUsedCanUpdate(templatesUpdateDto,templates);
        //可以更新，删除未提交的订单
        orderSubmissionRepository.deleteAllByTempIdAndStatus(templates.getId(),OrderStatus.UNSUBMITTED.getCode());
        covertTemplatesDtoToTemplates(templatesUpdateDto,templates,true);
        templatesRepository.save(templates);

        return getSuccessUpdateResult();
    }

    /**
     * 更新模板商品种类时，如果有已提交状态的订单正在关联，则无法更新
     * @param templatesUpdateDto
     * @param templates
     */
    private void  validTemplateShopUsedCanUpdate(TemplatesUpdateDto templatesUpdateDto,Templates templates){
        //查找正在使用该模板的已提交状态的订单
        List<OrderSubmission> orderSubmissionList = orderSubmissionRepository.findAllByTempIdAndStatus(templates.getId(),OrderStatus.SUBMITTED.getCode());
        //正在使用该模板的已提交的订单code
        String usedOrderSubmission = "";
        if (orderSubmissionList!=null && !orderSubmissionList.isEmpty()){
            for (OrderSubmission orderSubmission:orderSubmissionList){
                usedOrderSubmission +=orderSubmission.getCode()+",";
            }
            usedOrderSubmission = usedOrderSubmission.substring(0,usedOrderSubmission.length()-1);
        }

        List<TemplatesProductsDto> templatesProductsDtoList = templatesUpdateDto.getTemplatesProductsDtoList();
        List<TemplateProducts> templateProductsList =templates.getTemplateProductsList();
        if (!StringUtils.isEmpty(usedOrderSubmission)) {
            if ((templatesProductsDtoList == null && templateProductsList != null) || (templatesProductsDtoList != null && templateProductsList == null)){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATE_USED_CAN_NOT_UPDATE, usedOrderSubmission);
            }
            if (templatesProductsDtoList.size() != templateProductsList.size()) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATE_USED_CAN_NOT_UPDATE, usedOrderSubmission);
            }
            if (!templateProductsList.isEmpty()){
                for (TemplatesProductsDto templatesProductsDto:templatesProductsDtoList){
                    for (TemplateProducts templateProducts: templateProductsList){
                        if (templatesProductsDto.getProductId().equals(templateProducts.getProductId())){
                            templatesProductsDtoList.remove(templatesProductsDto);
                            templateProductsList.remove(templateProducts);
                        }
                    }
                }
                if (templatesProductsDtoList.size()!=0 && templateProductsList.size()!=0){
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATE_USED_CAN_NOT_UPDATE, usedOrderSubmission);
                }
            }
        }
    }

    /**
     * 参数转换器
     * @param templatesAddDto
     * @param templates
     */
    private void covertTemplatesDtoToTemplates(TemplatesAddDto templatesAddDto,Templates templates,Boolean isUpdate){
        BeanUtils.copyProperties(templatesAddDto,templates,"id");
        templates.setIsValid(true);
        if (!isUpdate){
            templates.setCreatedTime(new Date());

        }
        templates.setUpdatedTime(new Date());

        //模板类型
        TemplatesType templatesType = templatesTypeRepository.findOne(templatesAddDto.getTemplatesTypeId());
        if (templatesType == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATE_TYPE_NOT_EXISTS);
        }
        templates.setTemplatesType(templatesType);

        //仓库
        TagwareHouse tagwareHouse = tagwareHouseRepository.findOne(templatesAddDto.getTagwareHouseId());
        if (tagwareHouse == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS);
        }
        templates.setTagwareHouse(tagwareHouse);

        //线路
        List<TagLine> setupsTaglineList = tagLineRepository.findAll(templatesAddDto.getTaglineIdList());
        if (setupsTaglineList!=null && !setupsTaglineList.isEmpty()){
            templates.setTagLineList(setupsTaglineList);
        }


        List<TemplatesProductsDto> templatesProductsDtoList = templatesAddDto.getTemplatesProductsDtoList();
        List<TemplateProducts> saveTemplateProductsList = new ArrayList<>();
        if (templatesProductsDtoList != null && templatesProductsDtoList!= null) {
            for (TemplatesProductsDto templatesProductsDto : templatesProductsDtoList) {
                Products products = productsRepository.findOne(templatesProductsDto.getProductId());
                if (products != null) {
                    TemplateProducts templateProducts = new TemplateProducts();
                    BeanUtils.copyProperties(templatesProductsDto, templateProducts);
                    templateProducts.setTemplates(templates);
                    templateProducts.setProductCount(templatesProductsDto.getAmount());
                    templateProducts.setProducts(products);
                    //判断限购
                    productsService.checkProductsLimit(products, templateProducts.getProductCount());
                    saveTemplateProductsList.add(templateProducts);
                }
            }
        }

        //拥有的商品信息
        List<TemplateProducts> templateProductsList = null;
        if (isUpdate){
            templateProductsList = templates.getTemplateProductsList();
            templateProductsList.clear();
            templateProductsList.addAll(saveTemplateProductsList);
        }else{
            templates.setTemplateProductsList(saveTemplateProductsList);
        }
    }

    /**
     * 统一参数验证
     * @param templatesDto
     */
    private void validateParam(TemplatesAddDto templatesDto){
        if (StringUtils.isEmpty(templatesDto.getName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATES_NAME_EMPTY);
        }
        if(templatesDto.getTagwareHouseId() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATES_WAREHOUSE_EMPTY);
        }
        if(templatesDto.getTaglineIdList()==null || templatesDto.getTaglineIdList().isEmpty()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATES_LINE_EMPTY);
        }
        if (templatesDto.getTemplatesTypeId()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATES_TYPE_EMPTY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<TemplatesVo> findById(Long id) {
        if (id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        Specification<Templates> specification = new Specification<Templates>() {
            @Override
            public Predicate toPredicate(Root<Templates> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class),id));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };

        Templates templates = templatesRepository.findOne(specification);
        TemplatesVo result = covertTemplatesToTemplatesVo(templates);
        return ResponseParam.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam delete(List<Long> ids) {
        if(ids != null && !ids.isEmpty()){
            List<Templates> templatesList = templatesRepository.findAll(ids);
            if (templatesList != null && !templatesList.isEmpty()){
                for (Templates templates: templatesList){
                    validTempUsedCanDelete(templates);
                }
                templatesRepository.delete(templatesList);
            }
        }
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<TemplatesVo>> findByPage(TemplatesQuery templatesQuery) {
        Specification<Templates> specification = new Specification<Templates>() {
            @Override
            public Predicate toPredicate(Root<Templates> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                Predicate baseQuery = criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true);
                predicates.add(baseQuery);

                if (templatesQuery.getIsActive()!=null){
                    Predicate query = criteriaBuilder.equal(root.get("isActive").as(Boolean.class),templatesQuery.getIsActive());
                    predicates.add(query);
                }

                if (templatesQuery.getTemplateTypeId()!=null){
                    Predicate query = criteriaBuilder.equal(root.get("tempTypeId").as(Long.class),templatesQuery.getTemplateTypeId());
                    predicates.add(query);
                }
                if (templatesQuery.getWareHouseId()!=null){
                    Predicate query = criteriaBuilder.equal(root.get("tagWarehouseId").as(Long.class),templatesQuery.getWareHouseId());
                    predicates.add(query);
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        Pageable pageable = new PageRequest(templatesQuery.getPage(),templatesQuery.getPageSize());
        Page<Templates> pageResult = templatesRepository.findAll(specification,pageable);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());
        List<Templates> templatesList = pageResult.getContent();
        List<TemplatesVo> result = new ArrayList<TemplatesVo>();
        for (Templates templates:templatesList){
            TemplatesVo templatesVo = new TemplatesVo();
            BeanUtils.copyProperties(templates,templatesVo);

            TagwareHouse tagwareHouse = templates.getTagwareHouse();
            if (tagwareHouse != null) {
                MapVo tagwareHouseMapVo = new MapVo();
                BeanUtils.copyProperties(tagwareHouse, tagwareHouseMapVo);
                templatesVo.setTagwareHouseMapVo(tagwareHouseMapVo);
            }


            TemplatesType templatesType = templates.getTemplatesType();
            if (templatesType != null) {
                MapVo templatesTypeMapVo = new MapVo();
                BeanUtils.copyProperties(templatesType, templatesTypeMapVo);
                templatesVo.setTemplatesTypeMapVo(templatesTypeMapVo);
            }

            result.add(templatesVo);
        }
        return ResponseParam.success(result,pagination);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateActive(List<TemplatesActiveDto> templatesActiveDtoList) {
        if (templatesActiveDtoList !=null && !templatesActiveDtoList.isEmpty()){
            for (TemplatesActiveDto templatesActiveDto:templatesActiveDtoList){
                if(templatesActiveDto.getId() != null && templatesActiveDto.getIsActive()!=null){
                    Templates templates = templatesRepository.findOne(templatesActiveDto.getId());
                    if (templates==null){
                        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
                    }
                    templates.setIsActive(templatesActiveDto.getIsActive());
                    if (!templatesActiveDto.getIsActive()) {
                        validTempUsedCanDelete(templates);
                    }
                    templatesRepository.save(templates);
                }
            }
        }
        return getSuccessUpdateResult();
    }

    private void validTempUsedCanDelete(Templates templates){
        //查找正在使用该模板的已提交状态的订单
        List<OrderSubmission> orderSubmissionList = orderSubmissionRepository.findAllByTempIdAndStatus(templates.getId(),OrderStatus.SUBMITTED.getCode());
        //正在使用该模板的已提交的订单code
        String usedOrderSubmission = "";
        if (orderSubmissionList!=null && !orderSubmissionList.isEmpty()){
            for (OrderSubmission orderSubmission:orderSubmissionList){
                usedOrderSubmission +=orderSubmission.getCode()+",";
            }
            usedOrderSubmission = usedOrderSubmission.substring(0,usedOrderSubmission.length()-1);
        }

        if (!StringUtils.isEmpty(usedOrderSubmission)){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATE_USED_CAN_NOT_UPDATE, usedOrderSubmission);
        }

        //可以删除，删除关联的未提交的订单
        orderSubmissionRepository.deleteAllByTempIdAndStatus(templates.getId(),OrderStatus.UNSUBMITTED.getCode());
    }

    /**
     * entity，vo转换器
     * @param templates
     * @return
     */
    private TemplatesVo covertTemplatesToTemplatesVo(Templates templates){
        TemplatesVo templatesVo = new TemplatesVo();
        BeanUtils.copyProperties(templates,templatesVo);

        //仓库
        TagwareHouse tagwareHouse = templates.getTagwareHouse();
        if (tagwareHouse != null) {
            MapVo tagwareHouseMapVo = new MapVo();
            BeanUtils.copyProperties(tagwareHouse, tagwareHouseMapVo);
            templatesVo.setTagwareHouseMapVo(tagwareHouseMapVo);
        }

        //模板类型
        TemplatesType templatesType = templates.getTemplatesType();
        if (templatesType != null) {
            MapVo templatesTypeMapVo = new MapVo();
            BeanUtils.copyProperties(templatesType, templatesTypeMapVo);
            templatesVo.setTemplatesTypeMapVo(templatesTypeMapVo);
        }

        //线路
        List<TagLine> setupsTaglineList = templates.getTagLineList();
        List<MapVo> setupsTaglineMapVoList = CovertUtil.copyList(setupsTaglineList,MapVo.class);
        templatesVo.setSetupsTaglineMapVoList(setupsTaglineMapVoList);

        //拥有的商品
        List<TemplateProducts> templateProductsList =templates.getTemplateProductsList();
        List<TemplateProductsVo> templateProductsVoList = new ArrayList<TemplateProductsVo>();
        if (templateProductsList!=null && !templateProductsList.isEmpty()){
            for (TemplateProducts templateProducts: templateProductsList){
                TemplateProductsVo templateProductsVo = new TemplateProductsVo();
                BeanUtils.copyProperties(templateProducts,templateProductsVo,"id");
                templateProductsVo.setAmount(templateProducts.getProductCount());
                Products products = templateProducts.getProducts();
                BeanUtils.copyProperties(products,templateProductsVo);
                templateProductsVo.setProductName(products.getName());
                templateProductsVoList.add(templateProductsVo);
            }
        }
        templatesVo.setTemplateProductsMapVoList(templateProductsVoList);

        return templatesVo;
    }


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<TemplatesVo>> findByShop(Long shopId) {
        ShopsShop shopsShop = shopRepository.findOne(shopId);
        List<TemplatesVo> templatesVoList = new ArrayList<>();

        if (shopsShop == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_NAME_EXISTS);
        }

        //店铺关闭，无法查出模板
        if (!shopsShop.getStatus().equals(ShopStatus.CLOSE.getCode())){
            Boolean isPreparation = false;
            if (shopsShop.getStatus().equals(ShopStatus.PREPARATION.getCode())) {
                isPreparation = true;
            }
            Pathshop pathshop = shopsShop.getPathshop();
            if (pathshop != null) {
                Long tagLineId = pathshop.getPlansPath().getTagLineId();
                List<Templates> templatesList = findByTagline(tagLineId, isPreparation);
                if (templatesList != null && !templatesList.isEmpty()) {
                    for (Templates templates : templatesList) {
                        TemplatesVo templatesVo = covertTemplatesToTemplatesVo(templates);
                        templatesVoList.add(templatesVo);
                    }
                }
            }
        }
        return ResponseParam.success(templatesVoList);
    }

    /**
     * 通过线路查询模板
     * @param id                线路id
     * @param isPreparation    是否筹备
     * @return
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<Templates> findByTagline(Long id,Boolean isPreparation){
        Specification<Templates> specification = new Specification<Templates>() {
            @Override
            public Predicate toPredicate(Root<Templates> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.get("isActive").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.join("tagLineList",JoinType.LEFT).get("id").as(Long.class),id));
                if (isPreparation) {
                    predicates.add(criteriaBuilder.equal(root.get("tempTypeId").as(Integer.class), TemplateType.PREPARATION.getCode()));
                }else {
                    predicates.add(criteriaBuilder.notEqual(root.get("tempTypeId").as(Integer.class), TemplateType.PREPARATION.getCode()));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        return templatesRepository.findAll(specification);
    }


}
