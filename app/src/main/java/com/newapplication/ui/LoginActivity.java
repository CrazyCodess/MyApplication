package com.newapplication.ui;

import cn.bmob.v3.listener.SaveListener;

import com.newapplication.R;
import com.newapplication.bean.User;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private EditText UserName;
	private EditText UserPassword;
	private Button btn_login;
	private Button btn_register;
	private TextView ForgetPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		UserName = (EditText)findViewById(R.id.et_username);
		UserPassword= (EditText)findViewById(R.id.et_password);
		btn_login = (Button)findViewById(R.id.btn_login);
		btn_register = (Button)findViewById(R.id.btn_register_1);
		ForgetPassword = (TextView) findViewById(R.id.foget_password);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		ForgetPassword.setOnClickListener(this);
	}

	public void onClick(View v){
		final ProgressDialog mpd = new ProgressDialog(this);
		String userName = UserName.getText().toString();
		String userPassword = UserPassword.getText().toString();

		switch(v.getId())
		{
		case R.id.btn_login:
			if(userName.equals("")|| userPassword.equals("")){
				Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
			}else{
				final User bu = new User();
				bu.setUsername(userName);
				bu.setPassword(userPassword);
				mpd.setTitle("请稍后");
				mpd.setMessage("正在登录");
				mpd.show();  
				userManager.login(bu, new SaveListener() {
					@Override
					public void onSuccess() {
						if(bu.getEmailVerified()) {
							mpd.setMessage("登录成功");
							updateUserInfos();
							mpd.dismiss();
							startAnimActivity(MainUiActivity.class);
							finish();
						}else{
							mpd.setMessage("请先验证邮箱并激活账号");
							mpd.dismiss();
						}
					}

					public void onFailure(int code, String msg) {
						mpd.setMessage("登录失败,请输入正确的用户名和密码");
						mpd.dismiss();
					}
				});
			}
			break;
		case R.id.btn_register_1:
			startAnimActivity(RegisterActivity.class);
			break;
		case R.id.foget_password:
			startAnimActivity(ResetPasswordActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
