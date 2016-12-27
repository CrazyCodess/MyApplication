package com.newapplication.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newapplication.R;
import com.newapplication.bean.Lost;
import com.newapplication.adapter.base.BaseAdapterHelper;
import com.newapplication.adapter.QuickAdapter;
import com.newapplication.util.EditPopupWindow;
import com.newapplication.util.IPopupItemClick;
import com.newapplication.view.HeaderLayout;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

/**
 * 展示activity,lost
 */
public class ShowLostActivity extends ActivityBase implements
    IPopupItemClick,AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener{

    protected QuickAdapter<Lost> LostAdapter;
    RelativeLayout layout_action;
    ListView listview;
    PopupWindow morePop;//用来实现一个弹出框
    RelativeLayout progress;//进度

    //未有失物提醒
    LinearLayout layout_no;
    TextView tv_no;

    //弹出提醒
    EditPopupWindow mPopupWindow;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlost);
        initViews();
        initData();
        initListeners();
    }

    public void initViews(){
        initTopBarForBoth("失物信息", R.drawable.base_action_bar_add_bg_selector,
                new HeaderLayout.onRightImageButtonClickListener() {
                    @Override
                    public void onClick() {
                        startAnimActivity(AddLostActivity.class);
                        finish();
                    }
                });
        progress = (RelativeLayout)findViewById(R.id.progress);
        layout_no = (LinearLayout)findViewById(R.id.layout_no);
        tv_no = (TextView) findViewById(R.id.tv_no);
        listview  = (ListView)findViewById(R.id.list_lost);

        initEditPop();
    }

    public void setContentView() {
        setContentView(R.layout.activity_showlost);
    }
    public void initListeners(){
        listview.setOnItemClickListener(this);
    }

    @SuppressWarnings("deprecation")

    public void initData(){
        if (LostAdapter == null) {
            LostAdapter = new QuickAdapter<Lost>(this, R.layout.item_list) {
                @Override
                protected void convert(BaseAdapterHelper helper, Lost lost) {
                    helper.setText(R.id.tv_title, lost.getName_Item())
                            .setText(R.id.tv_describe, lost.getDescribe_Item())
                            .setText(R.id.tv_time, lost.getCreatedAt())
                            .setText(R.id.tv_phone, lost.getPhone());
                }
            };
        }
        listview.setAdapter(LostAdapter);
        queryLosts();
    }

    private void initEditPop() {
        mPopupWindow = new EditPopupWindow(this, 200, 48);
        mPopupWindow.setOnPopupItemClickListner(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        position = arg2;
        int[] location = new int[2];
        arg1.getLocationOnScreen(location );
        mPopupWindow.showAtLocation(arg1, Gravity.RIGHT | Gravity.TOP,
                location[0], getStateBar() + location[1]);
        return false;
    }


    private void queryLosts() {
        showView();
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.order("-createdAt");
        query.findObjects(this, new FindListener<Lost>() {
            @Override
            public void onSuccess(List<Lost> losts) {
                LostAdapter.clear();
                if (losts == null || losts.size() == 0) {
                    showErrorView(0);
                    LostAdapter.notifyDataSetChanged();
                    return;
                }
                progress.setVisibility(View.GONE);
                LostAdapter.addAll(losts);
                listview.setAdapter(LostAdapter);
            }

            @Override
            public void onError(int code, String arg0) {
                showErrorView(0);
            }
        });
    }


    private void showErrorView(int tag) {
        progress.setVisibility(View.GONE);
        listview.setVisibility(View.GONE);
        layout_no.setVisibility(View.VISIBLE);
        if (tag == 0) {
            tv_no.setText(getResources().getText(R.string.list_no_data_lost));
        } else {
            tv_no.setText(getResources().getText(R.string.list_no_data_found));
        }
    }

    private void showView() {
        listview.setVisibility(View.VISIBLE);
        layout_no.setVisibility(View.GONE);
    }

    @Override
    public void onEdit(View v) {
        Intent intent = new Intent(this, AddLostActivity.class);
        String title = "";
        String describe = "";
        String phone = "";
        intent.putExtra("describe", describe);
        intent.putExtra("phone", phone);
        intent.putExtra("title", title);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDelete(View v) {
        deleteLost();
    }

    private void deleteLost() {
        Lost lost = new Lost();
        lost.setObjectId(LostAdapter.getItem(position).getObjectId());
        lost.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                LostAdapter.remove(position);
            }

            @Override
            public void onFailure(int code, String arg0) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

