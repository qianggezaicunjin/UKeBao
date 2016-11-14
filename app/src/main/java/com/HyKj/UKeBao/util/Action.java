package com.HyKj.UKeBao.util;

/**
 * Created by Administrator on 2016/8/19.
 */
public enum Action {

    /**LoginModule*/
    //获取动态闪屏页背景图
    Login_getSplashBackGround,
    //登陆验证
    Login_UserLogin,
    //注册
    Login_Regist,
    //手机号是否存在
    Login_Regist_isExistence,
    //获取验证码
    Login_Regist_GetVerificationCode,
    //找回密码
    Login_ForgetPassword_getVerificationCode,
    Login_ForgetPassword,
    //获取行业类型数据
    Login_SettledAlliance_industryTypeData,
    //获取选择城市数据
    Login_SettledAlliance_chooseCity,
    //申请联盟上传图片
    Login_SettledAlliance_uploadPictures,
    //申请联盟获取店铺数据
    Login_SettledAlliance_getBusinessInfo,
    //提交申请页面上传店铺信息照片
    Login_SettledAlliance_Commit_uploadImage,
    //照片上传成功后的申请数据上传
    Login_SettledAlliance_Commit_userInfo,
    //注销
    Login_Examine_loginout,
    //获取客服电话
    Login_Examine_getCostomerService,
    //验证手机号是否存在
    Login_ForgetPassword_isExistence,

    /**Mainmodule*/
    //获取当前版本号
    Main_getVersion_num,
    //获取系统公告信息
    Main_getNoticeInfo,
    //获取用户名
    BusinessManage_GiveIntegral_getUserName,
    //赠送积分
    BusinessManage_SendIntegral,
    //获取积分记录
    BusinessManage_GiveIntegral_getRecordData,
    //获取财务数据
    BusinessManage_getFinanicalData,
    //获取财务数据实收详情
    BusinessManage_GetRealMoneyDetail,
    //获取现金流记录
    BusinessMange_getCashRecord,
    //获取买单记录
    BusinessManage_getPayRecord,
    //退款
    BusinessManage_refund,
    //店铺设置-获取店铺信息
    BusinessManage_businessSettings_getbusinessInfo,
    //店铺相册(图片可添加状态)
    BusinessManage_businessSettings_updataImageVacancy,
    //店铺相册(图片占满状态)
    BusinessManage_businessSettings_updataImage,
    //商品相册
    BusinessManage_BusinessSettings_GoodsImage,
    //提交店铺设置
    BusinessManage_businessSettings_commit,

    /**MarketingManage*/
    //获取会员数量
    MarketingManage_GetMemberCount,
    //获取营销管理
    MarketingManage_GetBusinessInfo,
    //红包和卡劵信息
    MarketingManage_GetRedPacketsAndCard,
    //获取单个卡劵详情
    MarketingManage_GetSingCardDetail,
    //获取单个红包详情
    MarketingManage_GetSingRedPacketDetail,
    //添加广告图片
    MarketingManage_RedPacketAttractCustome_AddImage,
    //发送揽客红包
    MarketingManage_RedPacketAttractCustome_sendRedPacket,
    //发送卡劵
    MarketingManage_SendCard,
    //红包领取记录
    MarketingManage_RedPacket_Collect_Record,
    //获取卡劵列表
    MarketingManage_getCardListInfo,
    //获取卡劵列表
    MarketingManage_getRedPacketListInfo,
    //判断是否为vip
    MarketingManage_isVip,
    //申请vip
    MarketingManage_ApplyVip,
    //申请vip（现金支付）
    MarketingManage_ApplyVip_CashPay,
    //申请vip（微信支付）
    MarketingManage_ApplyVip_WxPay,
    //申请vip（支付宝支付）
    MarketingManage_ApplyVip_AliPay,
    //获取升级vip信息
    MarketingManage_GetUpgradeVipInfo,

    /**UserInfoManage*/
    //获取商家信息
    UserInfoManage_GetBusinessInfo,
    //获取客服电话
    UserInfoManage_GetCustomerPhone,
    //修改密码
    UserInfoManage_ModifyPassword,
    //获取兑换信息
    UserInfoManage_GetExchangInfo,
    //确认收款
    UserInfoManage_ConfirmReceipt,
    //获取兑换记录
    UserInfoManage_GetExchangRecord,
    //申请提现
    UserInfoManage_Withdrawals,
    //获取提现记录
    UserInfoManage_getWithdrawalsRecord,
    //注销
    UserInfoManage_Cancellation,
    //银行卡信息
    UserInfoManage_BankInfo,
    //添加银行卡
    UserInfoManage_AddBankCard,
    //积分充值（余额）
    UserInfoManage_ConfirmRecharge_Balance,
    //积分充值（支付宝现金）
    UserInfoManage_CashCharge_Alipay,
    //积分充值（微信现金）
    UserInfoManage_CashCharge_WxPay,
}
