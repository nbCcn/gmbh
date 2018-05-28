package com.guming.service.arrangement;



import com.guming.arrangement.dto.ArrangementMoveDto;
import com.guming.arrangement.dto.ArrangementReMoveDto;
import com.guming.arrangement.dto.ShopArrangementQueryDto;
import com.guming.arrangement.dto.query.ArrangementQueryDto;
import com.guming.arrangement.entity.PlansArrangement;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:13
 */
public interface ArrangementService extends BaseService {

    ResponseParam find(ArrangementQueryDto arrangementQueryDto);

    /**
     * 通过店铺id获取当前操作时间下的送货安排
     *
     * @param shopId
     * @return
     */
    PlansArrangement getSendTimePlansArrangement(Long shopId);

    ResponseParam move(ArrangementMoveDto arrangementMoveDto);

    ResponseParam remove(ArrangementReMoveDto arrangementReMoveDto);

    void export(String dayStr, Long tagwareHouseId, HttpServletResponse response) throws FileNotFoundException, Exception;

    ResponseParam findByShop(ShopArrangementQueryDto shopArrangementQueryDto);
}
