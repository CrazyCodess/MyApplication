package com.newapplication.ui;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import com.newapplication.R;
import com.newapplication.bean.User;
import com.newapplication.util.CommonUtils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private EditText UserName;
	private EditText UserPassword;
	private EditText mUserPassword;
	private EditText Email;
	private Button btn_register;
	private Button	btn_cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		UserName = (EditText)findViewById(R.id.et_username);
		UserPassword= (EditText)findViewById(R.id.et_password);
		mUserPassword = (EditText)findViewById(R.id.et_SurePassword);
		Email = (EditText) findViewById(R.id.et_email);
		btn_register = (Button)findViewById(R.id.btn_register_2);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		btn_register.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final ProgressDialog mpd = new ProgressDialog(this);
		String userName = UserName.getText().toString();
		String userPassword = UserPassword.getText().toString();
		String muserPassword = mUserPassword.getText().toString();
		final String email = Email.getText().toString();
		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);

		switch (v.getId()) {
		case R.id.btn_register_2:
			if(!isNetConnected){
				Toast.makeText(RegisterActivity.this, "当前网络不可用，请检查您的网络", Toast.LENGTH_LONG).show();
			}else if(userName.equals("") || userPassword.equals("")){
				Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
			}else if(email.equals("")){
				Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_LONG).show();
			}else if(!userPassword.equals(muserPassword)){
				Toast.makeText(RegisterActivity.this, "两次输入的密码不同，请重新输入", Toast.LENGTH_LONG).show();
			}else{
				final User bu = new User();
				bu.setUsername(userName);
				bu.setPassword(userPassword);
				bu.setEmail(email);
				bu.setInstallId(BmobInstallation.getInstallationId(this));
				bu.setSex(true);
				bu.setDeviceType("android");
				mpd.setTitle("请稍后");
				mpd.setMessage("正在注册");
				mpd.show();
				bu.signUp(this, new SaveListener() {
				    @Override
				    public void onSuccess() {
							mpd.setMessage("注册成功,请前往"+email+"认证");
							mpd.dismiss();
							userManager.bindInstallationForRegister(bu.getUsername());
							startAnimActivity(LoginActivity.class);
							finish();
				    }
				    @Override
				    public void onFailure(int code, String msg) {
				    	mpd.setMessage("注册失败:"+msg);
						mpd.dismiss();
				    }
				});
			}
			break;
		case R.id.btn_cancel:
			startAnimActivity(LoginActivity.class);
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

