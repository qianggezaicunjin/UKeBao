package com.HyKj.UKeBao.model.businessManage.bean;

/**
 * 财务数据
 * Created by Administrator on 2016/9/27.
 */
public class FinancialManage {
    public String msg;

    public FinancialInfo rows;

    public String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FinancialInfo getRows() {
        return rows;
    }

    public void setRows(FinancialInfo rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FinancialManage{" +
                "msg='" + msg + '\'' +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                '}';
    }
}
