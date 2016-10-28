package com.HyKj.UKeBao.model.marketingManage.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */
public class RedPacket_collect_record {
    public String msg;

    public List<RedPacketDetailInfo> rows;

    public int status;

    public int total;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RedPacketDetailInfo> getRows() {
        return rows;
    }

    public void setRows(List<RedPacketDetailInfo> rows) {
        this.rows = rows;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "RedPacket_collect_record{" +
                "msg='" + msg + '\'' +
                ", rows=" + rows +
                ", status=" + status +
                ", total=" + total +
                '}';
    }
}
