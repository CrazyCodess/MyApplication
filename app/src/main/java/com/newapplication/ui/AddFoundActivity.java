package com.newapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;

import com.newapplication.R;
import com.newapplication.bean.Found;
import com.newapplication.view.HeaderLayout;

/**
 * 添加失物/招领信息界面
 */
public class AddFoundActivity extends BaseActivity {


    EditText edit_title, edit_phone, edit_describe;
    String title = "";
    String describe = "";
    String phone = "";


    String old_title = "";
    String old_describe = "";
    String old_phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        initData();
    }

    public void initViews() {
        initTopBarForBoth("添加失物信息", R.drawable.base_action_bar_true_bg_selector,
                new HeaderLayout.onRightImageButtonClickListener() {
                    @Override
                    public void onClick() {
                        addByType();
                    }
                });
        edit_describe = (EditText) findViewById(R.id.edit_describe);
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
    }

    public void initData() {
        old_title = getIntent().getStringExtra("title");
        old_phone = getIntent().getStringExtra("phone");
        old_describe = getIntent().getStringExtra("describe");

        edit_title.setText(old_title);
        edit_describe.setText(old_describe);
        edit_phone.setText(old_phone);
    }



    /**
     * 根据类型添加失物/招领
     */
    private void addByType() {
        title = edit_title.getText().toString();
        describe = edit_describe.getText().toString();
        phone = edit_phone.getText().toString();

        if (TextUtils.isEmpty(title)) {
            ShowToast("请填写标题");
            return;
        }
        if (TextUtils.isEmpty(describe)) {
            ShowToast("请填写描述");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ShowToast("请填写手机");
            return;
        }
        addFound();
    }
    public void  addTest(String title,String describe,String phone){
        this.title  = title;
        this.describe = describe;
        this.phone = phone;


        addFound();

    }
    private void addFound() {
        Found found = new Found();
        found.setLost_Thing_Describe(describe);
        found.setFind_Phone(phone);
        found.setFind_Item(title);
        found.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ShowToast("招领信息添加成功!");
                startAnimActivity(ShowFoundActivity.class);
                finish();
            }

            @Override
            public void onFailure(int code, String arg0) {
                ShowToast("添加失败:" + arg0);
            }
        });
    }
}