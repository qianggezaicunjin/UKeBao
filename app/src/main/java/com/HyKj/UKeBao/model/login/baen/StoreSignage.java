package com.HyKj.UKeBao.model.login.baen;

/**
 * Created by Administrator on 2016/9/3.
 */
public class StoreSignage {

    public String msg;

    public UploadImageInfo getRows() {
        return rows;
    }

    public void setRows(UploadImageInfo rows) {
        this.rows = rows;
    }

    public UploadImageInfo rows;

    public String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StoreSignage{" +
                "msg='" + msg + '\'' +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                '}';
    }
}
