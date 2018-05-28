package com.guming.dingtalk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.CorpMessageCorpconversationAsyncsendRequest;
import com.dingtalk.api.response.CorpMessageCorpconversationAsyncsendResponse;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.logger.YygLogger;
import com.guming.common.utils.EncryptUtils;
import com.guming.common.utils.StringToListUtils;
import com.guming.config.DingTalkConfig;
import com.guming.config.HttpClientManagerFactory;
import com.guming.dingtalk.response.DingJsapiResponseParam;
import com.guming.dingtalk.response.DingTokenResponseParam;
import com.guming.dingtalk.response.DingUserIdResponseParam;
import com.guming.dingtalk.response.DingUserInfoResponseParam;
import com.guming.dingtalk.vo.DingDepartmentVo;
import com.guming.dingtalk.vo.DingSignVo;
import com.guming.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Service
public class DingTalkService {

    public static YygLogger logger = new YygLogger(DingTalkService.class);

    @Autowired
    private DingTalkConfig dingTalkConfig;

    //根据远程调用获取的过时时间提前缓存失效的时间间隔（单位秒）
    public static final long SPACE_TIME = 100;

    public static final String ACCESS_TOKEN_KEY = "client.login.access.token";

    public static final String JSAPI_TICKET_KEY = "client.login.jsapi.ticket";

    @Autowired
    private HttpClientManagerFactory httpClientManagerFactory;

    @Autowired
    private RedisService redisService;

    /**
     * 获取签名
     *
     * @param ticket
     * @param nonceStr
     * @param timestamp
     * @param url
     * @return
     */
    public String sign(String ticket, String nonceStr, String timestamp, String url) {
        logger.dingtalk("=====================钉钉获取签名=========================");
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        try {
            return EncryptUtils.hashSHAEncrypt(plain.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return null;
    }

    /*
     * 在此方法中，为了避免频繁获取access_token，
     * 在距离上一次获取access_token时间在两个小时之内的情况，
     * 将直接从持久化存储中读取access_token
     *
     * 因为access_token和jsapi_ticket的过期时间都是7200秒
     * 所以在获取access_token的同时也去获取了jsapi_ticket
     */
    private String getAccessToken() {
        String token = "";
        Object temp = redisService.get(ACCESS_TOKEN_KEY);
        if (temp == null) {
            logger.dingtalk("=====================钉钉获取token=========================");
            String url = dingTalkConfig.getTokenUrl() + "?corpid=" + dingTalkConfig.getCorpId() + "&corpsecret=" + dingTalkConfig.getCorpSecret();

            String strResult = httpClientManagerFactory.httpGet(url);
            logger.dingtalk("=====================钉钉获取token完结=========================");
            logger.dingtalk(strResult);

            DingTokenResponseParam dingTalkReponseParam = JSON.parseObject(strResult, DingTokenResponseParam.class);
            if (dingTalkReponseParam.getErrcode() == 0) {
                token = dingTalkReponseParam.getAccessToken();
                redisService.set(ACCESS_TOKEN_KEY, token, dingTalkReponseParam.getExpiresIn() - SPACE_TIME);
                return token;
            }
            throw new ErrorMsgException(dingTalkReponseParam.getErrmsg());
        } else {
            token = (String) temp;
        }
        return token;
    }

    /**
     * 获取ticket
     *
     * @param accessToken
     * @return
     */
    private String getJsapiTicket(String accessToken) {
        String jsapiTicket = "";
        Object temp = redisService.get(JSAPI_TICKET_KEY + "." + accessToken);
        if (temp == null) {
            logger.dingtalk("=====================钉钉获取jsapi=========================");
            String url = dingTalkConfig.getJsapiUrl() + "?access_token=" + accessToken;

            String strResult = httpClientManagerFactory.httpGet(url);
            logger.dingtalk("=====================钉钉获取jsapi完结=========================");
            logger.dingtalk(strResult);

            DingJsapiResponseParam dingTalkReponseParam = JSON.parseObject(strResult, DingJsapiResponseParam.class);
            if (dingTalkReponseParam.getErrcode() == 0) {
                jsapiTicket = dingTalkReponseParam.getErrmsg();
                redisService.set(JSAPI_TICKET_KEY + "." + accessToken, dingTalkReponseParam.getExpiresIn() - SPACE_TIME);
                return jsapiTicket;
            }
            throw new ErrorMsgException(dingTalkReponseParam.getErrmsg());
        } else {
            jsapiTicket = ((Integer) temp).toString();
        }
        return jsapiTicket;
    }

    /**
     * 生成签名信息
     *
     * @param request
     * @return
     */
    public DingSignVo config(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String accessToken = getAccessToken();
        String ticket = getJsapiTicket(accessToken);
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = sign(ticket, nonceStr, timeStamp, url);
        String agentid = dingTalkConfig.getAgentId();

        DingSignVo dingSignVo = new DingSignVo();
        dingSignVo.setJsticket(ticket);
        dingSignVo.setSignature(signature);
        dingSignVo.setNonceStr(nonceStr);
        dingSignVo.setTimeStamp(timeStamp);
        dingSignVo.setCorpId(dingTalkConfig.getCorpId());
        dingSignVo.setAgentid(agentid);

        return dingSignVo;
    }

    /**
     * 获取用户钉钉信息
     *
     * @param code
     * @return
     */
    public DingUserInfoResponseParam getUserInfo(String code) {
        String userId = getUserId(code);
        return getUserInfoByUserId(userId);
    }

    /**
     * 通過userId獲取釘釘信息
     *
     * @param userId
     * @return
     */
    public DingUserInfoResponseParam getUserInfoByUserId(String userId) {
        String url = dingTalkConfig.getUserInfoUrl() + "?access_token=" + getAccessToken() + "&userid=" + userId;
        String strResult = httpClientManagerFactory.httpGet(url);
        if (!StringUtils.isEmpty(strResult)) {
            DingUserInfoResponseParam userInfoResponseParam = JSON.parseObject(strResult, DingUserInfoResponseParam.class);
            if (userInfoResponseParam.getErrcode() == 0) {
                return userInfoResponseParam;
            }
            throw new ErrorMsgException(userInfoResponseParam.getErrmsg());
        }
        return null;
    }

    /**
     * 获取用户userid
     *
     * @param code
     * @return
     */
    public String getUserId(String code) {
        String url = dingTalkConfig.getUserUrl() + "?access_token=" + getAccessToken() + "&code=" + code;
        String strResult = httpClientManagerFactory.httpGet(url);
        String userId = "";
        if (!StringUtils.isEmpty(strResult)) {
            DingUserIdResponseParam dingUserIdResponseParam = JSON.parseObject(strResult, DingUserIdResponseParam.class);
            if (dingUserIdResponseParam.getErrcode() == 0) {
                userId = dingUserIdResponseParam.getUserid();
                return userId;
            }
            throw new ErrorMsgException(dingUserIdResponseParam.getErrmsg());
        }
        return userId;
    }


    /**
     * 参数传递前封装成list形式，调用StringToListUtils.parseString 方法，以返回规定格式Str
     * <p>
     * 钉钉企业消息推送
     *
     * @throws Exception
     */
    public void msgPush(String deptIdsStr) throws Exception {

        String access_token = this.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(dingTalkConfig.getMsgUrl());
        CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
        req.setMsgtype("oa");
        req.setAgentId(Long.parseLong(dingTalkConfig.getAgentId()));
        req.setDeptIdList(deptIdsStr);
        req.setToAllUser(true);

        // 发送消息体有待修改
        req.setMsgcontentString("{\"message_url\": \"http://dingtalk.com\",\"head\": {\"bgcolor\": \"FFBBBBBB\",\"text\": \"头部标题\"},\"body\": {\"title\": \"正文标题\",\"form\": [{\"key\": \"姓名:\",\"value\": \"张三\"},{\"key\": \"爱好:\",\"value\": \"打球、听音乐\"}],\"rich\": {\"num\": \"15.6\",\"unit\": \"元\"},\"content\": \"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"image\": \"@lADOADmaWMzazQKA\",\"file_count\": \"3\",\"author\": \"李四 \"}}");
        CorpMessageCorpconversationAsyncsendResponse rsp = client.execute(req, access_token);

        System.out.println("------------------------------------");
        System.out.println(rsp.getBody());
        System.out.println("------------------------------------");

    }

    /**
     * 参数传递前封装成list形式，调用StringToListUtils.parseString 方法，以返回规定格式Str
     * <p>
     * 钉钉个人消息推送
     *
     * @throws Exception
     */
    public void userMsgPush(String userIdsStr) throws Exception {

        String access_token = this.getAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(dingTalkConfig.getMsgUrl());
        CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
        req.setMsgtype("oa");
        req.setAgentId(Long.parseLong(dingTalkConfig.getAgentId()));
        //req.setUseridList(userIdsStr);
        req.setDeptIdList(userIdsStr);
        req.setToAllUser(false);

        // 发送消息体有待修改
        req.setMsgcontentString("{\"message_url\": \"http://dingtalk.com\",\"head\": {\"bgcolor\": \"FFBBBBBB\",\"text\": \"头部标题\"},\"body\": {\"title\": \"正文标题\",\"form\": [{\"key\": \"姓名:\",\"value\": \"张三\"},{\"key\": \"爱好:\",\"value\": \"打球、听音乐\"}],\"rich\": {\"num\": \"15.6\",\"unit\": \"元\"},\"content\": \"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"image\": \"@lADOADmaWMzazQKA\",\"file_count\": \"3\",\"author\": \"李四 \"}}");
        CorpMessageCorpconversationAsyncsendResponse rsp = client.execute(req, access_token);

        System.out.println("------------------------------------");
        System.out.println(rsp.getBody());
        System.out.println("------------------------------------");

    }


    /**
     * 直接访问钉钉开放平台获取部门id
     *
     * @return String  部门Ids
     */
    public String getDeptIds() {
        String accessToken = this.getAccessToken();
        String url = "https://oapi.dingtalk.com/department/list_ids?access_token=" + accessToken + "&id=" + 1;
        String sr = httpClientManagerFactory.httpGet(url);
        JSONObject jsonObject = JSON.parseObject(sr);
        Long errCode = jsonObject.getLong("errcode");
        if (Long.valueOf(0).equals(errCode)) {
            DingDepartmentVo departmentVO = new DingDepartmentVo();

            String deptIds = jsonObject.getString("sub_dept_id_list");
            List<Long> deptIdsList = new ArrayList<>();
            List<String> deptIdStrList = StringToListUtils.parseList(deptIds);
            if (!StringUtils.isEmpty(deptIds)) {
                for (String idStr : deptIdStrList) {
                    deptIdsList.add(Long.parseLong(idStr));
                }
                departmentVO.setDeptIdsList(deptIdsList);
            }

            return StringToListUtils.parseString(departmentVO.getDeptIdsList());
        }
        return null;
    }
}


