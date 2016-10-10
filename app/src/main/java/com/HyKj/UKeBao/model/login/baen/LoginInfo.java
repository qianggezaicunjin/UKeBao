package com.HyKj.UKeBao.model.login.baen;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LoginInfo {
    public String msg;
    public RowsInfo rows;
    public String status;
    public boolean success;

    public LoginInfo() {
        super();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RowsInfo getRows() {
        return rows;
    }

    public void setRows(RowsInfo rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
