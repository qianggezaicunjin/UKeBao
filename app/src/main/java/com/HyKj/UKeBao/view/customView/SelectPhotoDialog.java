package com.HyKj.UKeBao.view.customView;



import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.HyKj.UKeBao.R;

/**
 * 
 * 
 * @author chc
 * 上传照片对话框
 *
 */

public class SelectPhotoDialog {
	public Dialog mDialog;
	//拍照
	public Button btn_pz;
	//相册选择
	public Button btn_xc;
	//取消
	public Button btn_qx;
	
	public SelectPhotoDialog(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.activity_setphoto_select, null);
		mDialog = new Dialog(context, R.style.customDialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(true);
		mDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
		btn_pz = (Button) view.findViewById(R.id.btn_pz);
		btn_xc = (Button) view.findViewById(R.id.btn_xc);
		btn_qx = (Button) view.findViewById(R.id.btn_qx);
		WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();		
		// 窗口大小
		lp.dimAmount = 0.3f;
		lp.y=15;
		mDialog.getWindow().setAttributes(lp);
		
	}
	public void show() {
		mDialog.show();
	}

	public void dismiss() {
		mDialog.dismiss();
	}

}
