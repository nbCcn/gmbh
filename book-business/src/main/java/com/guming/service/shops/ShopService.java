package com.guming.service.shops;

import com.guming.authority.entity.User;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.shops.dto.ShopAddDto;
import com.guming.shops.dto.ShopUpdateDto;
import com.guming.shops.dto.query.ShopQuery;
import com.guming.shops.vo.ShopVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/12 14:54
 */
public interface ShopService extends BaseService {

    ResponseParam<List<ShopVo>> findShops(ShopQuery shopDto);

    ResponseParam<?> addShop(ShopAddDto addDto);

    ResponseParam<?> deleteShop(List<Long> ids);

    ResponseParam<?> updateShop(ShopUpdateDto updateDto);

    ResponseParam<?> findById(Long id);

    /**
     * 获取相应的收货店铺
     * @param userId    用户id
     * @param shopId    店铺id
     * @return
     */
    List<ShopVo> getRecipientShopMapVoList(Long userId, Long shopId);

    /**
     * 查找当前用户下的店铺
     * @return
     */
    ResponseParam<List<ShopVo>> findCurrentUserShops(User user);

    /**
     * 获取商铺状态下拉框
     * @return
     */
    ResponseParam<List<MapVo>> getShopStatusList();

    ResponseParam<List<MapVo>> getShopTagrankList();

    ResponseParam importShop(MultipartFile uploadFile);

    ResponseParam<List<MapVo>> getShopUserList();

    ResponseParam resetPass(IdDto idDto);
}
