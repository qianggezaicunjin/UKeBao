package com.HyKj.UKeBao.util;

import android.app.Activity;

import java.util.Arrays;
import java.util.LinkedList;

public class ActivityManagerTools {
	  private LinkedList<Activity> activityLinkedList = new LinkedList<Activity>();
	    
	    private ActivityManagerTools() {
	    }
	    
	    private static ActivityManagerTools instance;
	    
	    public static ActivityManagerTools getInstance(){
	        if(null == instance){
	            instance = new ActivityManagerTools();
	        }
	        return instance;
	    }
	    //向list中添加Activity
	    public ActivityManagerTools addActivity(Activity activity){
	        activityLinkedList.add(activity);
	        return instance;
	    }
	    
	    //结束特定的Activity(s)
	    public ActivityManagerTools finshActivities(Class<? extends Activity>... activityClasses){
	        for (Activity activity : activityLinkedList) {
	            if( Arrays.asList(activityClasses).contains( activity.getClass() ) ){
	                activity.finish();
	            }
	        }
	        return instance;
	    }
	    
	    //结束所有的Activities
	    public ActivityManagerTools finshAllActivities() {
	        for (Activity activity : activityLinkedList) {
	            activity.finish();
	        }
	        return instance;
	    }
}
