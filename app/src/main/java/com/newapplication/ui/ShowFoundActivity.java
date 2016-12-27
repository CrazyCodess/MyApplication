package com.newapplication.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.newapplication.bean.Found;
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
 * 展示activity,found
 */
public class ShowFoundActivity extends ActivityBase implements
        IPopupItemClick,AdapterView.OnItemLongClickListener {

    private QuickAdapter<Found> FoundAdapter;
    RelativeLayout layout_action;
    ListView listview;
    PopupWindow morePop;//用来实现一个弹出框
    RelativeLayout progress;//进度
    LinearLayout layout_no;
    TextView tv_no;
    EditPopupWindow mPopupWindow;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfound);
        initViews();
        initData();
        initListeners();
    }

    public void initViews(){
        initTopBarForBoth("招领信息", R.drawable.base_action_bar_add_bg_selector,
                new HeaderLayout.onRightImageButtonClickListener() {
                    @Override
                    public void onClick() {
                        startAnimActivity(AddFoundActivity.class);
                        finish();
                    }
                });
        progress = (RelativeLayout)findViewById(R.id.progress);
        layout_no = (LinearLayout)findViewById(R.id.layout_no);
        tv_no = (TextView) findViewById(R.id.tv_no);
        listview  = (ListView)findViewById(R.id.list_found);

        initEditPop();
    }
    public void setContentView() {
        setContentView(R.layout.activity_showfound);
    }

    public void initListeners() {
        listview.setOnItemLongClickListener(this);
    }

    public void initData(){
        if (FoundAdapter == null) {
            FoundAdapter = new QuickAdapter<Found>(this, R.layout.item_list) {
                @Override
                protected void convert(BaseAdapterHelper helper, Found found) {
                    helper.setText(R.id.tv_title, found.getFind_Item())
                            .setText(R.id.tv_describe, found.getLost_Thing_Describe())
                            .setText(R.id.tv_time, found.getCreatedAt())
                            .setText(R.id.tv_phone, found.getFind_Phone());
                }
            };
        }
        listview.setAdapter(FoundAdapter);
        queryFounds();
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


    public void queryFounds() {
        showView();
        BmobQuery<Found> query = new BmobQuery<Found>();
        query.order("-createdAt");
        query.findObjects(this, new FindListener<Found>() {
            @Override
            public void onSuccess(List<Found> arg0) {
                FoundAdapter.clear();
                if (arg0 == null || arg0.size() == 0) {
                    showErrorView(1);
                    FoundAdapter.notifyDataSetChanged();
                    return;
                }
                FoundAdapter.addAll(arg0);
                listview.setAdapter(FoundAdapter);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onError(int code, String arg0) {
                showErrorView(1);
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
        deleteFound();
    }

    private void deleteFound() {
        Found found = new Found();
        found.setObjectId(FoundAdapter.getItem(position).getObjectId());
        found.delete(this, new DeleteListener() {

            @Override
            public void onSuccess() {
                FoundAdapter.remove(position);
            }

            @Override
            public void onFailure(int code, String arg0) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

