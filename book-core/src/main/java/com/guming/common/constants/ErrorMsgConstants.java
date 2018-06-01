package com.guming.common.constants;

/**
 * @Author: PengCheng
 * @Description: 错误类常量
 * @Date: 2018/4/12
 */
public class ErrorMsgConstants {

    //系统内部错误
    public static final Integer SYSTEM_ERROR_CODE = 0;
    public static final String SYSTEM_FAILED_MSG = "err.system.all";

    //成功消息
    public static final String OPTION_SUCCESS_MSG = "success.option";
    public static final String OPTION_DELETE_SUCCESS_MSG = "success.option.delete";
    public static final String OPTION_ADD_SUCCESS_MSG = "success.option.add";
    public static final String OPTION_UPDATE_SUCCESS_MSG = "success.option.update";
    public static final String OPTION_UPLOAD_SUCCESS_MSG = "success.option.upload";
    public static final String OPTION_EXPORT_SUCCESS_MSG = "success.option.export";
    public static final Integer OPTION_SUCCESS_CODE = 1;


    //验证参数错误
    public static final Integer OPTION_FAILED_CODE = 2;

    //未登陆信息
    public static final String OPTION_LOGIN_FIRST = "err.login.not";
    public static final Integer OPTION_NOT_LOGIN_CODE = 3;

    //权限不足信息
    public static final Integer OPTION_NOT_AUTHORITY_CODE = 4;
    public static final String OPTION_NOT_AUTHORITY_MSG = "err.validation.authority.no";

    //初始密码
    public static final Integer OPTION_INITIAL_PASSWORD_CODE = 5;
    public static final String OPTION_INITIAL_PASSWORD_FIRST = "option.initial.password";

    /**
     * 成功一半的code，适用于批量删除同时抛出删除失败的信息和通知前台刷新页面
     */
    public static final Integer OPTION_FAILED_HELF_CODE = 6;


    //登录
    public static final String ERROR_VALIDATION_ID_EMPTY = "error.id.empty";
    public static final String ERROR_VALIDATION_TOKEN = "err.validation.token";
    public static final String ERROR_VALIDATION_LOGIN_INFO = "err.validation.login.info";
    public static final String ERROR_VALIDATION_LOGIN_INFO_EMPTY = "err.validation.login.info.empty";
    public static final String ERROR_VALIDATION_USER_PASS = "error.validation.user.pass";
    public static final String ERROR_VALIDATION_LOGIN_USERNAME_EMPTY = "err.validation.login.username.empty";

    //角色
    public static final String ERROR_VALIDATION_ROLE_NAME_EMPTY = "err.validation.role.name.empty";
    public static final String ERROR_VALIDATION_ROLE_ID_EMPTY = "err.validation.role.id.empty";
    public static final String ERROR_VALIDATION_ROLE_NOT_EXISTS = "err.validation.role.not.exists";
    public static final String ERROR_VALIDATION_ROLE_NOT_SELECT = "err.validation.role.not.select";
    public static final String ERROR_VALIDATION_ROLE_NAME_REPEAT = "err.validation.role.name.repeat";

    //用户
    public static final String ERROR_VALIDATION_USER_ID_EMPTY = "err.validation.user.id.empty";
    public static final String ERROR_VALIDATION_USER_NAME_PASS_EMPTY = "err.validation.user.name.pass.empty";
    public static final String ERROR_VALIDATION_USER_NAME_EXISTS = "err.validation.user.name.exists";
    public static final String ERROR_VALIDATION_USER_NOT_EXISTS = "err.validation.user.not.exists";
    public static final String ERROR_VALIDATION_USER_ROLE_NOT_EXISTS = "err.validation.user.role.not.exists";
    public static final String ERROR_VALIDATION_USER_NOT_SELECT = "err.validation.user.not.select";
    public static final String ERROR_VALIDATION_USER_PASS_FORMAT_ERROR = "err.validation.user.pass.format.error";
    public static final String ERROR_VALIDATION_USER_USERNAME1_EMPTY = "err.validation.user.username1.empty";
    public static final String ERROR_VALIDATION_USER_SHOP_EXISTS = "err.validation.user.shop.exists";


    //统一参数
    public static final String ERROR_VALIDATION_USER_NOT_ACTIVE = "err.validation.user.not.active";
    public static final String ERROR_VALIDATION_USER_PASS_DIFFER = "err.validation.user.pass.differ";
    public static final String ERROR_VALIDATION_OBJECT_NOT_EXISTS = "err.validation.object.not.exists";

    public static final String ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NAME_EXISTS = "err.validation.tagwareHouse.class.name.exists";
    public static final String ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NAME_EMPTY = "err.validation.tagwareHouse.class.name.empty";
    public static final String ERROR_VALIDATION_TAGWAREHOUSE_CLASS_ID_EMPTY = "err.validation.tagwareHouse.class.id.empty";
    public static final String ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS = "err.validation.tagwareHouse.class.not.exists";
    public static final String ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS_DELETE = "err.validation.tagwareHouse.class.not.exists.delete";
    public static final String ERROR_VALIDATION_TAGWAREHOUSE_LINE_EXISTS = "err.validation.tagwareHouse.line.exists";

    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NAME_EXISTS = "err.validation.setupstagrank.class.name.exists";
    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NAME_EMPTY = "err.validation.setupstagrank.class.name.empty";
    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_ISPREPARE_EMPTY = "err.validation.setupstagrank.class.isprepare.empty";
    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS_DELETE = "err.validation.setupstagrank.class.not.exists.delete";
    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_ID_EMPTY = "err.validation.setupstagrank.class.id.empty";
    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS = "err.validation.setupstagrank.class.not.exists";

    public static final String ERROR_VALIDATION_SETUPSTAGRANK_CLASS_SHOP_EXISTS ="err.validation.setupstagrank.class.shop.exists";
    public static final String ERROR_VALIDATION_SHOP_CLASS_ID_EMPTY = "err.validation.shop.class.id.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_NAME_EMPTY = "err.validation.shop.class.name.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_ENAME_EMPTY = "err.validation.shop.class.ename.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_CODE_EMPTY = "err.validation.shop.class.code.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_PROVINCE_EMPTY = "err.validation.shop.class.province.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_CITY_EMPTY = "err.validation.shop.class.city.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_DISTRICT_EMPTY = "err.validation.shop.class.district.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_ADDRESS_EMPTY = "err.validation.shop.class.address.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_LNG_EMPTY = "err.validation.shop.class.lng.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_LAT_EMPTY = "err.validation.shop.class.lat.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_CONTACT_EMPTY = "err.validation.shop.class.contact.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_PHONE_EMPTY = "err.validation.shop.class.phone.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_STATUS_EMPTY = "err.validation.shop.class.status.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_JOINEDTIME_EMPTY = "err.validation.shop.class.joinedtime.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_NAME_EXISTS = "err.validation.shop.class.name.exists";
    public static final String ERROR_VALIDATION_SHOP_CLASS_NOT_EXISTS = "err.validation.shop.class.not.exists";
    public static final String ERROR_VALIDATION_SHOP_CLASS_NOT_EXISTS_DELETE = "err.validation.shop.class.not.exists.delete";
    public static final String ERROR_VALIDATION_SHOP_USER_NOT_MATCH = "err.validation.shop.user.not.match";
    public static final String ERROR_VALIDATION_SHOP_ID_EMPTY = "err.validation.shop.id.empty";
    public static final String ERROR_VALIDATION_SHOP_CLASS_USER_EXISTS = "err.validation.shop.class.user.exists";
    public static final String ERROR_VALIDATION_SHOP_CLASS_USER_NOT_EXISTS = "err.validation.shop.class.user.not.exists";
    public static final String ERROR_VALIDATION_SHOP_CLASS_USER_IS_EXISTS = "err.validation.shop.class.user.is.exists";


    public static final String ERR_VALIDATION_TAGLINE_CLASS_ID_EMPTY = "err.validation.tagline.class.id.empty";
    public static final String ERROR_VALIDATION_TAGLINE_CLASS_NOT_EXISTS_DELETE = "err.validation.tagline.class.not.exists.delete";
    public static final String ERROR_VALIDATION_TAGLINE_CLASS_NOT_EXISTS = "err.validation.tagline.class.not.exists";
    public static final String ERROR_VALIDATION_TAGLINE_CLASS_FTYPE_EMPTY = "err.validation.tagline.class.ftype.empty";
    public static final String ERROR_VALIDATION_TAGLINE_CLASS_NAME_EMPTY = "err.validation.tagline.class.name.empty";
    public static final String ERROR_VALIDATION_TAGLINE_CLASS_NAME_EXISTS = "err.validation.tagline.class.name.exists";

    //订单模板
    public static final String ERROR_VALIDATION_TEMPLATES_TYPE_NAME_EMPTY = "err.validation.templates.type.name.empty";
    public static final String ERROR_VALIDATION_TEMPLATES_TYPE_EMPTY = "err.validation.templates.type.empty";
    public static final String ERROR_VALIDATION_TEMPLATE_TYPE_NOT_EXISTS = "err.validation.templates.type.not.exists";
    public static final String ERROR_VALIDATION_TEMPLATES_NAME_EMPTY = "err.validation.templates.name.empty";
    public static final String ERROR_VALIDATION_TEMPLATES_WAREHOUSE_EMPTY = "err.validation.templates.warehouse.empty";
    public static final String ERROR_VALIDATION_TEMPLATES_LINE_EMPTY = "err.validation.templates.line.empty";
    public static final String ERROR_VALIDATION_TEMPLATE_USED_CAN_NOT_UPDATE = "err.validation.templates.used.can.not.update";

    /**
     * 商品
     */
    public static final String ERROR_VALIDATION_PRODUCTS_CODE_EMPTY = "err.validation.products.code.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_NAME_EMPTY = "err.validation.products.name.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_SPEC_EMPTY = "err.validation.products.spec.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_UNIT_EMPTY = "err.validation.products.unit.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_PRICE_EMPTY = "err.validation.products.price.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_STOCK_EMPTY = "err.validation.products.stock.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_LIMIT_EMPTY = "err.validation.products.limit.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_STEP_EMPTY = "err.validation.products.step.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_ID_EMPTY = "err.validation.products.id.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_NOT_EXISTS = "err.validation.products.not.exists";
    public static final String ERROR_VALIDATION_PRODUCTS_NOT_UNDERCARRIAGE = "err.validation.products.not.undercarriage";

    /**
     * 商品分类
     */
    public static final String ERROR_VALIDATION_PRODUCTS_CLASS_NAME_EMPTY = "err.validation.products.class.name.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_CLASS_ID_EMPTY = "err.validation.products.class.id.empty";
    public static final String ERROR_VALIDATION_PRODUCTS_CLASS_NOT_EXISTS = "err.validation.products.class.not.exists";
    public static final String ERROR_VALIDATION_PRODUCTS_CLASS_ID_EMPTY_DELETE = "err.validation.products.class.not.exists.delete";
    public static final String ERROR_VALIDATION_PRODUCTS_CLASS_USED = "err.validation.products.class.not.used";


    /**
     * 路线规划
     */
    public static final String ERR_VALIDATION_PATH_CLASS_ID_EMPTY = "err.validation.path.class.id.empty";
    public static final String ERROR_VALIDATION_NEED_LINE_NOT_SELECT = "err.validation.need.line.not.select";




    //订单
    public static final String ERROR_VALIDATION_ORDER_ID_EMPTY = "err.validation.order.id.empty";
    public static final String ERROR_VALIDATION_ORDER_NOT_EXISTS = "err.validation.order.not.exists";
    public static final String ERROR_VALIDATION_ORDER_STATUS_ERROR = "err.validation.order.status.error";
    public static final String ERROR_VALIDATION_ORDER_TEMPLATE_NOT_EXISTS = "err.validation.order.template.not.exists";
    public static final String ERROR_VALIDATION_ORDER_TEMPLATE_AMOUNT_EMPTY = "err.validation.order.template.amount.empty";
    public static final String ERROR_VALIDATION_ORDER_TEMPLATE_ID_EMPTY = "err.validation.order.template.id.empty";
    public static final String ERROR_VALIDATION_ORDER_TEMPLATES_PRODUCT_NOT_EXISTS = "err.validation.order.template.product.not.exists";
    public static final String ERROR_VALIDATION_ORDER_STATUS_FLOW = "err.validation.order.status.flow";
    public static final String ERROR_VALIDATION_ORDER_SEND_SHOP_ID_EMPTY = "err.validation.order.send.shop.id";
    public static final String ERROR_VALIDATION_ORDER_SEND_TIME_OK = "err.validation.order.send.time.ok";
    public static final String ERROR_VALIDATION_ORDER_SEND_SHOP_NOT_MATCH = "err.validation.order.send.shop.not.match";
    public static final String ERROR_VALIDATION_ORDER_SHOP_NOT_EXISTS = "err.validation.order.shop.not.exists";
    public static final String ERROR_VALIDATION_OEDER_SEND_SHOP_PATH_NOT_EXISTS = "err.validation.order.end.shop.path.not.exists";
    public static final String ERROR_VALIDATION_ORDER_CART_NOT_EXISTS = "err.validation.order.cart.not.exists";
    public static final String ERROR_VALIDATION_ORDER_REVOKE_ERROR = "err.validation.order.revoke.error";
    public static final String ERROR_VALIDATION_ORDER_PRODUCTS_OVER_LIMIT = "err.validation.order.products.over.limit";
    public static final String ERROR_VALIDATION_ORDER_SYNC_FAILED = "err.validation.order.sync.failed";
    public static final String ERROR_VALIDATION_ORDER_INVENTORY_ENABLE = "err.validation.order.inverntory.enable";
    public static final String ERROR_VALIDATION_ORDER_INVENTORY_FAILED = "err.validation.order.inverntory.failed";
    public static final String ERROR_VALIDATION_ORDER_PRODUCTS_NOT_EXISTS = "err.validation.order.products.not.exists";
    public static final String ERROR_VALIDATION_ORDER_REVOKE_TIME_ERROR = "err.validation.order.revoke.time.error";

    //路线安排
    public static final String ERROR_VALIDATION_ARRANGEMENT_CLASS_DATE_EMPTY = "err.validation.arrangement.class.date.empty";
    public static final String ERROR_VALIDATION_ARRANGEMENT_CLASS_ID_EMPTY = "err.validation.arrangement.class.id.empty";
    public static final String ERROR_VALIDATION_ARRANGEMENT_CLASS_EMPTY = "err.validation.arrangement.class.empty";
    public static final String ERROR_VALIDATION_ARRANGEMENT_TIME_INTERVAL_NOT_ACCORD  = "err.validation.arrangement.time.interval.not.accord";

    //客户端登陆
    public static final String ERROR_VALIDATION_CLIENT_LOGIN_ERROR = "err.validation.client.login.error";
    public static final String ERROR_VALIDATION_ORDER_SENDTIMEDUR_EXISTS = "err.validation.order.sendtimedur.exists";


    public static final String ERROR_VALIDATION_SETUP_ADVANCE_DAY_EMPTY = "err.validation.setup.advance.day.empty";
    public static final String ERROR_VALIDATION_SETUP_ADVANCE_START_END_NOT_MATCH = "err.validation.setup.advance.start.end.not.match";


    public static final String ERROR_VALIDATION_FILE_UPLOAD_SIZE_OVER = "err.validation.file.upload.size.over";
    public static final String ERROR_VALIDATION_FILE_UPLOAD_FAILED = "err.validation.file.upload.failed";
    public static final String ERROR_VALIDATION_FILE_DOWNLOAD_FAILED = "err.validation.file.download.failed";
    public static final String ERROR_VALIDATION_FILE_IMPORT_ROW_NOT_MATCH = "err.validation.file.import.row.not.match";
    public static final String ERROR_VALIDATION_FILE_IMPORT_FAILED = "err.validation.file.import.failed";
    public static final String ERROR_VALIDATION_FILE_EXPORT_FAILED = "err.validation.file.export.failed";

}
