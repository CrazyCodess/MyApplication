package com.newapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.newapplication.R;

import cn.bmob.im.BmobChat;

public class MainActivity extends BaseActivity {

    private static final int GO_LOGIN = 0;
    private static final int GO_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        BmobChat.DEBUG_MODE = true;
        BmobChat.getInstance(this).init("bc48a49d18b462fd2114fe71f4f95722");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(userManager.getCurrentUser() != null) {
            updateUserInfos();
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 2000);
        }else
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_LOGIN:
                    startAnimActivity(LoginActivity.class);
                    finish();
                    break;
                case GO_MAIN:
                    startAnimActivity(MainUiActivity.class);
                    finish();
                    break;
            }
        }
    };
}

