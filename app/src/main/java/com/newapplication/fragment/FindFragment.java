package com.newapplication.fragment;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment /*implements ActionBar.TabListener*/implements View.OnClickListener{
/*
    private static final String SELECTED_ITEM="selected_item";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar=getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("二手市场").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("失物招领").setTabListener(this));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_find,null);
        //init(view);
        return view;
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        Fragment fragment;
        FragmentTransaction frat=getActivity().getSupportFragmentManager().beginTransaction();;
        switch (tab.getPosition()){
            case 0:
                fragment=new MargetFragment();

                frat.replace(R.id.container,fragment);
                frat.commit();
                break;
            case 1:
                fragment=new LostFragment();
                frat.replace(R.id.container,fragment);
                frat.commit();
                break;


        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }
*/










    //private FragmentTransaction ft;
    private List<Fragment> list=new ArrayList<Fragment>();
    ViewPager viewPager;
    TextView textView1;
    TextView textView2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_find,null);
        init(view);
        viewPager=(ViewPager)view.findViewById(R.id.find_viewPager);
        Resources resource =  getActivity().getResources();
        ColorStateList csl =  resource.getColorStateList(R.color.find_top);
        if(viewPager.getCurrentItem()==0){
            textView1.setTextColor(Color.BLACK);
            textView2.setTextColor(csl);
        }
        else{
            textView2.setTextColor(Color.BLACK);
            textView1.setTextColor(csl);
        }
        return view;
    }
    private  void init(View view)
    {
         textView1=(TextView)view.findViewById(R.id.find_textView1);
        textView2=(TextView)view.findViewById(R.id.find_textView2);
        textView1.setTextColor(Color.BLACK);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        viewPager=(ViewPager)view.findViewById(R.id.find_viewPager);
        list.add(new MargetFragment());
        list.add(new LostFragment());
        viewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager()));


    }
    @Override
    public void onClick(View v) {

        Resources resource =  getActivity().getResources();
        ColorStateList csl =  resource.getColorStateList(R.color.find_top);
        switch (v.getId()){
            case R.id.find_textView1:
                textView1.setTextColor(Color.WHITE);
                textView2.setTextColor(csl);
                viewPager.setCurrentItem(0);
                break;
            case R.id.find_textView2:
                textView2.setTextColor(Color.WHITE);
                textView1.setTextColor(csl);
                viewPager.setCurrentItem(1);
        }
    }

    class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        // 当某子项被摧毁时
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        // 判断该view是否来自对象
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }
    }
}
