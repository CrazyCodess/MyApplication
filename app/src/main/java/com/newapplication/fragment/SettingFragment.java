package com.newapplication.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.listener.UpdateListener;

import com.newapplication.CustomApplication;
import com.newapplication.R;
import com.newapplication.ui.BlackListActivity;
import com.newapplication.ui.FragmentBase;
import com.newapplication.ui.LoginActivity;
import com.newapplication.ui.SetMyInfoActivity;
import com.newapplication.util.SharePreferenceUtil;
import com.newapplication.view.dialog.DialogTips;

/**
 * 设置
 */
@SuppressLint("SimpleDateFormat")
public class SettingFragment extends FragmentBase implements OnClickListener{

    Button btn_logout;
    TextView tv_set_name;
    RelativeLayout layout_info, rl_switch_voice,
            rl_switch_vibrate,layout_blacklist;

    ImageButton iv_open_notification, iv_close_notification, iv_open_voice,
            iv_close_voice, iv_open_vibrate, iv_close_vibrate;

    View view1,view2;
    SharePreferenceUtil mSharedUtil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedUtil = mApplication.getSpUtil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        initTopBarForOnlyTitle("设置");
        //黑名单列表
        layout_blacklist = (RelativeLayout) findViewById(R.id.layout_blacklist);
        layout_info = (RelativeLayout) findViewById(R.id.layout_info);
        rl_switch_voice = (RelativeLayout) findViewById(R.id.rl_switch_voice);
        rl_switch_vibrate = (RelativeLayout) findViewById(R.id.rl_switch_vibrate);
        layout_blacklist.setOnClickListener(this);
        layout_info.setOnClickListener(this);

        iv_open_notification = (ImageButton) findViewById(R.id.iv_open_notification);
        iv_close_notification = (ImageButton) findViewById(R.id.iv_close_notification);
        iv_open_voice = (ImageButton) findViewById(R.id.iv_open_voice);
        iv_close_voice = (ImageButton) findViewById(R.id.iv_close_voice);
        iv_open_vibrate = (ImageButton) findViewById(R.id.iv_open_vibrate);
        iv_close_vibrate = (ImageButton) findViewById(R.id.iv_close_vibrate);
        iv_open_notification.setOnClickListener(this);
        iv_close_notification.setOnClickListener(this);
        iv_open_voice.setOnClickListener(this);
        iv_close_voice.setOnClickListener(this);
        iv_open_vibrate.setOnClickListener(this);
        iv_close_vibrate.setOnClickListener(this);

        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);

        tv_set_name = (TextView) findViewById(R.id.tv_set_name);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        // 初始化
        boolean isAllowNotify = mSharedUtil.isAllowPushNotify();

        if (isAllowNotify) {
            iv_open_notification.setVisibility(View.VISIBLE);
            iv_close_notification.setVisibility(View.INVISIBLE);
        } else {
            iv_open_notification.setVisibility(View.INVISIBLE);
            iv_close_notification.setVisibility(View.VISIBLE);
        }
        boolean isAllowVoice = mSharedUtil.isAllowVoice();
        if (isAllowVoice) {
            iv_open_voice.setVisibility(View.VISIBLE);
            iv_close_voice.setVisibility(View.INVISIBLE);
        } else {
            iv_open_voice.setVisibility(View.INVISIBLE);
            iv_close_voice.setVisibility(View.VISIBLE);
        }
        boolean isAllowVibrate = mSharedUtil.isAllowVibrate();
        if (isAllowVibrate) {
            iv_open_vibrate.setVisibility(View.VISIBLE);
            iv_close_vibrate.setVisibility(View.INVISIBLE);
        } else {
            iv_open_vibrate.setVisibility(View.INVISIBLE);
            iv_close_vibrate.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        tv_set_name.setText(BmobUserManager.getInstance(getActivity())
                .getCurrentUser().getUsername());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_blacklist:// 启动到黑名单页面
                startAnimActivity(new Intent(getActivity(),BlackListActivity.class));
                break;
            case R.id.layout_info:// 启动到个人资料页面
                Intent intent =new Intent(getActivity(),SetMyInfoActivity.class);
                intent.putExtra("from", "me");
                startActivity(intent);
                break;
            case R.id.btn_logout:
                showDialog();

                break;
            case R.id.iv_open_notification:
                iv_open_notification.setVisibility(View.INVISIBLE);
                iv_close_notification.setVisibility(View.VISIBLE);
                mSharedUtil.setPushNotifyEnable(false);
                rl_switch_vibrate.setVisibility(View.GONE);
                rl_switch_voice.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                break;
            case R.id.iv_close_notification:
                iv_open_notification.setVisibility(View.VISIBLE);
                iv_close_notification.setVisibility(View.INVISIBLE);
                mSharedUtil.setPushNotifyEnable(true);
                rl_switch_vibrate.setVisibility(View.VISIBLE);
                rl_switch_voice.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_open_voice:
                iv_open_voice.setVisibility(View.INVISIBLE);
                iv_close_voice.setVisibility(View.VISIBLE);
                mSharedUtil.setAllowVoiceEnable(false);
                break;
            case R.id.iv_close_voice:
                iv_open_voice.setVisibility(View.VISIBLE);
                iv_close_voice.setVisibility(View.INVISIBLE);
                mSharedUtil.setAllowVoiceEnable(true);
                break;
            case R.id.iv_open_vibrate:
                iv_open_vibrate.setVisibility(View.INVISIBLE);
                iv_close_vibrate.setVisibility(View.VISIBLE);
                mSharedUtil.setAllowVibrateEnable(false);
                break;
            case R.id.iv_close_vibrate:
                iv_open_vibrate.setVisibility(View.VISIBLE);
                iv_close_vibrate.setVisibility(View.INVISIBLE);
                mSharedUtil.setAllowVibrateEnable(true);
                break;
        }
    }

    private void showDialog() {
        DialogTips dialog = new DialogTips(getActivity(), "提示!", "退出登录", "确定", true, true);
        // 设置成功事件
        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int userId) {
                CustomApplication.getInstance().logout();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
            // 显示确认对话框
            dialog.show();
            dialog = null;
        }
}
