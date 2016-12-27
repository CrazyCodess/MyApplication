package com.newapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newapplication.R;
import com.newapplication.fragment.ContactsFragment;
import com.newapplication.fragment.ConversationFragment;
import com.newapplication.fragment.FindFragment;
import com.newapplication.fragment.SettingFragment;

public class MainUiActivity extends FragmentActivity implements View.OnClickListener {

    private LinearLayout mTabFind;
    private LinearLayout mTabConversation;
    private LinearLayout mTabContacts;
    private LinearLayout mTabSetting;

    private ImageButton mImgFind;
    private ImageButton mImgConversation;
    private ImageButton mImgContacts;
    private ImageButton mImgSettings;

    private TextView mTextFind;
    private TextView mTextConversation;
    private TextView mTextContacts;
    private TextView mTextSettings;

    private Fragment mFragmentFind;
    private Fragment mFragmentConversation;
    private Fragment mFragmentContacts;
    private Fragment mFragmentSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        mTabFind.setOnClickListener(this);
        mTabConversation.setOnClickListener(this);
        mTabContacts.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
    }


    private void initView() {
        mTabFind= (LinearLayout) findViewById(R.id.id_tab_find);
        mTabConversation= (LinearLayout) findViewById(R.id.id_tab_conversation);
        mTabContacts= (LinearLayout) findViewById(R.id.id_tab_contacts);
        mTabSetting= (LinearLayout) findViewById(R.id.id_tab_setting);

        mImgFind = (ImageButton) findViewById(R.id.id_img_find);
        mImgConversation = (ImageButton) findViewById(R.id.id_img_conversation);
        mImgContacts = (ImageButton) findViewById(R.id.id_img_contacts);
        mImgSettings = (ImageButton) findViewById(R.id.id_img_setting);

        mTextFind = (TextView) findViewById(R.id.id_text_find);
        mTextConversation = (TextView) findViewById(R.id.id_text_conversation);
        mTextContacts = (TextView) findViewById(R.id.id_text_contacts);
        mTextSettings = (TextView) findViewById(R.id.id_text_setting);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i)
        {
            case 0:
                if(mFragmentFind == null)
                {
                    mFragmentFind = new FindFragment();
                    transaction.add(R.id.id_content, mFragmentFind);
                }else
                {
                    transaction.show(mFragmentFind);
                }
                break;
            case 1:
                if(mFragmentConversation == null)
                {
                    mFragmentConversation = new ConversationFragment();
                    transaction.add(R.id.id_content, mFragmentConversation);
                }else
                {
                    transaction.show(mFragmentConversation);
                }
                break;
            case 2:
                if(mFragmentContacts == null)
                {
                    mFragmentContacts = new ContactsFragment();
                    transaction.add(R.id.id_content, mFragmentContacts);
                }else
                {
                    transaction.show(mFragmentContacts);
                }
                break;
            case 3:
                if(mFragmentSetting == null)
                {
                    mFragmentSetting = new SettingFragment();
                    transaction.add(R.id.id_content, mFragmentSetting);
                }else
                {
                    transaction.show(mFragmentSetting);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(mFragmentFind !=null)
            transaction.hide(mFragmentFind);
        if(mFragmentConversation !=null)
            transaction.hide(mFragmentConversation);
        if(mFragmentContacts !=null)
            transaction.hide(mFragmentContacts);
        if(mFragmentSetting !=null)
            transaction.hide(mFragmentSetting);
    }

    @Override
    public void onClick(View v) {
        Reset();
        switch (v.getId())
        {
            case R.id.id_tab_find:
                mImgFind.setImageResource(R.mipmap.ic_menu_compass_pressed);
                mTextFind.setTextColor(Color.parseColor("#17b4eb"));
                setSelect(0);
                break;
            case R.id.id_tab_conversation:
                mImgConversation.setImageResource(R.mipmap.ic_menu_start_conversation_pressed);
                mTextConversation.setTextColor(Color.parseColor("#17b4eb"));
                setSelect(1);
                break;
            case R.id.id_tab_contacts:
                mImgContacts.setImageResource(R.mipmap.ic_menu_allfriends_pressed);
                mTextContacts.setTextColor(Color.parseColor("#17b4eb"));
                setSelect(2);
                break;
            case R.id.id_tab_setting:
                mImgSettings.setImageResource(R.mipmap.ic_settings_pressed);
                mTextSettings.setTextColor(Color.parseColor("#17b4eb"));
                setSelect(3);
                break;
            default:
                break;
        }
    }

    private void Reset()
    {
        mImgFind.setImageResource(R.mipmap.ic_menu_compass_normal);
        mImgConversation.setImageResource(R.mipmap.ic_menu_start_conversation_normal);
        mImgContacts.setImageResource(R.mipmap.ic_menu_allfriends_normal);
        mImgSettings.setImageResource(R.mipmap.ic_settings_normal);

        mTextFind.setTextColor(Color.parseColor("#000000"));
        mTextConversation.setTextColor(Color.parseColor("#000000"));
        mTextContacts.setTextColor(Color.parseColor("#000000"));
        mTextSettings.setTextColor(Color.parseColor("#000000"));
    }
}
