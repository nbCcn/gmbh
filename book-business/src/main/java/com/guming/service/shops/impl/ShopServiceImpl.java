package com.guming.service.shops.impl;

import com.guming.authority.entity.Role;
import com.guming.authority.entity.User;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.ShopStatus;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.common.utils.DateUtil;
import com.guming.common.utils.ExcelUtil;
import com.guming.common.utils.PBKDF2PasswordHasher;
import com.guming.config.BookConfig;
import com.guming.dao.authority.RoleRepository;
import com.guming.dao.authority.UserRepository;
import com.guming.dao.shops.ShopRepository;
import com.guming.dao.tagrank.TagRankRepository;
import com.guming.dao.tagwareHouse.TagwareHouseRepository;
import com.guming.plans.entity.Pathshop;
import com.guming.plans.entity.PlansPath;
import com.guming.service.shops.ShopService;
import com.guming.shops.dto.ShopAddDto;
import com.guming.shops.dto.ShopUpdateDto;
import com.guming.shops.dto.ShopUserDto;
import com.guming.shops.dto.query.ShopQuery;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.shops.vo.ExcelShopVo;
import com.guming.shops.vo.ShopUserVo;
import com.guming.shops.vo.ShopVo;
import com.guming.tagrank.entity.TagRank;
import com.guming.tagwareHouse.entity.TagwareHouse;
import com.guming.tagwareHouse.vo.TagwareHouseVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Auther: Ccn
 * @Description:店铺模块服务层
 * @Date: 2018/4/12 14:57
 */
@Service
@SuppressWarnings("all")
public class ShopServiceImpl extends BaseServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private TagwareHouseRepository tagwareHouseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookConfig bookConfig;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TagRankRepository tagRankRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.shopRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseParam findShops(ShopQuery shopDto) {
        Pageable pageable = new PageRequest(shopDto.getPage(), shopDto.getPageSize());
        Specification<ShopsShop> specification = new Specification<ShopsShop>() {
            @Override
            public Predicate toPredicate(Root<ShopsShop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(shopDto.getSearch())) {
                    list.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("name").as(String.class), "%" + shopDto.getSearch() + "%"),
                                    criteriaBuilder.like(root.get("phone").as(String.class), "%" + shopDto.getSearch() + "%"),
                                    criteriaBuilder.like(root.get("address").as(String.class), "%" + shopDto.getSearch() + "%")));
                }
                if (shopDto.getStatus() != null) {
                    list.add(criteriaBuilder.equal(root.get("status").as(String.class), shopDto.getStatus()));
                }
                if (shopDto.getStartTime() != null && shopDto.getEndTime() != null) {
                    list.add(criteriaBuilder.between(root.get("joinedTime"), DateUtil.parseDate(shopDto.getStartTime()), DateUtil.parseDate(shopDto.getEndTime())));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Integer.class), 0));
                Predicate[] predicates = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(predicates));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                return criteriaQuery.getRestriction();
            }
        };
        Page<ShopsShop> resultPage = shopRepository.findAll(specification, pageable);
        Pagination pagination = new Pagination(resultPage.getTotalElements(), resultPage.getNumber(), resultPage.getSize());
        List<ShopVo> shopVoList = new ArrayList<ShopVo>();
        List<ShopsShop> shopList = resultPage.getContent();
        if (shopList != null && !shopList.isEmpty()) {
            for (ShopsShop shop : shopList) {
                ShopVo shopVo = new ShopVo();
                BeanUtils.copyProperties(shop, shopVo);
                shopVo.setStatusStr(ShopStatus.getShopStatus(shop.getStatus()).getName());
                shopVo.setAddress(shop.getProvince() + shop.getCity() + shop.getDistrict() + shop.getAddress());
                List<TagwareHouseVo> tagwareHouseVoList = new ArrayList<>();
                Set<TagwareHouse> tagwareHouseSet = shop.getTagwareHouseSet();
                for (TagwareHouse tagwareHouse : tagwareHouseSet) {
                    TagwareHouseVo tagwareHouseVo = new TagwareHouseVo();
                    BeanUtils.copyProperties(tagwareHouse, tagwareHouseVo);
                    tagwareHouseVoList.add(tagwareHouseVo);
                }
                TagRank tagRank = shop.getTagRank();
                shopVo.setTagrankName(tagRank.getName());
                shopVo.setTagwareHouseVoList(tagwareHouseVoList);
                shopVoList.add(shopVo);
            }
        }
        return ResponseParam.success(shopVoList, pagination);
    }


    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam addShop(ShopAddDto addDto) {
        if (addDto.getTagwareHouseList().size() > 1) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_ID_EMPTY);
        }
        if (StringUtils.isBlank(addDto.getName()) || addDto.getName() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_NAME_EMPTY);
        }
        if (StringUtils.isBlank(addDto.getCode())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_CODE_EMPTY);
        }
        if (StringUtils.isBlank(addDto.getProvince())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_PROVINCE_EMPTY);
        }
        if (StringUtils.isBlank(addDto.getAddress())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_ADDRESS_EMPTY);
        }
        if (addDto.getLng() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_LNG_EMPTY);
        }
        if (addDto.getLat() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_LAT_EMPTY);
        }
        if (StringUtils.isBlank(addDto.getContact())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_CONTACT_EMPTY);
        }
        if (StringUtils.isBlank(addDto.getPhone())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_PHONE_EMPTY);
        }
        if (addDto.getStatus() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_STATUS_EMPTY);
        }
        Long count = shopRepository.countByName(addDto.getName());
        if (count != null && count >= 1) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_NAME_EXISTS);
        }
        ShopsShop shop = new ShopsShop();
        BeanUtils.copyProperties(addDto, shop);
        shop.setCreatedTime(new Date());
        shop.setUpdatedTime(new Date());
        shop.setIsDeleted(false);
        //根据仓库ID查询仓库,回存Shop
        List<Long> tagwareHouseIds = addDto.getTagwareHouseList();
        shop.setTagwareHouseSet(new HashSet<>());
        for (Long id : tagwareHouseIds) {
            TagwareHouse tagwareHouse = tagwareHouseRepository.findById(id);
            shop.getTagwareHouseSet().add(tagwareHouse);
        }
        //根据等级查询,回存Shop
        Long tagrankId = addDto.getTagrankId();
        TagRank tagRank = tagRankRepository.findOne(tagrankId);
        if (tagRank == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS);
        }
        shop.setTagRank(tagRank);

        //新增用户
        List<ShopUserDto> userDtos = addDto.getUserDtos();
        if (userDtos == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_USER_EXISTS);
        }
        List<User> userList = new ArrayList<>();
        User user;
        for (ShopUserDto shopUserDto : userDtos) {
            // 根据手机号查询用于，如果已有则关联
            user = userRepository.findUserByUserName(shopUserDto.getPhone());
            if (user != null) {
                // 用户存在更新替换
                user.setRoleList(null);

                Role role = roleRepository.findOne(shopUserDto.getRoleId());
                if (role == null) {
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_EXISTS);
                }
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                user.setRoleList(roleList);

                userList.add(user);
                userRepository.save(user);

            } else {
                user = new User();
                user.setUserName(shopUserDto.getPhone());
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                if (StringUtils.isEmpty(shopUserDto.getUserPass())) {
                    user.setUserPass(new PBKDF2PasswordHasher().encode(bookConfig.getInitialPassword()));
                } else {
                    user.setUserPass(new PBKDF2PasswordHasher().encode(shopUserDto.getUserPass()));
                }
                user.setIsStaff(false);
                user.setIsActive(shopUserDto.getIsActive());
                user.setIsSuperuser(false);

                Role role = roleRepository.findOne(shopUserDto.getRoleId());
                if (role == null) {
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_EXISTS);
                }
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                user.setRoleList(roleList);

                userList.add(user);
                userRepository.save(user);

            }
        }
        shop.setUserList(userList);

        shopRepository.save(shop);
        return getSuccessAddResult();
    }


    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam<?> deleteShop(List<Long> ids) {
        if (ids == null && ids.isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_NOT_EXISTS_DELETE);
        }
        shopRepository.deleteByIds(ids);
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam<?> updateShop(ShopUpdateDto updateDto) {
        if (updateDto.getId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_ID_EMPTY);
        }
        if (updateDto.getTagwareHouseList() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS);
        }
        ShopsShop shop = shopRepository.findOne(updateDto.getId());
        if (shop == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_NOT_EXISTS);
        }
        BeanUtils.copyProperties(updateDto, shop, new String[]{"createdTime"});
        shop.setUpdatedTime(new Date());
        List<Long> tagwareHouseIds = updateDto.getTagwareHouseList();
        shop.setTagwareHouseSet(new HashSet<>());
        for (Long id : tagwareHouseIds) {
            TagwareHouse tagwareHouse = tagwareHouseRepository.findById(id);
            shop.getTagwareHouseSet().add(tagwareHouse);
        }

        //根据等级查询,回存Shop
        Long tagrankId = updateDto.getTagrankId();
        TagRank tagRank = tagRankRepository.findOne(tagrankId);
        if (tagRank == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS);
        }
        shop.setTagRank(tagRank);


        List<ShopUserDto> userDtos = updateDto.getUserDtos();
        if (userDtos == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_USER_NOT_EXISTS);
        }
        List<User> userList = new ArrayList<>();
        User user;
        for (ShopUserDto shopUserDto : userDtos) {
            if (shopUserDto.getUserId() == null) {
                user = userRepository.findUserByUserName(shopUserDto.getPhone());
                if (user == null) {
                    user = new User();
                    user.setUserName(shopUserDto.getPhone());
                    user.setPhone(shopUserDto.getPhone());
                    user.setFirstName(updateDto.getContact());
                    user.setUpdateTime(new Date());
                    if (StringUtils.isEmpty(shopUserDto.getUserPass())) {
                        user.setUserPass(new PBKDF2PasswordHasher().encode(bookConfig.getInitialPassword()));
                    } else {
                        user.setUserPass(new PBKDF2PasswordHasher().encode(shopUserDto.getUserPass()));
                    }
                    user.setIsStaff(false);
                    user.setIsActive(shopUserDto.getIsActive());
                    user.setIsSuperuser(false);

                    Role role = roleRepository.findOne(shopUserDto.getRoleId());
                    if (role == null) {
                        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_EXISTS);
                    }
                    List<Role> roleList = new ArrayList<>();
                    roleList.add(role);
                    user.setRoleList(roleList);

                    userList.add(user);
                    userRepository.save(user);
                } else {
                    user.setUserName(shopUserDto.getPhone());
                    user.setPhone(shopUserDto.getPhone());
                    user.setFirstName(updateDto.getContact());
                    user.setUpdateTime(new Date());
                    if (StringUtils.isEmpty(shopUserDto.getUserPass())) {
                        user.setUserPass(new PBKDF2PasswordHasher().encode(bookConfig.getInitialPassword()));
                    } else {
                        user.setUserPass(new PBKDF2PasswordHasher().encode(shopUserDto.getUserPass()));
                    }
                    user.setIsStaff(false);
                    user.setIsActive(shopUserDto.getIsActive());
                    user.setIsSuperuser(false);

                    Role role = roleRepository.findOne(shopUserDto.getRoleId());
                    if (role == null) {
                        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_EXISTS);
                    }
                    List<Role> roleList = new ArrayList<>();
                    roleList.add(role);
                    user.setRoleList(roleList);

                    userList.add(user);
                    userRepository.save(user);
                }


            } else {
                Long userId = shopUserDto.getUserId();
                user = userRepository.findOne(userId);
                user.setUserName(shopUserDto.getPhone());
                user.setPhone(shopUserDto.getPhone());
                user.setFirstName(updateDto.getContact());
                user.setUpdateTime(new Date());
                if (StringUtils.isEmpty(shopUserDto.getUserPass())) {
                    user.setUserPass(new PBKDF2PasswordHasher().encode(bookConfig.getInitialPassword()));
                } else {
                    user.setUserPass(new PBKDF2PasswordHasher().encode(shopUserDto.getUserPass()));
                }
                user.setIsStaff(false);
                user.setIsActive(shopUserDto.getIsActive());
                user.setIsSuperuser(false);

                Role role = roleRepository.findOne(shopUserDto.getRoleId());
                if (role == null) {
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_EXISTS);
                }
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                user.setRoleList(roleList);

                userList.add(user);
                userRepository.save(user);
            }
            shop.setUserList(userList);
        }
        shopRepository.save(shop);
        return getSuccessUpdateResult();
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseParam<?> findById(Long id) {
        if (id == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_CLASS_ID_EMPTY);
        }
        ShopsShop shop = shopRepository.findOne(id);
        ShopVo shopVo = new ShopVo();
        shopVo.setTagwareHouseVoList(new ArrayList<>());
        BeanUtils.copyProperties(shop, shopVo);
        shopVo.setStatusStr(ShopStatus.getShopStatus(shop.getStatus()).getName());
        Set<TagwareHouse> tagwareHouseSet = shop.getTagwareHouseSet();
        if (tagwareHouseSet != null && !tagwareHouseSet.isEmpty()) {
            for (TagwareHouse tagwareHouse : tagwareHouseSet) {
                TagwareHouseVo tagwareHouseVo = new TagwareHouseVo();

                BeanUtils.copyProperties(tagwareHouse, tagwareHouseVo);
                shopVo.getTagwareHouseVoList().add(tagwareHouseVo);
            }
        }


        TagRank tagRank = shop.getTagRank();
        if (tagRank == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS);
        }
        shopVo.setTagrankId(tagRank.getId());
        shopVo.setTagrankName(tagRank.getName());

        List<User> userList = shop.getUserList();
        List<ShopUserVo> shopUserVoList = new ArrayList<>();
        for (User user : userList) {
            ShopUserVo shopUserVo = new ShopUserVo();
            shopUserVo.setUserId(user.getId());
            shopUserVo.setPhone(user.getUserName());
            shopUserVo.setUserPass(user.getUserPass());
            List<Role> roleList = user.getRoleList();
            for (Role role : roleList) {
                shopUserVo.setRoleId(role.getId());
                shopUserVo.setRoleName(role.getRoleName());
            }
            shopUserVoList.add(shopUserVo);
        }

        shopVo.setUserVoList(shopUserVoList);
        return ResponseParam.success(shopVo);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ShopVo> getRecipientShopMapVoList(Long userId, Long shopId) {
        //获取当前店铺不存在时，返回空数据
        ShopsShop shop = shopRepository.findOne(shopId);
        List<MapVo> mapVoList = new ArrayList<MapVo>();
        if (shop == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_USER_NOT_MATCH);
        }
        List<User> userList = shop.getUserList();
        if (userList == null || userList.isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_USER_NOT_MATCH);
        }

        Boolean flag = false;
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_USER_NOT_MATCH);
        }

        Specification<ShopsShop> specification = new Specification<ShopsShop>() {
            @Override
            public Predicate toPredicate(Root<ShopsShop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                Pathshop pathshop = shop.getPathshop();
                if (pathshop != null) {
                    PlansPath plansPath = pathshop.getPlansPath();
                    if (plansPath != null) {
                        list.add(criteriaBuilder.equal(root.join("pathshop", JoinType.LEFT).get("pathId").as(Long.class), plansPath.getId()));
                    }
                }

                list.add(criteriaBuilder.equal(root.join("userList", JoinType.LEFT).get("id").as(Long.class), userId));
                return criteriaQuery.where(list.toArray(new Predicate[list.size()])).groupBy(root.get("id").as(Long.class)).getRestriction();
            }
        };

        List<ShopsShop> shopsShopList = shopRepository.findAll(specification);

        if (shopsShopList != null && !shopsShopList.isEmpty()) {
            for (ShopsShop shopsShop : shopsShopList) {
                mapVoList.add(new MapVo(shopsShop.getId(), shopsShop.getName()));
            }
        }
        return CovertUtil.copyList(shopsShopList, ShopVo.class);
    }


    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ResponseParam<List<ShopVo>> findCurrentUserShops(User user) {
        Specification<ShopsShop> specification = new Specification<ShopsShop>() {
            @Override
            public Predicate toPredicate(Root<ShopsShop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.join("userList").get("id").as(Long.class), user.getId()));
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };

        List<ShopsShop> shopsShopList = shopRepository.findAll(specification);
        List<ShopVo> shopVoList = new ArrayList<>();
        if (shopsShopList != null && !shopsShopList.isEmpty()) {
            for (ShopsShop shopsShop : shopsShopList) {
                shopVoList.add(covertShopToShopVo(shopsShop));
            }
        }
        return ResponseParam.success(shopVoList);
    }

    private ShopVo covertShopToShopVo(ShopsShop shopsShop) {
        ShopVo shopVo = new ShopVo();
        BeanUtils.copyProperties(shopsShop, shopVo);
        shopVo.setStatusStr(i18nHandler(ShopStatus.getShopStatus(shopsShop.getStatus()).getI18N()));
        return shopVo;
    }

    @Override
    public ResponseParam<List<MapVo>> getShopStatusList() {
        List<MapVo> mapVoList = new ArrayList<>();
        for (ShopStatus shopStatus : ShopStatus.values()) {
            mapVoList.add(new MapVo(shopStatus.getCode().longValue(), i18nHandler(shopStatus.getI18N())));
        }
        return ResponseParam.success(mapVoList);
    }

    @Override
    public ResponseParam<List<MapVo>> getShopUserList() {
        List<MapVo> mapVoList = new ArrayList<>();
        List<Role> shopRole = roleRepository.findShopRole();
        for (Role role : shopRole) {
            mapVoList.add(new MapVo(role.getId(), role.getRoleName()));
        }
        return ResponseParam.success(mapVoList);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseParam<List<MapVo>> getShopTagrankList() {
        List<MapVo> mapVoList = new ArrayList<>();
        List<TagRank> tagRankList = tagRankRepository.findAll();
        for (TagRank tagRank : tagRankList) {
            mapVoList.add(new MapVo(tagRank.getId(), tagRank.getName()));
        }
        return ResponseParam.success(mapVoList);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam resetPass(IdDto idDto) {
        if (idDto.getId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_ID_EMPTY);
        }
        User user = userRepository.findOne(idDto.getId());

        if (user == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_EXISTS);
        }

        user.setUserPass(new PBKDF2PasswordHasher().encode(bookConfig.getInitialPassword()));
        user.setUpdateTime(new Date());

        userRepository.save(user);
        return getSuccessUpdateResult();
    }


    /**
     * @param uploadFile
     * @return
     */

    private Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam<?> importShop(MultipartFile uploadFile) {
        File uploadDir = new File("/usr/local/book/upload/");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        // 服务器路径  /usr/local/book/upload/
        // 本地路径   C:/Users/13136/Desktop/upload/
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        //新建一个文件
        File tempFile = new File("/usr/local/book/upload/" + System.currentTimeMillis() + ".xlsx");
        //初始化输入流
        InputStream is = null;
        try {
            //将上传的文件写入新建的文件中
            uploadFile.transferTo(tempFile);

            //根据新建的文件实例化输入流
            is = new FileInputStream(tempFile);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if (ExcelUtil.isExcel2007(uploadFile.getOriginalFilename())) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }

            ExcelUtil<ExcelShopVo> excelUtil = new ExcelUtil<ExcelShopVo>(ExcelShopVo.class);

            List<ExcelShopVo> shopSheet = excelUtil.importExcel("Sheet1", wb);

            //删除上传的临时文件
            if (tempFile.exists()) {
                tempFile.delete();
            }

            ShopsShop shop;
            for (ExcelShopVo excelShopVo : shopSheet) {
                shop = shopRepository.findShopsShopByCode(excelShopVo.getCode());
                if (shop == null) {
                    shop = new ShopsShop();
                }
                BeanUtils.copyProperties(excelShopVo, shop);
                shop.setStatus(ShopStatus.getStatusCode(excelShopVo.getStatusStr()));
                shop.setCreatedTime(new Date());
                shop.setUpdatedTime(new Date());
                shop.setIsDeleted(false);

                TagwareHouse tagwareHouse = tagwareHouseRepository.findByName(excelShopVo.getTagwareHouse());
                if (tagwareHouse == null) {
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS);
                }
                Set<TagwareHouse> set = new HashSet<>();
                set.add(tagwareHouse);
                shop.setTagwareHouseSet(set);

                TagRank tagRank = tagRankRepository.findByName(excelShopVo.getTagrankStr());
                if (tagRank == null) {
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS);
                }
                shop.setTagRank(tagRank);

                List<User> list = new ArrayList<>();
                Class clazz = excelShopVo.getClass();
                for (int i = 1; i < 6; i++) {
                    //第一个参数是被调用方法的名称，后面接着这个方法的形参类型
                    PropertyDescriptor pd = new PropertyDescriptor("username" + i, clazz);

                    Method getMethod = pd.getReadMethod();//获得get方法
                    //取得方法后即可通过invoke方法调用，传入被调用方法所在类的对象和实参,对象可以通过cls.newInstance取得
                    String username = (String) getMethod.invoke(excelShopVo);
                    if (i == 1 && StringUtils.isEmpty(username)) {
                        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_USERNAME1_EMPTY);
                    }
                    if (StringUtils.isEmpty(username)) {
                        continue;
                    }
                    User user = userRepository.findUserByUserName(username);
                    if (user == null) {
                        user = new User();
                    }
                    user.setUserName(username);
                    user.setCreateTime(new Date());
                    user.setUpdateTime(new Date());
                    user.setUserPass(new PBKDF2PasswordHasher().encode(bookConfig.getInitialPassword()));
                    user.setIsStaff(false);
                    user.setIsSuperuser(false);
                    user.setIsActive(true);

                    pd = new PropertyDescriptor("identity" + i, clazz);

                    getMethod = pd.getReadMethod();
                    String rolename = (String) getMethod.invoke(excelShopVo);

                    if (rolename == null) {
                        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NAME_EMPTY);
                    }
                    List<Role> roleList = new ArrayList<>();
                    Role role = roleRepository.findRoleByRoleName(rolename);
                    roleList.add(role);
                    user.setRoleList(roleList);
                    user = userRepository.save(user);

                    list.add(user);
                }

                shop.setUserList(list);
                shopRepository.save(shop);
            }

            return getSuccessImportResult();
        } catch (Exception e) {
            logger.error("", e);
            throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

