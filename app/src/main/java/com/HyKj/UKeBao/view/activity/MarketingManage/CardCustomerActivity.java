package com.HyKj.UKeBao.view.activity.marketingManage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityCardCustomerBinding;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.CardCustomerModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardInfo;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.customView.CircleImageView;
import com.HyKj.UKeBao.viewModel.marketingManage.CardCustomerViewModel;
import com.squareup.picasso.Picasso;

/**
 * 卡劵揽客
 * Created by Administrator on 2016/10/19.
 */
public class CardCustomerActivity extends BaseActiviy implements View.OnClickListener {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CardCustomerActivity.class);

        return intent;
    }

    private CircleImageView userImage;//卡劵头像

    private TextView venctureName;

    private TextView startTime_toast_card;//卡劵开始时间

    private TextView endTime_toast_card;//卡劵结束时间

    private TextView expenseNum_toast_card;//规则

    private TextView sendCard_toast_card;

    private Dialog dialog;

    private ImageView finish_card;

    private TextView cardNum_toatst_card;

    public double curryentLatitude;//当前纬度

    public double currryentLongtitude;//当前精度

    public String gradearrange;//公里数

    public String memberCount;//会员数

    public String currylocation;//当前位置

    private String startTimesrc = "";//开始日期

    private String endTimesrc = "";//结束日期

    private String full_cut_money;//满足金额

    private String discount_money;//优惠金额

    private String card_num;//卡劵数量

    private String card_limit;//每人限领张数

    private CardCustomerViewModel viewModel;

    private BusinessInfo businessInfo;

    private ActivityCardCustomerBinding mBinding;

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_card_customer);

        currylocation = getIntent().getStringExtra("currylocation");

        curryentLatitude = getIntent().getDoubleExtra("curryentLatitude", 0.0);

        currryentLongtitude = getIntent().getDoubleExtra("currryentLongtitude", 0.0);

        gradearrange = getIntent().getStringExtra("gradearrange");

        memberCount = getIntent().getStringExtra("membercount");

        businessInfo = (BusinessInfo) getIntent().getSerializableExtra("businessInfo");

        mBinding.setView(this);

        viewModel = new CardCustomerViewModel(new CardCustomerModel(), this);
    }

    @Override
    public void setUpView() {
        setTitleTheme("卡劵揽客");
    }

    @Override
    public void setListeners() {
        mBinding.startTimeCardCustomerActivity.setOnClickListener(this);

        mBinding.endTimeCardCustomerActivity.setOnClickListener(this);

        mBinding.completeCardCustomerActivty.setOnClickListener(this);

        mBinding.lowestMoneyCardCustomerActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String temp = s.toString();

                int posDot = temp.indexOf("0");

                if (posDot == 0 && temp.length() >= 2) {
                    s.delete(posDot, posDot + 1);
                }
            }

        });

        mBinding.cardMoneyCardCustomerActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String temp = s.toString();

                int posDot = temp.indexOf("0");

                if (posDot == 0 && temp.length() >= 2) {
                    s.delete(posDot, posDot + 1);
                }
            }

        });

        mBinding.cardCountCardCustomerActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String temp = s.toString();

                int posDot = temp.indexOf("0");

                if (posDot == 0 && temp.length() >= 2) {
                    s.delete(posDot, posDot + 1);
                }
            }

        });

        mBinding.limitCountCardCustomerActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String temp = s.toString();

                int posDot = temp.indexOf("0");

                if (posDot == 0 && temp.length() >= 2) {
                    s.delete(posDot, posDot + 1);
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startTime_cardCustomerActivity:
                mBinding.startTimeCardCustomerActivity.setFocusable(false);
                Time time = new Time("GMT+8");
                time.setToNow();
                DatePickerDialog di = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                if (monthOfYear + 1 > 9) {
                                    if (dayOfMonth > 9) {
                                        mBinding.startTimeCardCustomerActivity.setText(year + "-"
                                                + (monthOfYear + 1) + "-"
                                                + dayOfMonth);
                                        startTimesrc = year + "" + (monthOfYear + 1) + ""
                                                + dayOfMonth + "";
                                    } else {
                                        mBinding.startTimeCardCustomerActivity.setText(year + "-"
                                                + (monthOfYear + 1) + "-" + "0"
                                                + dayOfMonth);
                                        startTimesrc = year + "" + (monthOfYear + 1)
                                                + "0" + dayOfMonth + "";
                                    }
                                } else {
                                    if (dayOfMonth > 9) {
                                        mBinding.startTimeCardCustomerActivity.setText(year + "-" + "0"
                                                + (monthOfYear + 1) + "-"
                                                + dayOfMonth);
                                        startTimesrc = year + "0"
                                                + (monthOfYear + 1)
                                                + dayOfMonth + "";
                                    } else {
                                        mBinding.startTimeCardCustomerActivity.setText(year + "-" + "0"
                                                + (monthOfYear + 1) + "-" + "0"
                                                + dayOfMonth);
                                        startTimesrc = year + "0" + (monthOfYear + 1)
                                                + "0" + dayOfMonth + "";
                                    }
                                }
                            }
                        }, time.year, time.month, time.monthDay);

                di.show();
                break;
            case R.id.endTime_cardCustomerActivity:
                Time times = new Time("GMT+8");
                times.setToNow();
                mBinding.endTimeCardCustomerActivity.setFocusable(false);
                if (mBinding.startTimeCardCustomerActivity.getText().equals("")) {
                    toast("请先输入开始时间", this);
                } else {
                    DatePickerDialog dd = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {


                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // TODO Auto-generated method stub
                                    if (monthOfYear + 1 > 9) {
                                        if (dayOfMonth > 9) {
                                            mBinding.endTimeCardCustomerActivity.setText(year + "-"
                                                    + (monthOfYear + 1) + "-"
                                                    + dayOfMonth);
                                            endTimesrc = year + "" + (monthOfYear + 1) + ""
                                                    + dayOfMonth + "";
                                        } else {
                                            mBinding.endTimeCardCustomerActivity.setText(year + "-"
                                                    + (monthOfYear + 1) + "-" + "0"
                                                    + dayOfMonth);
                                            endTimesrc = year + "" + (monthOfYear + 1) + ""
                                                    + "0" + dayOfMonth + "";
                                        }
                                    } else {
                                        if (dayOfMonth > 9) {
                                            mBinding.endTimeCardCustomerActivity.setText(year + "-" + "0"
                                                    + (monthOfYear + 1) + "-"
                                                    + dayOfMonth);
                                            endTimesrc = year + "0"
                                                    + "" + (monthOfYear + 1)
                                                    + dayOfMonth + "";
                                        } else {
                                            mBinding.endTimeCardCustomerActivity.setText(year + "-" + "0"
                                                    + (monthOfYear + 1) + "-" + "0"
                                                    + dayOfMonth);
                                            endTimesrc = year + "0" + (monthOfYear + 1)
                                                    + "0" + dayOfMonth + "";
                                        }
                                    }
                                }
                            }, times.year, times.month, times.monthDay);
                    dd.show();

                    break;
                }
            case R.id.complete_CardCustomerActivty:

                full_cut_money = mBinding.lowestMoneyCardCustomerActivity.getText().toString();

                discount_money = mBinding.cardMoneyCardCustomerActivity.getText().toString();

                card_num = mBinding.cardCountCardCustomerActivity.getText().toString();

                card_limit = mBinding.limitCountCardCustomerActivity.getText().toString();

                viewModel.verification(full_cut_money, discount_money, card_num, card_limit, startTimesrc, endTimesrc, businessInfo);

                break;
            case R.id.finish_card:
                dialog.dismiss();

                break;

            //发送卡劵
            case R.id.sendCard_toast_card:

                viewModel.sendCard(mBinding.startTimeCardCustomerActivity.getText().toString(),
                        mBinding.endTimeCardCustomerActivity.getText().toString(),
                        Integer.valueOf(card_limit),card_num,discount_money,full_cut_money,
                        mBinding.useRuleCardCustomerActivity.getText().toString(),
                        currryentLongtitude,curryentLatitude);

                break;
        }
    }

    //弹出卡劵框
    public void initDialog() {
        dialog = new Dialog(this);

        Window dialogWindow = dialog.getWindow();

        dialogWindow.setGravity(Gravity.CENTER);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.toast_card);

        userImage = (CircleImageView) dialog.findViewById(R.id.userImage_toast_card);

        venctureName = (TextView) dialog.findViewById(R.id.venctureName_toast_card);

        finish_card = (ImageView) dialog.findViewById(R.id.finish_card);

        startTime_toast_card = (TextView) dialog.findViewById(R.id.startTime_toast_card);

        cardNum_toatst_card = (TextView) dialog.findViewById(R.id.cardNum_toatst_card);

        endTime_toast_card = (TextView) dialog.findViewById(R.id.endTime_toast_card);

        expenseNum_toast_card = (TextView) dialog.findViewById(R.id.expenseNum_toast_card);

        sendCard_toast_card = (TextView) dialog.findViewById(R.id.sendCard_toast_card);

        sendCard_toast_card.setOnClickListener(this);

        finish_card.setOnClickListener(this);

        dialog.setCanceledOnTouchOutside(true);

        WindowManager m = getWindowManager();

        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        p.height = (int) (d.getHeight() * 0.75); // 高度设置为屏幕的0.75

        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65

        dialogWindow.setAttributes(p);

        dialog.show();

        loadImage(userImage);//加载头像

        venctureName.setText(businessInfo.getBusinessName());//加载名字

        cardNum_toatst_card.setText(discount_money + "");

        expenseNum_toast_card.setText("消费满" + full_cut_money + "" + "元可以使用");

        startTime_toast_card.setText(startTimesrc);

        endTime_toast_card.setText(endTimesrc);

    }

    public void loadImage(ImageView iv) {
        Picasso.with(iv.getContext())
                .load(businessInfo.getBusinessStoreImages().get(0))
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(iv);
    }

    //跳转到主界面
    public void jump(CardInfo cardInfo) {
        Intent intent= MainActivity.getStartIntent(this);

        intent.putExtra("id", cardInfo.getId());

        intent.putExtra("typeAll", 11);

        intent.putExtra("cardcount",card_num);

        intent.putExtra("nameTiltle",cardInfo.getDetails());

        intent.putExtra("curryentLatitude",curryentLatitude);

        intent.putExtra("currryentLongtitude",currryentLongtitude);

        startActivity(intent);
    }
}
