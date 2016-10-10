package com.HyKj.UKeBao.model.login.baen;

/**注册信息反馈
 * Created by Administrator on 2016/8/23.
 */
public class RegistInfo {

    public Boolean success;

    public String msg;//返回消息

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
