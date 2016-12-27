package com.newapplication.bean;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobChatUser{


    /**
     * //显示数据拼音的首字母
     */
    private String sortLetters;

    /**
     * 性别-true-男
     */
    private Boolean sex;

    /**
     * 地理坐标
     */
    private BmobGeoPoint location;

    public BmobGeoPoint getLocation() {
        return location;
    }
    public void setLocation(BmobGeoPoint location) {
        this.location = location;
    }
    public Boolean getSex() {
        return sex;
    }
    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

}
