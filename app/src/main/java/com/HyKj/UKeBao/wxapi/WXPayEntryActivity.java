package com.HyKj.UKeBao.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.RedPacketAttractCustomeActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	private final String ACTION_NAME = "successBack";

	private AlertDialog pay_success_dialog;//支付成功提示框

	private Button bt_experience;

	private ImageButton bt_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_result);
		api = WXAPIFactory.createWXAPI(this, "wx5ed23f8629dd7b90");
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("CashChargeFragment", "onPayFinish, errCode = " + resp.errCode);
		if (resp.errCode == 0 && MyApplication.payTpye == 2) {
			LogUtil.d("微信errCode==0");

			Intent intent = new Intent(getApplicationContext(),
					RedPacketAttractCustomeActivity.class);
			intent.putExtra("wechat_pay", true);

			startActivity(intent);

			finish();
		} else if (resp.errCode == -1) {
			Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();

			BufferCircleDialog.dialogcancel();

			Intent intent = new Intent(getApplicationContext(),
					RedPacketAttractCustomeActivity.class);

			intent.putExtra("wechat_pay", false);

			startActivity(intent);

			finish();
		} else if (resp.errCode == -2) {
			Toast.makeText(getApplicationContext(), "交易取消", Toast.LENGTH_SHORT).show();

			BufferCircleDialog.dialogcancel();

			Intent intent = new Intent(getApplicationContext(),
					RedPacketAttractCustomeActivity.class);

			intent.putExtra("wechat_pay", false);

			startActivity(intent);

			finish();
		}else if(resp.errCode==0 && MyApplication.payTpye==1){
			//现金充值成功后，发送广播
			 Intent mIntent = new Intent(ACTION_NAME); 
               //发送广播 
             sendBroadcast(mIntent);

             finish();
			
		}
		//揽客vip支付成功后调用
		else if(resp.errCode==0 && MyApplication.payTpye==5){
			paySuccessDialog();

		}
		
		else {
			finish();
		}
	}
	//支付成功界面
	public void paySuccessDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		View dialogContentView = View.inflate(this, R.layout.view_vip_success_dialog, null);

		//马上体验
		bt_experience = (Button) dialogContentView.findViewById(R.id.bt_experience);

		bt_back = (ImageButton) dialogContentView.findViewById(R.id.imb_title_bar_back);

		bt_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pay_success_dialog.dismiss();
			}
		});

		bt_experience.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent= MainActivity.getStartIntent(WXPayEntryActivity.this);

				intent.putExtra("vip_pay_success",true);

				pay_success_dialog.dismiss();

				startActivity(intent);

				finish();
			}
		});

		pay_success_dialog = builder.create();

		pay_success_dialog.show();

		pay_success_dialog.setCancelable(false);

		pay_success_dialog.setCanceledOnTouchOutside(false);

		// 设置dialog的大小
		// 将对话框的大小按屏幕大小的百分比设置
		Window dialogWindow = pay_success_dialog.getWindow();

		dialogWindow.setContentView(dialogContentView);

		WindowManager windowManager = getWindowManager();

		Display display = windowManager.getDefaultDisplay(); // 获取屏幕

		WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

		lp.height = (int) (display.getHeight() * 1); // 高度设置为屏幕的1

		lp.width = (int) (display.getWidth() * 1); // 宽度设置为屏幕的1

		dialogWindow.setAttributes(lp);
	}
}
