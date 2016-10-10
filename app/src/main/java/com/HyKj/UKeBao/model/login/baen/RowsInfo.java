package com.HyKj.UKeBao.model.login.baen;

/**
 * 登录rows信息
 *   token 	string 	令牌
    isExamine 	int 	商家状态（0：待审核，1：审核通过，2：未填写资料，3：审核不通过）
    companyTel 	string 	客服电话
    recharge 	double 	平台现金充值积分比例
    feedBack 	string 	审核不通过原因
    businessStoreImage 	string 	商家店招
 * @author Administrator
 */
public class RowsInfo {


    /*
    * */
    public String account;//用户名

    public String businessId;

    public String businessName;//商店名称

    public String businessStoreId;//店铺id

    public String businessStoreImage;//店铺招牌

    public String cash;//现金

    public String companyTel;//客户电话

    public String feedBack;//审核不通过原因

    public String freezeCash;//冻结现金

    public String id;//用户id

    public String integral;//积分

    public String integralScale;//积分比例

    public String ip;//ip

    public int isExamine;//审核状态

    public String name;//用户昵称

    public String phone;//用户手机号

    public String recharge;//平台现金充值积分比例

    public String status;

    public String token;//令牌

    public String getBusinessStoreImage() {
        return businessStoreImage;
    }

    public void setBusinessStoreImage(String businessStoreImage) {
        this.businessStoreImage = businessStoreImage;
    }

    public String getFreezeCash() {
        return freezeCash;
    }

    public void setFreezeCash(String freezeCash) {
        this.freezeCash = freezeCash;
    }

    public RowsInfo() {
        super();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessStoreId() {
        return businessStoreId;
    }

    public void setBusinessStoreId(String businessStoreId) {
        this.businessStoreId = businessStoreId;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIntegralScale() {
        return integralScale;
    }

    public void setIntegralScale(String integralScale) {
        this.integralScale = integralScale;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(int isExamine) {
        this.isExamine = isExamine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecharge() {
        return recharge;
    }

    public void setRecharge(String recharge) {
        this.recharge = recharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RowsInfo{" +
                "account='" + account + '\'' +
                ", businessId='" + businessId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessStoreId='" + businessStoreId + '\'' +
                ", businessStoreImage='" + businessStoreImage + '\'' +
                ", cash='" + cash + '\'' +
                ", companyTel='" + companyTel + '\'' +
                ", feedBack='" + feedBack + '\'' +
                ", freezeCash='" + freezeCash + '\'' +
                ", id='" + id + '\'' +
                ", integral='" + integral + '\'' +
                ", integralScale='" + integralScale + '\'' +
                ", ip='" + ip + '\'' +
                ", isExamine=" + isExamine +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", recharge='" + recharge + '\'' +
                ", status='" + status + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
