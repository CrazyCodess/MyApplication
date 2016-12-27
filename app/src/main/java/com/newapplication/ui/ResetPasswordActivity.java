package com.newapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.newapplication.R;
import com.newapplication.bean.User;

import cn.bmob.v3.listener.ResetPasswordByEmailListener;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener{

    private EditText Email;
    private Button Sure;
    private Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
    }

    private void initView(){
        Email = (EditText) findViewById(R.id.et_resetpassword_email);
        Sure = (Button) findViewById(R.id.btn_sure);
        Cancel = (Button) findViewById(R.id.reset_cancel);
        Sure.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        final String email = Email.getText().toString();
        switch (v.getId()) {
            case R.id.btn_sure:
                User.resetPasswordByEmail(ResetPasswordActivity.this, email, new ResetPasswordByEmailListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ResetPasswordActivity.this, "重置密码成功，请到"+email+"查看邮件并进行密码重置", Toast.LENGTH_LONG).show();
                        startAnimActivity(LoginActivity.class);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(ResetPasswordActivity.this, "重置密码失败，请检查邮箱是否填写正确", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.reset_cancel:
                startAnimActivity(LoginActivity.class);
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
