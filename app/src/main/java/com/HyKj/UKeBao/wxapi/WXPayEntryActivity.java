package com.HyKj.UKeBao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.util.LogUtil;
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
		Log.d("CashAddFragment", "onPayFinish, errCode = " + resp.errCode);
		if (resp.errCode == 0 && MyApplication.payTpye == 2) {
			LogUtil.d("微信errCode==0");

			Intent intent = new Intent(getApplicationContext(),
					RedPacketAttractCustomeActivity.class);
			intent.putExtra("wechat_pay", true);

			startActivity(intent);

			finish();
		} else if (resp.errCode == -1) {
			Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else if (resp.errCode == -2) {
			Toast.makeText(getApplicationContext(), "交易取消", Toast.LENGTH_SHORT)
					.show();
			finish();
		}else if(resp.errCode==0 && MyApplication.payTpye==1){
			//现金充值成功后，发送广播
			 Intent mIntent = new Intent(ACTION_NAME); 
               //发送广播 
             sendBroadcast(mIntent);
             finish();
			
		}
		
		else {
			finish();
		}
	}
}
