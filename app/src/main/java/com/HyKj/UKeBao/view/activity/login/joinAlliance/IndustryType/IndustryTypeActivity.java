package com.HyKj.UKeBao.view.activity.login.joinAlliance.IndustryType;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.CategoryInfo;
import com.HyKj.UKeBao.model.login.joinAlliance.IndustryType.IndustryTypeModel;
import com.HyKj.UKeBao.model.login.baen.ProductCategory;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.longin.ChooseIndustryAdapter;
import com.HyKj.UKeBao.viewModel.login.joinAlliance.IndustryType.IndustryTypeViewModel;

/**
 * 行业类型
 * Created by Administrator on 2016/8/30.
 */
public class IndustryTypeActivity extends BaseActiviy implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final static String TAG = "ChooseIndustryTypeActivity";

    private TextView titleBarName;

    private ImageButton backImageButton;

    private ListView chooseIndustryListView;

    private String[] industryTpes;

    private String chooseType;// 选择的item对应的行业类型

    private ProductCategory productCategory;//从服务器解析的数据

    private CategoryInfo chooseCategory;//选中的类型

    private IndustryTypeViewModel viewModel;


    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, IndustryTypeActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {

        setContentView(R.layout.activity_choose_industry);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        chooseIndustryListView = (ListView) findViewById(R.id.lv_chooseIndustry);

        BufferCircleDialog.show(IndustryTypeActivity.this,"正在获取行业类型..",false,null);

        viewModel = new IndustryTypeViewModel(new IndustryTypeModel(), this);
    }

    @Override
    public void setUpView() {
        //初始化标题栏
        setTitleTheme("行业类型");
        //获取行业类型数据
        getIndustryTypeData();
    }

    //获取行业类型数据
    private void getIndustryTypeData() {
        viewModel.getIndustryTypeData();
    }

    @Override
    public void setListeners() {
        backImageButton.setOnClickListener(this);

        chooseIndustryListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回
            case R.id.imb_title_bar_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.d("您点击了行业类型条目");
        if (productCategory != null) {
            chooseCategory = productCategory.getCategory().get(position);
        }
        setBackResult();
    }

    /**
     * 设置返回参数
     */
    private void setBackResult() {
        if (chooseCategory != null) {
            Intent intent = new Intent();
            intent.putExtra("industryType", chooseCategory.name);
            intent.putExtra("parentName", chooseCategory.parentName);
            intent.putExtra("discount", chooseCategory.discount);
            intent.putExtra("integral", chooseCategory.integral);
            intent.putExtra("category", chooseCategory.id);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    //设置适配器
    public void setAdapterData() {

        productCategory=viewModel.productCategory;

        chooseIndustryListView.setAdapter(new ChooseIndustryAdapter(IndustryTypeActivity.this, viewModel.productCategory.getCategory()));

        BufferCircleDialog.dialogcancel();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
