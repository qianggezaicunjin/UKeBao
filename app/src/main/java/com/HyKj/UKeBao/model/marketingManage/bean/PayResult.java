package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 *  支付返回结果
 * Created by Administrator on 2016/10/24.
 */
public class PayResult {
    public String _input_charset;// 参数编码， 固定值

    public String notify_url;// 服务器异步通知页面路径

    public String out_trade_no;// 商户网站唯一订单号

    public String partner;// 签约合作者身份ID

    public String payment_type;// 支付类型， 固定值

    public String seller_id;// 签约卖家支付宝账号

    public String service;// 服务接口名称， 固定值

    public String sign;

    public String sign_type;

    public String subject;// 商品名称

    public String total_fee;// 商品金额

    public String body;// 商品详情

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    @Override
    public String toString() {
        return "PayResult{" +
                "_input_charset='" + _input_charset + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", partner='" + partner + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", service='" + service + '\'' +
                ", sign='" + sign + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", subject='" + subject + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
