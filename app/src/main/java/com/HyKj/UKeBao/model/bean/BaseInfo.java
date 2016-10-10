package com.HyKj.UKeBao.model.bean;

import com.HyKj.UKeBao.model.login.baen.RowsInfo;

/**
 * Created by Administrator on 2016/8/23.
 */
public class BaseInfo {
    public Boolean success;

    public String msg;

    public RowsInfo rows;

    public String status;

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


}
