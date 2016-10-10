package com.HyKj.UKeBao.model.bean;

/**
 * Created by Administrator on 2016/9/21.
 */
public class ToUpDateInfo {
    public String msg;

    public String status;

    public Rows rows;

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

    public Rows getRows() {
        return rows;
    }

    public void setRows(Rows rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ToUpDateInfo{" +
                "msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", rows=" + rows +
                '}';
    }

    public class Rows {
        public boolean status;

        public int type;

        public String url;

        public String version;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Rows{" +
                    "status=" + status +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }

}
