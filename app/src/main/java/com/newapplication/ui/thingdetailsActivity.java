package com.newapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.newapplication.R;
import com.newapplication.bean.Goods_List;
import com.newapplication.util.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;

public class thingdetailsActivity extends Activity {

    Intent intent;
    Goods_List goods;
    String avatar;
    ImageView headph,thingsph;
    TextView name,time,adress,details,newPrice,oldPrice,phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thingdetails);
        intent= getIntent();
        avatar=intent.getStringExtra("avatar");
        goods=(Goods_List)intent.getSerializableExtra("goods");
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thingdetails, menu);
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

    private void initView()
    {
        headph=(ImageView)findViewById(R.id.marget_headph);
        thingsph=(ImageView)findViewById(R.id.marget_thing);
        name=(TextView)findViewById(R.id.marget_name);
        time=(TextView)findViewById(R.id.marget_time);
        adress=(TextView)findViewById(R.id.marget_adress);
        details=(TextView)findViewById(R.id.marget_thingdetails);
        newPrice=(TextView)findViewById(R.id.marget_thingNewprice);
        oldPrice=(TextView)findViewById(R.id.marget_Oldprice);
        phoneNumber=(TextView)findViewById(R.id.phoneNumber);


        if (avatar!= null && !avatar.equals("")) {
            ImageLoader.getInstance().displayImage(avatar, headph,
                    ImageLoadOptions.getOptions());
        } else {
            headph.setImageResource(R.mipmap.default_head);
        }
        goods.getThingsPhoto().loadImage(this,thingsph);
        name.setText(goods.getUsername());
        adress.setText(goods.getAdress());
        details.setText(goods.getThingsDetails());
        newPrice.setText(goods.getNewPrice());
        oldPrice.setText(goods.getOldPrice());
        phoneNumber.setText(goods.getMobileNumber());






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
            time.setText(currentDay-day+"天前");
        }
        else{
            if(currentHour-hour>=1){
                time.setText(currentHour-hour+"小时前");
            }
            else{
                if(currentMinute-minute>=1){
                    time.setText(currentMinute-minute+"分钟前");
                }
                else{
                    time.setText("刚刚");
                }
            }
        }
    }
}
