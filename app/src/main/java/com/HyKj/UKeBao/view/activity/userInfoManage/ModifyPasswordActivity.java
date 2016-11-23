package com.HyKj.UKeBao.view.activity.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.userInfoManage.ModifyPasswordModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.login.LoginActivity;
import com.HyKj.UKeBao.viewModel.userInfoManage.ModifyPasswordViewModel;

/**
 * 密码设置
 * Created by Administrator on 2016/10/31.
 */
public class ModifyPasswordActivity extends BaseActiviy{
    public TextView old_password;//旧密码

    public  TextView new_password;//新密码

    public TextView confirm_password;//确认密码

    private Button bt_commit;//提交

    private ModifyPasswordViewModel viewModel;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,ModifyPasswordActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_password_setting);

        old_password= (TextView) findViewById(R.id.et_old_passwdInput);

        new_password= (TextView) findViewById(R.id.et_new_passwdInput);

        confirm_password= (TextView) findViewById(R.id.et_confirm_passwdInput);

        bt_commit= (Button) findViewById(R.id.btn_complete_setting_password);

        viewModel=new ModifyPasswordViewModel(this,new ModifyPasswordModel());
    }

    @Override
    public void setUpView() {
        setTitleTheme("密码设置");
    }

    @Override
    public void setListeners() {
        //完成
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword=old_password.getText().toString().trim();

                String newPassword=new_password.getText().toString().trim();

                String confirmPassword=confirm_password.getText().toString().trim();

                commit(oldPassword,newPassword,confirmPassword);
            }
        });
    }

    private void commit(String oldPassword, String newPassword, String confirmPassword) {
        if(TextUtils.isEmpty(oldPassword)){
            toast("请输入旧密码~",this);

            return;
        }else if(TextUtils.isEmpty(newPassword)){
            toast("请输入新密码~",this);

            return;
        }else if(TextUtils.isEmpty(confirmPassword)){
            toast("请输入确认新密码~",this);

            return;
        }else if(!newPassword.equals(confirmPassword)){
            toast("密码不一致~",this);

            return;
        }else if(oldPassword.length()<6){
            toast("旧密码必须大于6位数!",this);

            return;
        }else if(newPassword.length()<6){
            toast("新密码必须大于6位数!",this);

            return;
        }else if(confirmPassword.length()<6){
            toast("确认密码必须大于6位数!",this);

            return;
        }
        else if(confirmPassword.length()<6){
            toast("旧密码和新密码不能一致!",this);

            return;
        }
        else if(newPassword.equals(oldPassword)){
            toast("新密码不能和原密码一致~",this);

            return;
        }
        BufferCircleDialog.show(this,"提交中..请稍候~",false,null);

        viewModel.commitNewPassword(oldPassword,newPassword,confirmPassword);
    }

    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }

    //跳转到登陆页
    public void jump() {
        Intent intents = new Intent();

        intents.setAction("FINISH_MAIN");

        sendBroadcast(intents);

        startActivity(LoginActivity.getStartIntent(this));

        finish();
    }
}
