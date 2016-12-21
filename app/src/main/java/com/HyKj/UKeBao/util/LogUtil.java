package com.HyKj.UKeBao.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/29.
 */
public class LogUtil {
    private final static LogUtil logUtil=new LogUtil();

    private LogUtil(){}

    public static void d(String msg){
       logUtil.outLog(msg);//关闭debug输出
    }

    private void outLog(String msg){
      Log.d(null,msg);
    }
}
