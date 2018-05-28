package com.guming.kingdee;

import com.alibaba.fastjson.JSON;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.logger.YygLogger;
import com.guming.common.utils.EncryptUtils;
import com.guming.common.utils.RandomStringUtil;
import com.guming.config.HttpClientManagerFactory;
import com.guming.kingdee.request.InventoryProductRequestParam;
import com.guming.kingdee.request.InventoryRequestParam;
import com.guming.kingdee.request.SyncOrderRequestParam;
import com.guming.kingdee.response.InventoryProductResponseParam;
import com.guming.kingdee.response.InventoryResponseParam;
import com.guming.kingdee.response.SynOrderResponseParam;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.entity.OrderTemplatesSubmission;
import com.guming.products.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 金蝶cloud接口类
 * @Date: 2018/4/27
 */
@Service
public class KingdeeService {
    private YygLogger logger = new YygLogger(KingdeeService.class);

    /**
     * 检测库存
     * @param orderSubmission  已提交状态的订单
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public InventoryResponseParam orderInventory(OrderSubmission orderSubmission){
        String nonce = RandomStringUtil.generateRandomString(15);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = generateSignature(nonce,timestamp);

        String url = orderSubmission.getPlansPath().getTagLine().getTagwareHouse().getWebhook();

        InventoryRequestParam inverntoryRequestParam = new InventoryRequestParam();
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        if (orderTemplatesSubmissionList!=null && !orderTemplatesSubmissionList.isEmpty()){
            List<InventoryProductRequestParam> inventoryProductRequestParamList = new ArrayList<>();
            for (OrderTemplatesSubmission orderTemplatesSubmission: orderTemplatesSubmissionList){
                Products products = orderTemplatesSubmission.getProducts();
                if (products.getIsValid() && products.getIsUp()){
                    InventoryProductRequestParam inventoryProductRequestParam = new InventoryProductRequestParam();
                    inventoryProductRequestParam.setProductId(products.getId());
                    inventoryProductRequestParam.setProductGid(products.getCode());
                    inventoryProductRequestParamList.add(inventoryProductRequestParam);
                }
                inverntoryRequestParam.setItems(inventoryProductRequestParamList);
            }
        }

        StringBuilder requestUrl = new StringBuilder(url)
                .append("?signature=").append(signature)
                .append("&nonce=").append(nonce)
                .append("&timestamp=").append(timestamp)
                .append("&ftype=inventory");

        String requestParam = JSON.toJSONString(inverntoryRequestParam);
        logger.kingdee("=============检测库存==============请求url:\n\t"+requestUrl);
        logger.kingdee("=============检测库存==============请求参数:\n\t"+requestParam);

        String result = httpClientManagerFactory.httpPost(requestUrl.toString(),requestParam,"UTF-8");
        logger.kingdee("=============检测库存==============响应参数:\n\t"+result);
        InventoryResponseParam inventoryResponseParam = JSON.parseObject(result,InventoryResponseParam.class);
        if (inventoryResponseParam==null || inventoryResponseParam.getErrCode()!=0){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_INVENTORY_FAILED);
        }
        return inventoryResponseParam;
    }

    @Autowired
    private HttpClientManagerFactory httpClientManagerFactory;

    /**
     * 同步审核订单到金蝶
     * @param orderSubmission   已提交状态的订单
     * @return
     */
    public SynOrderResponseParam syncOrder(OrderSubmission orderSubmission){
        String nonce = RandomStringUtil.generateRandomString(15);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = generateSignature(nonce,timestamp);
        String url = orderSubmission.getPlansPath().getTagLine().getTagwareHouse().getWebhook();

        StringBuilder requestUrl = new StringBuilder(url)
                .append("?signature=").append(signature)
                .append("&nonce=").append(nonce)
                .append("&timestamp=").append(timestamp)
                .append("&ftype=sync");

        SyncOrderRequestParam syncOrderRequestParam = new SyncOrderRequestParam();
        syncOrderRequestParam.setContent(orderSubmission.getDescribe());
        syncOrderRequestParam.setShopCode(orderSubmission.getShopsShop().getCode());
        syncOrderRequestParam.setSendDate(orderSubmission.getSendTime());
        syncOrderRequestParam.setOrderCode(orderSubmission.getCode());
        syncOrderRequestParam.setLogistics(orderSubmission.getPlansPath().getTagLine().getFtype());

        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()){
            List<InventoryProductRequestParam> inventoryProductRequestParamList = new ArrayList<>();
            for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList){
                if (orderSubmission.getIsValid()) {
                    InventoryProductRequestParam inventoryProductRequestParam = new InventoryProductRequestParam();
                    Products products = orderTemplatesSubmission.getProducts();
                    inventoryProductRequestParam.setProductId(products.getId());
                    inventoryProductRequestParam.setProductGid(products.getCode());
                    inventoryProductRequestParam.setAmount(orderTemplatesSubmission.getAmount());
                    inventoryProductRequestParamList.add(inventoryProductRequestParam);
                }
            }
            syncOrderRequestParam.setInventoryProductRequestParamList(inventoryProductRequestParamList);
        }

        String requestParam = JSON.toJSONString(syncOrderRequestParam);
        logger.kingdee("=============同步订单==============请求url:\n\t"+requestUrl);
        logger.kingdee("=============同步订单==============请求参数:\n\t"+requestParam);

        String result = httpClientManagerFactory.httpPost(requestUrl.toString(),requestParam,"UTF-8");
        logger.kingdee("=============同步订单==============响应参数:\n\t"+result);

        SynOrderResponseParam synOrderResponseParam = JSON.parseObject(result,SynOrderResponseParam.class);


        //推送失敗拋出異常，讓數據回滾
        if (synOrderResponseParam.getError()!=null && !synOrderResponseParam.getError().equals(0)){
            if (synOrderResponseParam.getErrCode()!=null && !synOrderResponseParam.getErrCode().equals(0)) {
                if (synOrderResponseParam.getProductList() != null && !synOrderResponseParam.getProductList().isEmpty()) {
                    String kucunProducts = "";
                    for (InventoryProductResponseParam inventoryProductResponseParam : synOrderResponseParam.getProductList()) {
                        kucunProducts += inventoryProductResponseParam.getProductCode() + ",";
                    }
                    kucunProducts = kucunProducts.substring(0, kucunProducts.length() - 1);
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_INVENTORY_ENABLE, kucunProducts);
                }
            }
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SYNC_FAILED);
        }
        return synOrderResponseParam;
    }

    private String generateSignature(String nonce, String timestamp){
        String[] arr = new String[]{"8df5e34c3dc190df5a6d3fd96b029d", timestamp, nonce};
        Arrays.sort(arr);
        String s ="";
        for (int i=0;i<arr.length;i++){
            s+=arr[i];
        }
        return EncryptUtils.hashSHAEncrypt(s.getBytes());
    }

}
