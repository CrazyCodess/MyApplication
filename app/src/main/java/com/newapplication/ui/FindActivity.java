package com.newapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.newapplication.R;
import com.newapplication.bean.Goods_List;
import com.newapplication.bean.User;
import com.newapplication.util.ImageLoadOptions;
import com.newapplication.view.HeaderLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;


public class FindActivity extends ActivityBase {


    String avatar;
    PullToRefreshListView mPull;
    private ILoadingLayout loadingLayout;
    Intent intent;
    Bundle bundle;
    ListView listView;
    List<Goods_List> goodsList=new ArrayList<>();
    // BmobFile icon;
    int kinds;
    //String avatar;
    // ListView topView,middleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        /*listView=(ListView)findViewById(R.id.marget_list);*/
        initListView();
       //textView=(TextView)findViewById(R.id.marget_show);
        intent=getIntent();
        bundle=intent.getExtras();
        kinds=bundle.getInt("position");
        // topView=(ListView)findViewById(R.id.marget_top_listView);
        //middleView=(ListView)findViewById(R.id.marget_middle_listView);
        listView.setAdapter(new myAdapter(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initListView()
    {
        initTopBarForBoth("类别", R.drawable.base_action_bar_add_bg_selector, new HeaderLayout.onRightImageButtonClickListener() {
            @Override
            public void onClick() {
                startAnimActivity(AddGoodsActivity.class);
                finish();
            }
        });
        mPull=(PullToRefreshListView)findViewById(R.id.marget_list);
        loadingLayout = mPull.getLoadingLayoutProxy();
        loadingLayout.setLastUpdatedLabel("");
        loadingLayout
                .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
        loadingLayout
                .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
        loadingLayout
                .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
        // //滑动监听
        mPull.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0) {
                    loadingLayout.setLastUpdatedLabel("");
                    loadingLayout
                            .setPullLabel(getString(R.string.pull_to_refresh_top_pull));
                    loadingLayout
                            .setRefreshingLabel(getString(R.string.pull_to_refresh_top_refreshing));
                    loadingLayout
                            .setReleaseLabel(getString(R.string.pull_to_refresh_top_release));
                } else if (firstVisibleItem + visibleItemCount + 1 == totalItemCount) {
                    loadingLayout.setLastUpdatedLabel("");
                    loadingLayout
                            .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
                    loadingLayout
                            .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
                    loadingLayout
                            .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
                }
            }
        });

        // 下拉刷新监听
        mPull
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // 下拉刷新(从第一页开始装载数据)
                        queryData(0,STATE_REFRESH);
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // 上拉加载更多(加载下一页数据)
                        queryData(curPage, STATE_MORE);
                    }
                });
        listView=mPull.getRefreshableView();
        listView.setAdapter(new myAdapter(this));
    }


    static final int STATE_REFRESH=0;
    static final int STATE_MORE=1;
    private int limit = 20;		// 每页的数据是10条
    private int curPage = 0;   // 当前页的编号，从0开始

private void queryData(final int page,final int actionType)
{



    BmobQuery<Goods_List> query=new BmobQuery<>();
  /*  query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));*/
    switch (kinds){
        case 0:
            query.addWhereEqualTo("belongTo","运动");
            break;
        case 1:
            query.addWhereEqualTo("belongTo","学习");
            break;
        case 2:
            query.addWhereEqualTo("belongTo","生活");
            break;
        case 3:
            query.addWhereEqualTo("belongTo","虚拟");
            break;
        case 4:
            query.addWhereEqualTo("belongTo","娱乐");
            break;
        case 5:
            query.addWhereEqualTo("belongTo","其他");
            break;
    }
    query.setLimit(limit);
    query.setSkip(page*limit);
    query.order("-updatedAt,-createdAt");
    query.findObjects(this, new FindListener<Goods_List>() {
        @Override
        public void onSuccess(List<Goods_List> list) {

            if(list.size()>0){
                if(actionType==STATE_REFRESH){
                    curPage=0;
                    goodsList.clear();
                }
                for(Goods_List list1:list){
                    goodsList.add(list1);
                }
                curPage++;

            }else if(actionType==STATE_MORE){
                toast("没有更多物品详情！");
            }
            else if(actionType==STATE_REFRESH){
                toast("没有更多物品详情");
            }
            else if(list.size()==0){
                toast("暂时没有人发布物品");
            }
            mPull.onRefreshComplete();
        }

        @Override
        public void onError(int i, String s) {
            toast("查询失败:"+s);
            mPull.onRefreshComplete();
        }
    });
}


    public  void getAvatar(User user)
    {
        avatar=user.getAvatar();

    }



    private class myAdapter extends BaseAdapter {

        class ViewHolder{
            ImageView headph,thingph;
            TextView personName,timeText,adressText,thingDetails;
            TextView oldPrice,newPrice;
            LinearLayout layout;
        }








        class myClick implements View.OnClickListener {
           /*
           * 通过构造方法给监听器传值
           *
           *
           * */
            Goods_List goods;
            //String time;

            public myClick(Goods_List goods){
                this.goods=goods;
            }
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putSerializable("goods",goods);
                bundle.putString("avatar",avatar);
                Intent intent=new Intent(FindActivity.this,thingdetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        }





        Context context;
/*        String name,details;
        int resourceId0,resourceId1;
        String adress,time;*/
        public myAdapter(Context context)   {
            this.context=context;
        }
        /*
        * 通过构造方法传入姓名，时间，地点，头像，物品图片，详细信息
        *
        * */
/*        public myAdapter(Context context,String name,String time,String adress,String details,int resourceId0,int resourceId1){
            this.context=context;
            this.name=name;
            this.details=details;
            this.resourceId0=resourceId0;
            this.resourceId1=resourceId1;
            this.adress=adress;
            this.time=time;
        }*/
        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                //if(top)View view= LayoutInflater.inflate(R.layout.marget_top_listview_show, parent);
                final ViewHolder holder;
                //View view = LayoutInflater.from(FindActivity.this).inflate(R.layout.marget_listviewadapter,parent,false);
                if(convertView==null){
                    convertView= LayoutInflater.from(FindActivity.this).inflate(R.layout.marget_listviewadapter,parent,false);
                    holder=new ViewHolder();
                    holder.headph=(ImageView)convertView.findViewById(R.id.marget_headph);
                    holder.personName=(TextView)convertView.findViewById(R.id.marget_name);
                    holder.timeText=(TextView)convertView.findViewById(R.id.marget_time);
                    holder.adressText=(TextView)convertView.findViewById(R.id.marget_adress);
                    holder.thingph=(ImageView)convertView.findViewById(R.id.marget_thing);
                    holder.thingDetails=(TextView)convertView.findViewById(R.id.marget_thingdetails);
                    holder.newPrice=(TextView)convertView.findViewById(R.id.marget_thingNewprice);
                    holder.oldPrice=(TextView)convertView.findViewById(R.id.marget_Oldprice);
                    holder.layout=(LinearLayout)convertView.findViewById(R.id.marget_thinglinear);
                    convertView.setTag(holder);
                }
            else{
                    holder = (ViewHolder) convertView.getTag();
                }
            /*
            * 以下是设置文本的实际实现内容
            *
            *
            * */

            Goods_List goods=(Goods_List)getItem(position);
            /*BmobFile icon=goods.getIcon();
            icon.loadImage(FindActivity.this,holder.headph);*/
              BmobFile thingPhoto=goods.getThingsPhoto();
            thingPhoto.loadImage(FindActivity.this, holder.thingph);
            //Date date=new Date();
            holder.personName.setText(goods.getUsername());
            holder.adressText.setText("来自["+goods.getAdress()+"]");
            holder.thingDetails.setText(goods.getThingsDetails());
            holder.oldPrice.setText("￥"+goods.getOldPrice());
            holder.newPrice.setText("￥"+goods.getNewPrice());

            /*
            * 以下求时间差
            * */
            Calendar cal=Calendar.getInstance();
            int currentHour=cal.get(Calendar.HOUR_OF_DAY);
            int currentMinute=cal.get(Calendar.MINUTE);
            int currentDay=cal.get(Calendar.DAY_OF_MONTH);
            String createdTime=goods.getCreatedAt();
            String[] s1=createdTime.split("-",3);//得到年月日
            String[] s2=s1[2].split(" ",2);
            String[] s3=s2[1].split(":",3);
            int day=Integer.parseInt(s2[0]);//创建日
            int hour=Integer.parseInt(s3[0]);//创建时
            int minute=Integer.parseInt(s3[1]);//创建分
            if(currentDay-day>=1){
                holder.timeText.setText(currentDay-day+"天前");
            }
            else{
                if(currentHour-hour>=1){
                    holder.timeText.setText(currentHour-hour+"小时前");
                }
                else{
                    if(currentMinute-minute>=1){
                        holder.timeText.setText(currentMinute-minute+"分钟前");
                    }
                    else{
                        holder.timeText.setText("刚刚");
                    }
                }
            }










            getAvatar(BmobUser.getCurrentUser(FindActivity.this,User.class));

            if (avatar!= null && !avatar.equals("")) {
                ImageLoader.getInstance().displayImage(avatar, holder.headph,
                        ImageLoadOptions.getOptions());
            } else {
                holder.headph.setImageResource(R.mipmap.default_head);
            }








            holder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//设置文本中划线
            holder.layout.setOnClickListener(new myClick(goods));

            return convertView;
        }





    }




    private void toast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
