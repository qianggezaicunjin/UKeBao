package com.HyKj.UKeBao.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveBitMapToSD {
	
	public static File saveBitmap(Bitmap bm, Context mcontex) {
		SharedPreferenceHelper myHelper = new SharedPreferenceHelper(mcontex);
		if (0 <= myHelper.getClientID() && myHelper.getClientID() < 8) {
			int a = myHelper.getClientID() + 1;
			myHelper.setClientID(a);
		} else {
			myHelper.removeAllData();
		}

//		String 	filename = Environment.getExternalStorageDirectory().getPath()
//				+ "/clip" + myHelper.getClientID() + ".png";
		String 	filename = Environment.getExternalStorageDirectory().getPath()
				+ "/youkebao/photo/";
		File pathFile=new File(filename);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		String 	path = pathFile.getPath()+"/clip"+ myHelper.getClientID() + ".png";
		File f = new File(path);
//		Toast.makeText(mcontex, path, Toast.LENGTH_LONG).show();
		FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.JPEG, 40, fOut);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
       
        try {
                fOut.flush();
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
                fOut.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return f;
	}
	
}
