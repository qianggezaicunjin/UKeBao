package com.HyKj.UKeBao.util;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/*
 * 
 * @version 1.0
 * 
 */
public class SharedPreferenceHelper {

	private final StringBuffer sb;
	private SharedPreferences userCacheconfiure;
	private static final String printInfo = "ImageUrlsss";
	private static final String printInfoFirst = "PrinterInfomationFirst";
	public final SharedPreferences.Editor userEditor;
	private String stringAdd;
	/**
	 * 
	 * 
	 * @param context
	 */
	@SuppressLint("CommitPrefEdits")
	public SharedPreferenceHelper(Context context) {
		userCacheconfiure = context.getSharedPreferences(printInfo,
				Context.MODE_PRIVATE);
		 sb=new StringBuffer();
		userEditor = userCacheconfiure.edit();
	}

	/**
	 *  Additional printer information
	 */
	public static SharedPreferenceHelper instance(Context context){
		SharedPreferenceHelper helper=new SharedPreferenceHelper(context);
		return helper;
		
	}

	public void setPrintInfo(String printInfos) {
		userEditor.putString("printInfos", stringAdd+printInfos);
		userEditor.commit();

	}

	/**
	 * Do not additional printer information
 	 */
	public void setPrintInfoNoAppend(String printInfos) {
		userEditor.putString("printInfos", printInfos);
		userEditor.commit();

	}

	/**
	 * get the print information
	 * @return
	 */
	public String getPrintInfo() {
		if(userCacheconfiure.getString("printInfos","").length()!=0){
			stringAdd=userCacheconfiure.getString("printInfos","");
			return userCacheconfiure.getString("printInfos","");
		}
			stringAdd="";
		return "";
	}

	/**
	 *
	 Add the printer information selected last.
	 * @param printInfos
	 */
	public void setPrintInfoFrist(String printInfos) {
		userEditor.putString("PrinterInfomationFirst", printInfos);
		userEditor.commit();

	}

	/**
	 * Get the selected printer information, used to adjust the order of the collection,
	 * making the final selection displayed at the end of Item.
	 * @return
	 */
	public String getPrintInfoFirst() {
		if(userCacheconfiure.getString("PrinterInfomationFirst","").length()!=0){
			stringAdd=userCacheconfiure.getString("PrinterInfomationFirst","");
			return userCacheconfiure.getString("PrinterInfomationFirst","");
		}
		stringAdd="";
		return "";
	}

	/**
	 * 娓呯┖镓?湁镄勬暟鎹?
	 */
	public void removeAllData(){
		userEditor.clear();
		userEditor.commit();
	}
	public void setClientID(int s){
		userEditor.putInt("clientid", s);
		userEditor.commit();
	}
	public int getClientID(){
		if(userCacheconfiure.getInt("clientid",0)!=-1){
			//stringAdd=userCacheconfiure.getInt("clientid",0);
			return userCacheconfiure.getInt("clientid",0);
		}
		stringAdd="";
		return 0;
	}
	/**
	 **鐢ㄦ潵琛ㄧず涓婃radioButton阃変腑镄勪綅缃?
	 * @param falg
	 */
	public void setRadioButtonPosition(String positon){
		userEditor.putString("radioButtonPosition", positon);
		userEditor.commit();
	}
	/**
	 **銮峰彇涓?骇凿滃崟浜岀骇凿滃崟镄勪綅缃俊鎭?
	 * @param falg
	 */
	public String getRadioButtonPosition(){
		if(userCacheconfiure.getString("radioButtonPosition", "").contains("=")){
			return userCacheconfiure.getString("radioButtonPosition", "");
		}
		return null;
	}
	
	/**
	 * 
	 * 鍒ゆ柇鍦ㄥ箍锻婇〉闱㈡槸鍚︾偣鍑讳简杩涘叆鍏朵粬鐣岄溃
	 * 
	 */
	public void setImgEnterActivity (boolean falg){
		userEditor.putBoolean("imgClick", falg);
		userEditor.commit();
	}
	
	public Boolean getImgEnterActivity(){
		return userCacheconfiure.getBoolean("imgClick", true);
	}
	
	
	/**
	 **鐢ㄦ潵鍒ゆ柇鏄惁鎺ユ敹瀹炴椂娑堟伅锛宖alg=true 鎺ユ敹锛宖alg=false涓嶆帴鏀?
	 * @param falg
	 */
	public void setRealTimeReceiveMessage (boolean falg){
		userEditor.putBoolean("realTimeRecevieMessage", falg);
		userEditor.commit();
	}
	/**
	 **銮峰彇 realTimeRecevieMessage(瀹炴椂娑堟伅) 镄勫?
	 * 
	 */
	public Boolean getRealTimeReceiveMessage(){
		return userCacheconfiure.getBoolean("realTimeRecevieMessage", true);
	}
	/**
	 **鐢ㄦ潵鍒ゆ柇鏄惁鎺ユ敹鍚庡彴娑堟伅锛宖alg=true 鎺ユ敹锛宖alg=false涓嶆帴鏀?
	 * @param falg
	 */
	public void setBackStageReceiveMessage (boolean falg){
		userEditor.putBoolean("backStageReceiveMessage", falg);
		userEditor.commit();
	}
	/**
	 **銮峰彇 backStageReceiveMessage(鍚庡彴娑堟伅锛?镄勫?
	 * 
	 */
	public Boolean getBackStageReceiveMessage(){
		return userCacheconfiure.getBoolean("backStageReceiveMessage", true);
	}
	//SoundTone
	/**
	 **鐢ㄦ潵鍒ゆ柇鏄惁链夋秷鎭彁绀洪煶锛宖alg=true 链夛紝falg=false 娌℃湁
	 * @param falg
	 */
	public void setSoundTone (boolean falg){
		userEditor.putBoolean("soundTone", falg);
		userEditor.commit();
	}
	/**
	 **銮峰彇 soundTone(娑堟伅鎻愮ず阔筹级 镄勫?
	 * 
	 */
	public Boolean getSoundTone(){
		return userCacheconfiure.getBoolean("soundTone", true);
	}
	/**
	 **鐢ㄦ潵鍒ゆ柇鏄惁链夋秷鎭渿锷紝falg=true 链夛紝falg=false 娌℃湁
	 * @param falg
	 */
	public void setVibratedMessage (boolean falg){
		userEditor.putBoolean("vibratedMessage", falg);
		userEditor.commit();
	}
	/**
	 **銮峰彇 vibratedMessage(娑堟伅闇囧姩) 镄勫?
	 * 
	 */
	public Boolean getVibratedMessage(){
		return userCacheconfiure.getBoolean("vibratedMessage", true);
	}
	public void setCanRecordPersion (boolean falg){
		userEditor.putBoolean("canRecordPersion", falg);
		userEditor.commit();
	}
	/**
	 **銮峰彇 vibratedMessage(娑堟伅闇囧姩) 镄勫?
	 * 
	 */
	public Boolean getCanRecordPersion(){
		return userCacheconfiure.getBoolean("canRecordPersion", true);
	}

}
