package com.HyKj.UKeBao.data;


import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.HyKj.UKeBao.model.bean.BaseInfo;
import com.HyKj.UKeBao.model.login.baen.LoginInfo;
import com.HyKj.UKeBao.model.login.baen.RegistInfo;
import com.HyKj.UKeBao.model.login.baen.VerificationInfo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface NetWorkService {

    //注册模块
    /**
     * 登陆验证
     * 用户名
     * 密码
     * 登陆身份（商家5，会员6）
     * android:3  ios:4
     * 手机设备号
     */
    @POST("login!appLogin.do")
    Observable<LoginInfo> userLogin(@Query("account") String account, @Query("passwd") String passwd, @Query("identityId") String identityId, @Query("deviceType") int deviceType, @Query("deviceNo") String deviceNo);

    /**
     * 注册模块
     * 验证码
     * 手机号
     * 账号（手机号）
     * 新密码
     */
    @POST("businessStoreAdmin!addForeign.do")
    Observable<RegistInfo> regist(@Query("smsSecurityCode") String smsSecurityCode, @Query("phone") long phone, @Query("account") long account, @Query("passwdNew") String passwdNew);

    /**
     * 验证手机号是否存在
     * 手机号
     */
    @POST("businessStoreAdmin!phoneIsExist.do")
    Observable<VerificationInfo> isExistence(@Query("phone") long phone);

    /**
     * 获取验证码
     * 手机号
     */
    @POST("businessStoreAdmin!getBackPwdSmsSecurityCode.do")
    Observable<BaseInfo> getVerificationCode(@Query("phone") long phone);

    /**
     * 找回密码
     * 新密码
     * 验证码
     * 手机号
     */
    @POST("businessStoreAdmin!getBackPwd.do")
    Observable<JsonObject> forgetPassword(@Query("passwdNew") String passwdNew, @Query("smsSecurityCode") String smsSecurityCode, @Query("phone") String phone);

    /**
     * 行业类型信息
     */
    @POST("productCategory!list.do")
    Observable<JSONObject> industryTypeData();

    /**
     * 获取城市信息
     */
    @GET("js/province_bas.json")
    Observable<JSONArray> chooseCity();

    /**
     * 申请联盟图片上传
     * modelType：模块类型 1:产品图2:商家图3:临时图片4:广告图片
     * file：图片文件
     */
    @Multipart //上传文件必须指定注解
    @POST("image!add.do")
    Observable<JSONObject> uploadPictures(@Part("file\";filename=\"upload.jpg") RequestBody file, @Query("modelType") int modelType, @Query("name") String name);

    /**
     * 获取商家信息
     */
    @POST("businessStore!getById.do")
    Observable<JSONObject> getBusinessStore(@Query("token") String token);

    /**
     * 提交申请资料
     * businessName 	string 	店铺名称
     * name 	是 	string 	联系人
     * tel 	是 	string 	联系电话
     * businessRegistrationNo 	是 	string 	工商注册号
     * category 	是 	int 	行业类型
     * province 	是 	string 	省
     * city 	否 	string 	市
     * area 	否 	string 	区
     * address 	否 	string 	详细地址
     * longitude 	是 	double 	精度
     * latitude 	是 	double 	纬度
     * businessStoreImages 	否 	string 	店招
     * identityPicture 	是 	list 	营业执照,身份证照,身份证背面照
     * id 	否 	int 	商家主键  提交该id将不再新添加 而是对该主键的商家信息进行修改
     */
    @POST("businessStore!addForeign.do")
    Observable<JSONObject> first_commit(@Query("businessName") String businessName,
                                        @Query("name") String name,
                                        @Query("tel") String tel,
                                        @Query("businessRegistrationNo") String businessRegistrationNo,
                                        @Query("category") int category,
                                        @Query("province") String province,
                                        @Query("city") String city,
                                        @Query("area") String area,
                                        @Query("address") String address,
                                        @Query("longitude") Double longitude,
                                        @Query("latitude") Double latitude,
                                        @Query("businessStoreImages") String businessStoreImages,
                                        @Query("identityPicture") List<String> identityPicture,
                                        @Query("token") String token);

    @POST("businessStore!addForeign.do")
    Observable<JSONObject> commit(@Query("businessName") String businessName,
                                  @Query("name") String name,
                                  @Query("tel") String tel,
                                  @Query("businessRegistrationNo") String businessRegistrationNo,
                                  @Query("category") int category,
                                  @Query("province") String province,
                                  @Query("city") String city,
                                  @Query("area") String area,
                                  @Query("address") String address,
                                  @Query("longitude") Double longitude,
                                  @Query("latitude") Double latitude,
                                  @Query("businessStoreImages") String businessStoreImages,
                                  @Query("identityPicture") List<String> identityPicture,
                                  @Query("id") String id,
                                  @Query("token") String token);

    /**
     * token令牌
     * 注销
     */
    @POST("login!appLoginOut.do")
    Observable<JSONObject> loginOut(@Query("token") String token);

    /**
     * 获取客服电话
     */
    @POST("company!getCustomerService.do")
    Observable<JSONObject> getCostomerService();

    /**
     * 设备类型
     * 获取当前版本号
     */
    @POST("appVersionCheck!checkVersion.do")
    Observable<JSONObject> whetheToUpDate(@Query("type") String type);

    /**
     * receiveIdentity 	是 	int 	接收方身份 5：商家
     * page 	否 	int 	页数
     * rows 	否 	int 	每页行数
     */
    @POST("notice!listByIdentityPage.do")
    Observable<JSONObject> getNoticeInfo(@Query("receiveldentity") int receiveldentity, @Query("page") int page, @Query("rows") int rows);

    /**
     * 获取用户名
     * phone String  手机号码
     */
    @POST("menber!getNameByPhone.do")
    Observable<JSONObject> getUserName(@Query("phone") String phone);

    //店铺设置
    /**
     * 赠送积分
     * cash 现金
     * businessName 店铺名称
     * integral 积分
     * phone 手机号码
     */
    @POST("businessStore!sendIntegral.do")
    Observable<JSONObject> sendIntegral(@Query("cash") double cash, @Query("integral") double integral, @Query("businessName") String businessName, @Query("phone") String phone, @Query("token") String token);

    /**
     * 获取赠送积分记录
     * businessStoreId 	是 	int 	商家id
     * page 	是 	int 	返回记录页数
     * rows 	是 	int 	返回记录行数
     * isSend 	否 	boolean 	筛选赠送积分记录（true:筛选，false或不传:不筛选）
     * type 	否 	boolean 	筛选交易状态（true:筛选收入类型，false:筛选支出类型，不传：不筛选）
     */
    @POST("businessStoreIntegralLog!listByPage.do")
    Observable<JSONObject> getRecordData(@Query("page") int page, @Query("rows") int rows, @Query("businessStoreId") int businessStoreId,
                                         @Query("isSend") String isSend, @Query("type") String type, @Query("token") String token);

    /**
     * 获取财务信息
     */
    @POST("offlineOrders!paySummary.do")
    Observable<JSONObject> getFinancialData(@Query("openDate") String openDate, @Query("stopDate") String stopDate, @Query("token") String token);

    /**
     * 获取现金流信息
     * businessStoreId 	是 	int 	商家id
     * page 	是 	int 	返回记录页数
     * rows 	是 	int 	返回记录行数
     */
    @POST("businessStoreCashLog!listByPage.do")
    Observable<JSONObject> getCashRecord(@Query("businessStoreId") int businessStoreId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 获取订单数据
     * page 	是 	int 	页数
     * rows 	是 	int 	每页显示行数
     * status 	否 	int 	不传获取全部，传1获取已完成，传5获取已退款
     * canCancel 	否 	boolean 	是否只获取能取消的数据 true:是 不传或false:否
     */
    @POST("offlineOrders!listByBusinessStore.do")
    Observable<JSONObject> getPayRecord(@Query("page") int page, @Query("rows") int rows, @Query("status") int status, @Query("token") String token);

    /**
     * 获取全部订单数据
     * page 	是 	int 	页数
     * rows 	是 	int 	每页显示行数
     * status 	否 	int 	不传获取全部，传1获取已完成，传5获取已退款
     * canCancel 	否 	boolean 	是否只获取能取消的数据 true:是 不传或false:否
     */
    @POST("offlineOrders!listByBusinessStore.do")
    Observable<JSONObject> getAllPayRecord(@Query("page") int page, @Query("rows") int rows, @Query("token") String token);

    /**
     *  退款
     *  orderId 	是 	int 	订单id
     *  passwd 	是 	string 	商家密码
     * */
    @POST("pay!refund.do")
    Observable<JSONObject> refund(@Query("orderId") int orderId,@Query("passwd")String passwd,@Query("token")String token);

    /**
     *  提交店铺设置
     *  tel 	是 	string 	店内电话
     *  name 	是 	string 	联系人姓名
     *  pictures 	否 	list 	店铺相册
     *  address 	否 	string 	店铺地址
     *  province 	否 	string 	省
     *  city 	否 	string 	市
     *  area 	否 	string 	区
     *  longitude 	否 	double 	地理坐标 （精度）
     *  latitude 	否 	double 	地理坐标 （纬度）
     * */
    @POST("businessStore!updateForeign.do")
    Observable<JSONObject> commitBusinessSettings(@Query("tel")String tel,
                                                  @Query("name")String name,
                                                  @Query("pictures")List<String> pictures,
                                                  @Query("address")String address,
                                                  @Query("province")String province,
                                                  @Query("city")String city,
                                                  @Query("area")String area,
                                                  @Query("longitude")double longitude,
                                                  @Query("latitude")double latitude,
                                                  @Query("token")String token);


    //营销模块
    /**   获取会员数量
     *   discount  是 	double 	距离（单位：公里） 大于等于3公里
     *  longitude 	是 	double 	精度
     *  latitude 	是 	double 	纬度*/

    @POST("menber!getMenberCount.do")
    Observable<JSONObject> getMemberCount(@Query("discount")double discount,@Query("longitude")double longitude,
                                          @Query("latitude")double latitude,@Query("token")String token);

    /** 获取红包卡劵数量
     *  page 	否 	int 	页数
     *  rows 	否 	int 	每页行数 如果是10 则卡卷和红包的条数最多10条 最多共20条数据
     * */
    @POST("shopper/showmanship!enabledRedBag.do")
    Observable<JSONObject> getRedPacketsAndCardInfo(@Query("page")int page,@Query("rows") int rows,@Query("token")String token);

    /**
     * 获取单个卡劵详情
     * id 	是 	int 	主键
     * */
    @POST("coup!getByBusiness.do")
    Observable<JSONObject> getSingCardDetail(@Query("id")int id,@Query("token")String token);

    /**
     * 获取单个红包详情
     * id 	是 	int 	主键
     * */
    @POST("shopper/showmanship!getById.do")
    Observable<JSONObject> getSingRedPacketDetail(@Query("id")int id,@Query("token")String token);
}
