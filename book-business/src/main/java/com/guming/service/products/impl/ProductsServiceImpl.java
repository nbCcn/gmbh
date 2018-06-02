package com.guming.service.products.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.ShopStatus;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.common.utils.FileUtil;
import com.guming.common.utils.OrderUtil;
import com.guming.dao.order.OrderSubmissionRepository;
import com.guming.dao.order.OrderTemplatesSubmissionRepository;
import com.guming.dao.orderTemplate.TemplateProductsRepository;
import com.guming.dao.products.ProductsClassifyRepository;
import com.guming.dao.products.ProductsHistoryRepository;
import com.guming.dao.products.ProductsRepository;
import com.guming.dao.shops.ShopRepository;
import com.guming.dao.tagline.TagLineRepository;
import com.guming.dao.tagrank.TagRankRepository;
import com.guming.dao.tagwareHouse.TagwareHouseRepository;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.entity.OrderTemplatesSubmission;
import com.guming.orderTemplate.dto.TemplatesProductsDto;
import com.guming.orderTemplate.entity.TemplateProducts;
import com.guming.orderTemplate.vo.TemplateProductsVo;
import com.guming.products.dto.ProductsAddDto;
import com.guming.products.dto.ProductsUpdateDto;
import com.guming.products.dto.UndercarriageDto;
import com.guming.products.dto.query.ProductsQuery;
import com.guming.products.entity.Products;
import com.guming.products.entity.ProductsClassify;
import com.guming.products.entity.ProductsHistory;
import com.guming.products.entity.ProductsStatus;
import com.guming.products.model.ProductsImportExportModel;
import com.guming.products.vo.ProductsVo;
import com.guming.service.products.ProductsService;
import com.guming.shops.dto.ShopLineProductsQuery;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.tagline.entity.TagLine;
import com.guming.tagrank.entity.TagRank;
import com.guming.tagwareHouse.entity.TagwareHouse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @Author: PengCheng
 * @Description: 商品
 * @Date: 2018/4/18
 */
@Service
public class ProductsServiceImpl extends BaseServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductsClassifyRepository productsClassifyRepository;

    @Autowired
    private ProductsHistoryRepository productsHistoryRepository;

    @Autowired
    private TagwareHouseRepository tagwareHouseRepository;

    @Autowired
    private TagLineRepository tagLineRepository;

    @Autowired
    private TagRankRepository tagRankRepository;

    @Autowired
    private TemplateProductsRepository templateProductsRepository;

    @Autowired
    private OrderTemplatesSubmissionRepository orderTemplatesSubmissionRepository;

    @Autowired
    private OrderSubmissionRepository orderSubmissionRepository;

    @PersistenceContext(unitName = "primaryPersistenceUnit")
    private EntityManager em;

    @Override
    protected BaseRepository getRepository() {
        return this.productsRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<ProductsVo>> findByPage(ProductsQuery productsQuery) {
        Page<Products> page = findProductsByPage(productsQuery,true);
        List<Products> productsList = page.getContent();
        List<ProductsVo> result =new ArrayList<ProductsVo>();
        Pagination pagination = new Pagination(page.getTotalElements(),productsQuery.getPage(),productsQuery.getPageSize());
        for (Products products:productsList){
            ProductsVo productsVo = covertProductsToProductsVo(products);
            result.add(productsVo);
        }
        return ResponseParam.success(result,pagination);
    }

    private Page<Products> findProductsByPage(ProductsQuery productsQuery, Boolean isPaging){
        Map<String,Object> queryMap = new LinkedHashMap<>();
       StringBuilder querySql =new StringBuilder("select p.* from sys_products p,sys_products_tagwarehouse pw,sys_products_tagline pl " +
               "where 1=1 and p.id = pw.product_id and pl.product_id=p.id ");

//        StringBuilder querySql =new StringBuilder("select p.* from sys_products p left join sys_products_tagwarehouse pw on p.id = pw.product_id " +
//                " left join sys_products_tagline pl on pl.product_id=p.id " +
//                "where 1=1 ");
        if (!StringUtils.isEmpty(productsQuery.getName())){
            querySql.append(" and ( p.name like :name");
            queryMap.put("name","%"+productsQuery.getName()+"%");
            querySql.append(" or p.code like :code ) ");
            queryMap.put("code","%"+productsQuery.getName()+"%");
        }

        if (productsQuery.getClassifyId()!=null){
            querySql.append(" and p.classify_id = :classify ");
            queryMap.put("classify",productsQuery.getClassifyId());
        }

        if (productsQuery.getIsUp() != null){
            querySql.append(" and p.is_up = :isUp ");
            queryMap.put("isUp",productsQuery.getIsUp());
        }

        List<Long> warehouseIds=productsQuery.getWarehouseIds();
        if (warehouseIds!=null && !warehouseIds.isEmpty()){
            querySql.append(" and pw.tagwarehouse_id in :tagwarehouseIds ");
            queryMap.put("tagwarehouseIds",warehouseIds);
        }

        List<Long> tagLineIds = productsQuery.getTagLineIds();
        if(tagLineIds != null && !tagLineIds.isEmpty()){
            querySql.append(" and pl.tagline_id in :taglineIds ");
            queryMap.put("taglineIds",tagLineIds);
        }

        querySql.append(" group by p.id ");

        StringBuilder countQuerySql =new StringBuilder("select count(1) from (").append(querySql).append(") c");
        Query countQuery = em.createNativeQuery(countQuerySql.toString());

        querySql.append(" order by p.sort desc, p.id desc ");

        StringBuilder pageQuerySql = new StringBuilder(querySql);
        if (isPaging) {
            pageQuerySql.append("limit :page,:pageSize");
        }
        Query resultQuery = em.createNativeQuery(pageQuerySql.toString(),Products.class);

        if (queryMap != null && !queryMap.isEmpty()){
            for (Map.Entry<String,Object> entry :queryMap.entrySet()){
                countQuery.setParameter(entry.getKey(),entry.getValue());
                resultQuery.setParameter(entry.getKey(),entry.getValue());
            }
        }

        if (isPaging) {
            resultQuery.setParameter("page", productsQuery.getPage() * productsQuery.getPageSize());
            resultQuery.setParameter("pageSize", productsQuery.getPageSize());
        }
        BigInteger count = (BigInteger) countQuery.getSingleResult();

        List<Products> productsList = resultQuery.getResultList();

        Pageable pageable = new PageRequest(productsQuery.getPage(),productsQuery.getPageSize());
        Page<Products> page = new PageImpl<Products>(productsList,pageable,count.longValue());
        return page;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<ProductsVo> findAllMessageById(Long id) {
        if (id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        //该产品数据
        Products products = productsRepository.findOne(id);
        if (products == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
        }

        ProductsVo productsVo = covertProductsToProductsVo(products);

        //查询出历史商品价格数据
        List<ProductsHistory> productsHistoryList = productsHistoryRepository.findAllByProductId(id);
        productsVo.setProductsHistoryList(productsHistoryList);

        return ResponseParam.success(productsVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<ProductsVo> findById(Long id) {
        if (id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        //该产品数据
        Products products = productsRepository.findOne(id);
        if (products == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
        }

        ProductsVo productsVo = covertProductsToProductsVo(products);
        return ResponseParam.success(productsVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam undercarriage(UndercarriageDto undercarriageDto) {
        if (undercarriageDto != null){
            Products products = productsRepository.findOne(undercarriageDto.getId());
            if (products == null){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
            }

            products.setIsUp(undercarriageDto.getIsUp());
            productsRepository.save(products);

            if (!undercarriageDto.getIsUp()) {
                //下架成功后,同步删除订单模板中的关联数据
                templateProductsRepository.deleteAllByProductId(undercarriageDto.getId());
                //下架成功后,同步删除未过审核的订单中的关联数据
                orderTemplatesSubmissionRepository.deleteAllByProductId(undercarriageDto.getId());
            }
        }
        return getSuccessOperationResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam add(ProductsAddDto productsAddDto) {
        validate(productsAddDto);
        Products products = new Products();
        covertProductsAddDtoToProducts(productsAddDto,products,false);
        productsRepository.save(products);

        return getSuccessAddResult();
    }

    /**
     * 更新商品，如果商品价格变动则保存一份历史价格
     * @param productsUpdateDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam update(ProductsUpdateDto productsUpdateDto) {
        if (productsUpdateDto.getId() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        validate(productsUpdateDto);
        Products products = productsRepository.findOne(productsUpdateDto.getId());
        if (products==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
        }

        //价格变动,将历史价格存入
        ProductsHistory productsHistory = null;
        if (products.getPrice().doubleValue() !=(productsUpdateDto.getPrice().doubleValue())){
            productsHistory = new ProductsHistory();
            productsHistory.setName(products.getName());
            productsHistory.setPrice(products.getPrice());
            productsHistory.setCreateTime(productsHistory.getUpdateTime()==null?new Date():productsHistory.getUpdateTime());
            productsHistory.setUpdateTime(new Date());
            productsHistory.setProductId(products.getId());
            productsHistory.setVersion(OrderUtil.versionGenerate());
        }


        covertProductsAddDtoToProducts(productsUpdateDto,products,true);
        productsRepository.save(products);

        if (productsHistory != null){
            productsHistoryRepository.save(productsHistory);
        }

        //模板中数量更新
        List<TemplatesProductsDto> templatesProductsDtoList = productsUpdateDto.getTemplatesProductsDtoList();
        if (templatesProductsDtoList != null && !templatesProductsDtoList.isEmpty()) {
            List<TemplateProducts> templateProductsList = new ArrayList<TemplateProducts>();
            for (TemplatesProductsDto templatesProductsDto : templatesProductsDtoList) {
                TemplateProducts templateProducts = templateProductsRepository.findOne(templatesProductsDto.getId());
                if (templateProducts != null){
                    templateProducts.setProductCount(templatesProductsDto.getAmount());
                    templateProductsList.add(templateProducts);
                }
            }
            templateProductsRepository.save(templateProductsList);
        }
        return getSuccessUpdateResult();
    }

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<Products> findProductsByTagLineId(Long tagLineId){
        Specification<Products> specification = new Specification<Products>() {
            @Override
            public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("isUp").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.join("tagLineList",JoinType.LEFT).get("id").as(Long.class),tagLineId));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        return productsRepository.findAll(specification);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam delete(List<Long> ids) {
        if (ids != null && !ids.isEmpty()){
            for (Long id : ids){
                Products products = productsRepository.findOne(id);
                if (products != null) {
                    if (products.getIsUp()) {
                        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_NOT_UNDERCARRIAGE);
                    }
                    productsRepository.delete(id);
                    //删除成功后，删除所有的历史价格数据
                    productsHistoryRepository.deleteAllByProductId(id);
                    //删除成功后,同步删除订单模板中的关联数据
                    templateProductsRepository.deleteAllByProductId(id);
                    //下架成功后,同步删除未过审核的订单中的关联数据
                    orderTemplatesSubmissionRepository.deleteAllByProductId(id);
                }
            }
        }
        return getSuccessDeleteResult();
    }

    /**
     * 参数转换器
     * @param productsAddDto
     * @param products
     */
    private void  covertProductsAddDtoToProducts(ProductsAddDto productsAddDto,Products products,Boolean isUpdate){
        if(productsAddDto != null){
            BeanUtils.copyProperties(productsAddDto,products,"id");
            products.setIsValid(true);
            products.setUpdatedTime(new Date());
            if (!isUpdate){
                products.setCreatedTime(new Date());
            }

            //商品分类
            ProductsClassify productsClassify = productsClassifyRepository.findOne(productsAddDto.getProductsClassifyId());
            if(productsClassify == null){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CLASS_NOT_EXISTS);
            }
            products.setProductsClassify(productsClassify);

            //店铺等级
            List<TagRank> tagRankList = tagRankRepository.findAll(productsAddDto.getTagrankIdList());
            if(tagRankList == null || tagRankList.isEmpty()){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS);
            }
            products.setTagRankList(tagRankList);

            //店铺状态

            List<Long> shopStatusIdList = productsAddDto.getShopStatusIdList();
            if (shopStatusIdList != null && !shopStatusIdList.isEmpty()){
                List<ProductsStatus> productsStatusList;
                if (!isUpdate) {
                    productsStatusList = new ArrayList<>();
                }else{
                    productsStatusList =products.getProductsStatusList();
                    productsStatusList.clear();
                }
                for (Long shopStatusId : shopStatusIdList){
                    ProductsStatus productsStatus = new ProductsStatus();
                    productsStatus.setProductId(products.getId());
                    productsStatus.setShopStatus(shopStatusId.intValue());
                    productsStatusList.add(productsStatus);
                }
                products.setProductsStatusList(productsStatusList);
            }

            //仓库
            List<TagwareHouse> tagwareHouseList = tagwareHouseRepository.findAll(productsAddDto.getWarehouseIdList());
            if (tagwareHouseList == null || tagwareHouseList.isEmpty()){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS);
            }
            products.setTagwareHouseList(tagwareHouseList);

            //过滤线路
            List<TagLine> tagLineList= null;
            if(productsAddDto.getNeedTagLines()){
                List<Long> taglineIdList =productsAddDto.getTaglineIdList();
                if (taglineIdList!=null && !taglineIdList.isEmpty()){
                    tagLineList = tagLineRepository.findAll(taglineIdList);
                }
            }else{
                tagLineList = tagLineRepository.findByTagwareHouseIds(productsAddDto.getWarehouseIdList());
            }
            products.setTagLineList(tagLineList);

        }
    }

    /**
     * 添加更新时的参数验证
     * @param productsAddDto
     */
    private void validate(ProductsAddDto productsAddDto){
        if (StringUtils.isEmpty(productsAddDto.getCode())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_CODE_EMPTY);
        }

        if (StringUtils.isEmpty(productsAddDto.getName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_NAME_EMPTY);
        }

        if (StringUtils.isEmpty(productsAddDto.getSpec())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_SPEC_EMPTY);
        }

        if (StringUtils.isEmpty(productsAddDto.getUnit())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_UNIT_EMPTY);
        }

        if (productsAddDto.getPrice()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_PRICE_EMPTY);
        }

        if (productsAddDto.getStock()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_STOCK_EMPTY);
        }

        if (productsAddDto.getLimit()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_LIMIT_EMPTY);
        }

        if (productsAddDto.getStep()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_STEP_EMPTY);
        }

        if(productsAddDto.getNeedTagLines().equals(true) && (productsAddDto.getTaglineIdList()==null || productsAddDto.getTaglineIdList().isEmpty())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_NEED_LINE_NOT_SELECT);
        }
    }

    private ProductsVo covertProductsToProductsVo(Products products){
        ProductsVo productsVo = new ProductsVo();
        BeanUtils.copyProperties(products,productsVo);
        //商品分類
        ProductsClassify productsClassify = products.getProductsClassify();
        if (productsClassify!=null){
            MapVo mapVo = new MapVo();
            BeanUtils.copyProperties(productsClassify,mapVo);
            productsVo.setProductsClassifyMapVo(mapVo);
        }

        //倉庫信息
        List<TagwareHouse> tagwareHouseList = products.getTagwareHouseList();
        productsVo.setWarehouseMapVoList( CovertUtil.copyList(tagwareHouseList,MapVo.class));

        //支持的店铺等级
        List<TagRank> tagRankList = products.getTagRankList();
        productsVo.setSetupsTagrankMapVoList(CovertUtil.copyList(tagRankList,MapVo.class));

        //支持的店铺状态
        List<ProductsStatus> productsStatusList = products.getProductsStatusList();
        List<MapVo> shopStatusMapVoList = new ArrayList<MapVo>();
        if(!productsStatusList.isEmpty() && productsStatusList!=null){
            for (ProductsStatus productsStatus : productsStatusList){
                Integer shopStatus = productsStatus.getShopStatus();
                shopStatusMapVoList.add(new MapVo(shopStatus.longValue(),i18nHandler(ShopStatus.getShopStatus(shopStatus).getI18N())));
            }
            productsVo.setShopStatusMapVoList(shopStatusMapVoList);
        }


        //过滤的路线组信息
        if (products.getNeedTagLines()){
            List<TagLine> tagLineList =products.getTagLineList();
            productsVo.setTaglineMapVoList(CovertUtil.copyList(tagLineList,MapVo.class));
        }

        //关联的订单模板数据
        List<TemplateProducts> templateProductsList = products.getTemplateProductsList();
        List<TemplateProductsVo> templateProductsVoList = new ArrayList<>();
        if (templateProductsList != null && !templateProductsList.isEmpty()){
            for (TemplateProducts templateProducts:templateProductsList){
                TemplateProductsVo templateProductsVo = new TemplateProductsVo();
                BeanUtils.copyProperties(templateProducts,templateProductsVo);
                templateProductsVo.setAmount(templateProducts.getProductCount());
                templateProductsVo.setTemplateName(templateProducts.getTemplates().getName());
                templateProductsVo.setTemplateId(templateProducts.getTemplateId());
                templateProductsVoList.add(templateProductsVo);
            }
        }
        productsVo.setTemplateProductsVoList(templateProductsVoList);
        return productsVo;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<TemplateProductsVo>> findProducesByTempIdAndShopId(ShopLineProductsQuery shopLineProductsQuery) {
        if (shopLineProductsQuery.getShopId() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_ID_EMPTY);
        }
        if (shopLineProductsQuery.getTempId() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATE_ID_EMPTY);
        }
        ShopsShop shopsShop = shopRepository.findOne(shopLineProductsQuery.getShopId());
        Integer shopStatus = shopsShop.getStatus();
        Specification<Products> specification = new Specification<Products>() {
            @Override
            public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.join("productsStatusList",JoinType.LEFT).get("shopStatus").as(Integer.class),shopStatus));
                predicates.add(criteriaBuilder.equal(root.join("templateProductsList",JoinType.LEFT).get("templateId").as(Long.class),shopLineProductsQuery.getTempId()));
                if (!StringUtils.isEmpty(shopLineProductsQuery.getProductName())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class),shopLineProductsQuery.getProductName()));
                }
                return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        List<Products> productsList = productsRepository.findAll(specification);

        List<TemplateProductsVo> templateProductsVoList = getCartCountData(productsList,shopLineProductsQuery.getShopId(),shopLineProductsQuery.getTempId());
        return ResponseParam.success(templateProductsVoList);
    }

    @Override
    public void checkProductsLimit(Products products, Integer amount){
        if (bookConfig.getProductLimitStat()) {
            if (amount > products.getLimit()) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_PRODUCTS_OVER_LIMIT,products.getName());
            }
        }
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<TemplateProductsVo>> findTagLineTemplateProducesByShopId(ShopLineProductsQuery shopLineProductsQuery) {
        Long shopId = shopLineProductsQuery.getShopId();
        Long tempId = shopLineProductsQuery.getTempId();
        if (shopId == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        if (tempId == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATE_ID_EMPTY);
        }

        ShopsShop shopsShop = shopRepository.findOne(shopId);
        Integer shopStatus = shopsShop.getStatus();
        Long tagLineId = shopsShop.getPathshop().getPlansPath().getTagLine().getId();

        Specification<Products> specification = new Specification<Products>() {
            @Override
            public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("isUp").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.join("tagLineList").get("id").as(Long.class),tagLineId));
                predicates.add(criteriaBuilder.equal(root.join("productsStatusList").get("shopStatus").as(Integer.class),shopStatus));
                if (!StringUtils.isEmpty(shopLineProductsQuery.getProductName())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+shopLineProductsQuery.getProductName()+"%"));
                }
                if (shopLineProductsQuery.getProductClassId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("classifyId").as(Long.class),shopLineProductsQuery.getProductClassId()));
                }
                return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };

        Pageable pageable = new PageRequest(shopLineProductsQuery.getPage(),shopLineProductsQuery.getPageSize());
        Page<Products> pageResult =  productsRepository.findAll(specification,pageable);
        List<Products> productsList = pageResult.getContent();
        List<TemplateProductsVo> templateProductsVoList = getCartCountData(productsList,shopId,tempId);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());
        return ResponseParam.success(templateProductsVoList,pagination);
    }

    /**
     * 通过购物车获取商品下的数量
     * @param productsList     需要查询的商品
     * @param shopId            商品id
     * @param tempId            模板id
     * @return
     */
    private List<TemplateProductsVo> getCartCountData(List<Products> productsList,Long shopId,Long tempId){
        List<TemplateProductsVo> templateProductsVoList = new ArrayList<>();
        if (productsList != null && !productsList.isEmpty()){
            //获取购物车
            OrderSubmission orderCart=orderSubmissionRepository.findCartByShopIdAndTempId(shopId,tempId);
            for (Products products:productsList){
                TemplateProductsVo templateProductsVo = new TemplateProductsVo();
                BeanUtils.copyProperties(products,templateProductsVo);
                templateProductsVo.setClassifyName(products.getProductsClassify().getName());
                templateProductsVo.setProductName(products.getName());
                templateProductsVo.setAmount(0);
                if (orderCart != null) {
                    List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderCart.getOrderTemplatesSubmissionList();
                    if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()) {
                        for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList) {
                            if (orderTemplatesSubmission.getProductId().equals(products.getId())) {
                                templateProductsVo.setAmount(orderTemplatesSubmission.getAmount());
                            }
                        }
                    }
                }
                templateProductsVoList.add(templateProductsVo);
            }
        }
        return templateProductsVoList;
    }

    @Override
    public void importExcel(MultipartFile multipartFile) {
        //先生成唯一的缓存文件
        String fileName = bookConfig.getUploadTempPath()+ UUID.randomUUID().toString().replace("-","") +multipartFile.getOriginalFilename();
        FileUtil.uploadFile(multipartFile,fileName);
    }


    private List<ProductsImportExportModel> covertEntityToExportModel(List<Products> productsList){
        List<ProductsImportExportModel> productsImportExportModelList = new ArrayList<>();
        if (productsList != null && !productsList.isEmpty()){
            for (Products products: productsList){
                ProductsImportExportModel productsImportExportModel = new ProductsImportExportModel();
                BeanUtils.copyProperties(products,productsImportExportModel);
                productsImportExportModel.setProductsClassify(products.getProductsClassify().getName());

                List<TagRank> tagRankList = products.getTagRankList();
                if (tagRankList != null && !tagRankList.isEmpty()){
                    List<String> tankList = new ArrayList<>();
                    for (TagRank tagRank:tagRankList){
                        tankList.add(tagRank.getName());
                    }
                    productsImportExportModel.setTagRankList(tankList);
                }

                List<ProductsStatus> productsStatusList = products.getProductsStatusList();
                if (productsStatusList != null && !productsStatusList.isEmpty()){
                    List<String> statusList = new ArrayList<>();
                    for (ProductsStatus productsStatus:productsStatusList){
                        statusList.add(i18nHandler(ShopStatus.getShopStatus(productsStatus.getShopStatus()).getI18N()));
                    }
                    productsImportExportModel.setStatusList(statusList);
                }

                List<TagwareHouse> tagwareHouseList = products.getTagwareHouseList();
                if (tagwareHouseList != null && !tagwareHouseList.isEmpty()){
                    List<String> tagWarehouseList = new ArrayList<>();
                    for (TagwareHouse tagwareHouse:tagwareHouseList){
                        tagWarehouseList.add(tagwareHouse.getName());
                    }
                    productsImportExportModel.setTagWarehouseList(tagWarehouseList);
                }


                if(products.getNeedTagLines()){
                    List<TagLine> tagLineList = products.getTagLineList();
                    if (tagLineList != null && !tagLineList.isEmpty()){
                        List<String> lineList = new ArrayList<>();
                        for (TagLine tagLine:tagLineList){
                            lineList.add(tagLine.getName());
                        }
                        productsImportExportModel.setTagLineList(lineList);
                    }
                }
                productsImportExportModelList.add(productsImportExportModel);
            }
        }
        return productsImportExportModelList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProductTagLineWitchNotFilterLineByWareHouse(Long tagWareHouseId,TagLine needAddTagLine){
        List<Products> productsList = productsRepository.findWareHouseProductWithOutFilterLine(tagWareHouseId);
        if (productsList!=null && !productsList.isEmpty()){
            for (Products products : productsList){
                List<TagLine> tagLineList = products.getTagLineList();
                if (tagLineList == null){
                    tagLineList = new ArrayList<>();
                }
                tagLineList.add(needAddTagLine);
            }
            productsRepository.save(productsList);
        }
    }
}
