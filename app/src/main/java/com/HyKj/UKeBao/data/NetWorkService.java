package com.HyKj.UKeBao.data;


import com.HyKj.UKeBao.model.businessManage.businessSettings.bean.GoodsInfo;
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
import retrofit2.http.Body;
import retrofit2.http.Field;
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

    /******************登陆模块****************************/

    /**
     * 获取动态闪屏页信息
     * positionId int 请求id（35）
     * */
    @POST("ad!listByAdPosition.do")
    Observable<JSONObject> getBackgrounp(@Query("positionId")int positionId);

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
    Observable<JSONObject> isExistence(@Query("phone") long phone);

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
    Observable<JSONObject> forgetPassword(@Query("passwdNew") String passwdNew, @Query("smsSecurityCode") String smsSecurityCode, @Query("phone") String phone);

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

    /******************店铺设置****************************/

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
     *  获取财务实收详情
     *  openDate 	是 	string 	开始时间 例：2016-09-01
     *  stopDate 	是 	string 	结束时间 例：2016-09-30
     * */
    @POST("offlineOrders!paySummaryDetailed.do")
    Observable<JSONObject> getRealMoneyDetail(@Query("openDate") String openDate, @Query("stopDate") String stopDate, @Query("token") String token);

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
     * 退款
     * orderId 	是 	int 	订单id
     * passwd 	是 	string 	商家密码
     */
    @POST("pay!refund.do")
    Observable<JSONObject> refund(@Query("orderId") int orderId, @Query("passwd") String passwd, @Query("token") String token);

    /**
     * 提交店铺设置
     * tel 	是 	string 	店内电话
     * name 	是 	string 	联系人姓名
     * pictures 	否 	list 	店铺相册
     * address 	否 	string 	店铺地址
     * province 	否 	string 	省
     * city 	否 	string 	市
     * area 	否 	string 	区
     * longitude 	否 	double 	地理坐标 （精度）
     * latitude 	否 	double 	地理坐标 （纬度）
     * piList[0].src； piList[0].name 	否 	list 	商品相册图片路径； 商品相册图片名称
     */
    @POST("businessStore!updateForeign.do")
    Observable<JSONObject> commitBusinessSettings(@Query("tel") String tel,
                                                  @Query("name") String name,
                                                  @Query("pictures") List<String> pictures,
                                                  @Query("address") String address,
                                                  @Query("province") String province,
                                                  @Query("city") String city,
                                                  @Query("area") String area,
                                                  @Query("longitude") double longitude,
                                                  @Query("latitude") double latitude,
                                                  @Body RequestBody piList,
                                                  @Query("token") String token);

    /**
     * 删除商品
     * id 	是 	int 	商品ID
     * */
    @POST("bsProductImg!delete.do")
    Observable<JSONObject> delete_goods(@Query("id") int id,@Query("token") String token);

    /******************营销模块****************************/

    /**
     * 获取会员数量
     * discount  是 	double 	距离（单位：公里） 大于等于3公里
     * longitude 	是 	double 	精度
     * latitude 	是 	double 	纬度
     */

    @POST("menber!getMenberCount.do")
    Observable<JSONObject> getMemberCount(@Query("discount") double discount, @Query("longitude") double longitude,
                                          @Query("latitude") double latitude, @Query("token") String token);

    /**
     * 获取红包卡劵数量
     * page 	否 	int 	页数
     * rows 	否 	int 	每页行数 如果是10 则卡卷和红包的条数最多10条 最多共20条数据
     */
    @POST("shopper/showmanship!enabledRedBag.do")
    Observable<JSONObject> getRedPacketsAndCardInfo(@Query("page") int page, @Query("rows") int rows, @Query("token") String token);

    /**
     * 获取单个卡劵详情
     * id 	是 	int 	主键
     */
    @POST("coup!getByBusiness.do")
    Observable<JSONObject> getSingCardDetail(@Query("id") int id, @Query("token") String token);

    /**
     * 获取单个红包详情
     * id 	是 	int 	主键
     */
    @POST("shopper/showmanship!getById.do")
    Observable<JSONObject> getSingRedPacketDetail(@Query("id") int id, @Query("token") String token);

    /**
     * 发送揽客红包
     * count 	是 	int 	人数
     * integralQuota 	是 	double 	总积分
     * distance 	是 	double 	距离
     * image 	是 	string 	图片
     * context 	是 	string 	内容
     * latitude 	是 	double 	纬度
     * longitude 	是 	double 	经度
     * payType 	否 	short 	支付类型（默认0），0：积分 1：支付宝 2：微信3:现金
     */
    @POST("shopper/showmanship!sendBusinessStoreShowmanship.do")
    Observable<JSONObject> sendDataToWeb(@Query("count") String count, @Query("integralQuota") double integralQuota,
                                         @Query("distance") double distance, @Query("image") String image,
                                         @Query("context") String context, @Query("latitude") double latitude,
                                         @Query("longitude") double longitude, @Query("payType") short payType, @Query("token") String token);

    /**
     * 发送卡劵
     * startTime 	是 	string 	开始时间
     * endTime 	是 	string 	结束时间
     * getLimit 	是 	int 	会员领取数量限制 0为不限制
     * inventory 	是 	string 	数量
     * price 	是 	string 	面值
     * deduction 	是 	string 	限用价(满xxx可用)
     * details 	是 	string 	其他说明
     * longitude 	是 	double 	精度
     * latitude 	是 	double 	纬度
     */
    @POST("coup!addToBusinessYouYou.do")
    Observable<JSONObject> sendCard(@Query("startTime") String startTime, @Query("endTime") String endTime, @Query("getLimit") int getLimit,
                                    @Query("inventory") String inventory, @Query("price") String price, @Query("deduction") String deduction,
                                    @Query("details") String details, @Query("longitude") double longitude, @Query("latitude") double latitude,
                                    @Query("token") String token);

    /**
     *  红包领取记录
     *  showmanshipId 	int 	红包id
     *  page 	int 	请求页数
     *  rows 	int 	请求记录数
     * */
    @POST("receive!receiveList.do")
    Observable<JSONObject> redPacket_record(@Query("showmanshipId")int id,@Query("page")int page,@Query("rows")int rows,@Query("token")String token);

    /**
     * 获取卡劵列表
     *  rows 	是 	int 	分页行数
     *  page 	是 	int 	分页页数
     * */
    @POST("coup!listByBusiness.do")
    Observable<JSONObject> getAllCardInfo(@Query("rows")int rows,@Query("page")int page,@Query("token")String token);

    /**
     *  商家揽客红包记录
     *  businessStoreId 	是 	int 	商家ID
     *  page 	是 	int 	页数
     *  rows 	是 	int 	每页行数
     * */
    @POST("shopper/showmanship!storeRedbagList.do")
    Observable<JSONObject> getAllRedPacketInfo(@Query("rows")int rows, @Query("page")int page, @Query("businessStoreId") int id, @Query("token")String token);

    /**
     *  商家是否为vip
     *  id 	否 	int 	商家id 不填默认为当前登录商家
     * */
    @POST("businessStore!isVip.do")
    Observable<JSONObject> isVip(@Query("token") String token);

    /**
     * 申请成为vip
     * */
    @POST("applyVip!add.do")
    Observable<JSONObject> applyVip(@Query("token")String token);

    /**
     * 充值vip
     *  id 	是 	int 	支付订单id
     *  token 	只app需要 	string 	token
     *  paymentType 	否 	int 	支付平台（默认1）：1 微信，2 支付宝 3 现金账户
     *  useWebPay 	否 	int 	默认：0，使用web方式支付，1使用app方式支付
     * */
    @POST("payApplyVipOrder!pay.do")
    Observable<JSONObject> rechargeVip(@Query("id")int vipPayId, @Query("token")String token,@Query("paymentType")int payType,@Query("useWebPay")int useWebPay);

    /**
     * 获取升级vip信息
     * */
    @POST("payApplyVipOrder!getMsg.do")
    Observable<JSONObject> getPayInfo(@Query("token") String token);



    /******************用户信息模块****************************/
    /**
     * 获取客服电话
     * */
    @POST("company!getCustomerService.do")
    Observable<JSONObject> getCustomerPhone();

    /**
     * 修改密码
     * passwdOld 	是 	string 	旧密码
     * passwdNew 	是 	string 	新密码
     * passwdAgain 	是 	string 	确认密码
     * */
    @POST("businessStoreAdmin!updatePwd.do")
    Observable<JSONObject> commitNewPassword(@Query("passwdOld")String passwdOld,
                                             @Query("passwdNew")String passwdNew,
                                             @Query("passwdAgain")String passwdAgain,
                                             @Query("token")String token);

    /**
     * 兑换信息
     * checkNo 	是 	int 	验证码
     * */
    @POST("offlineOrders!checkNO.do")
    Observable<JSONObject> getCodeInfo(@Query("checkNo")int checkNo,@Query("token")String token);

    /**
     * 兑换-确认收款
     *  checkNo 	是 	string 	验证码
     * */
    @POST("offlineOrders!checkOrderNo.do")
    Observable<JSONObject> confirmReceipt(@Query("checkNo")int checkNo,@Query("token")String token);

    /**
     *  查询兑换记录
     *  businessStoreId 	是 	int 	商家id
     *  page 	是 	int 	返回记录页数
     *  rows 	是 	int 	返回记录行数
     * */
    @POST("orderProducts!orderListByBusienssStoreId.do")
    Observable<JSONObject> getExchangRecord(@Query("businessStoreId")int businessStoreId,@Query("page")int page,@Query("rows")int rows,@Query("token")String token);

    /**
     * 申请提现
     * businessStoreId 	是 	int 	商家id
     * quota 	是 	float 	提现金额
     * */
    @POST("extractionCash!add.do")
    Observable<JSONObject> withdrawals(@Query("businessStoreId")int businessStoreId,@Query("quota")float quota,@Query("token")String token);

    /**
     * 获取提现记录
     *  businessStoreId 	是 	int 	商家id
     *  page 	是 	int 	返回记录页数
     *  rows 	是 	int 	返回记录行数
     * */
    @POST("extractionCash!listByPage.do")
    Observable<JSONObject> getwithdrawalsRecord(@Query("businessStoreId")int businessStoreId,@Query("page")int page,@Query("rows")int rows,@Query("token")String token);

    /**
     * 注销
     * token String  令牌
     */
    @POST("login!appLoginOut.do")
    Observable<JSONObject> cancellation(@Query("token")String token);

    /**
     *  添加银行卡
     *  FBankNo 	是 	string 	银行卡号
     *  smsCode 	是 	string 	手机验证码
     *  FBankName 	是 	string 	银行名称
     *  FName 	是 	string 	开户人名称
     * */
    @POST("bankMessage!addBusinessStore.do")
    Observable<JSONObject> addBankCard(@Query("FBankNo")String fBankNo,@Query("smsCode")String smsCode,@Query("FBankName")String fBankName,@Query("FName")String fName,@Query("token")String token);

    /**
     *  充值积分（余额充值）
     *  cash 	是 	int 	金额
     *  integral 	是 	int 	积分
     * */
    @POST("businessStore!cashAccountRechargeIntegral.do")
    Observable<JSONObject> confirmRecharge(@Query("cash")String cash,@Query("integral")String integral,@Query("token")String token);

    /**
     *  现金充值
     *  integral 	是 	int 	充值积分
     *  payType 	是 	int 	支付方式 1：微信 2:支付宝
     *  useWebPay 	否 	int 	默认1：使用app ；0：使用web
     * */
    @POST("businessStore!appRechargeIntegral.do")
    Observable<JSONObject> cashCharge(@Query("integral")String integral,@Query("payType")int payType,@Query("token")String token);
}
