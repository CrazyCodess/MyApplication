package com.newapplication.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import java.util.Date;

/**
 * 失物招领列表
 * Created by blisso lee on 2015/10/30.
 */

public class Found extends BmobObject {
   //失物名称
    private String find_Item;
    //失物发现时间
    private Date find_Date;
    //失物发现地点
    private String find_Address;
    //失物简单描述
    private String lost_Thing_Describe;
    //失物是否被领走
    private Boolean is_In_Ready=false;
    //失物发布者
    private User Publisher;
    //失物者联系电话
    private String find_Phone;

    public String getFind_Phone() {
        return find_Phone;
    }

    public void setFind_Phone(String find_Phone) {
        this.find_Phone = find_Phone;
    }

    public User getPublisher() {
        return Publisher;
    }

    public void setPublisher(User publisher) {
        Publisher = publisher;
    }



    public Date getFind_Date() {
        return find_Date;
    }

    public void setFind_Date(Date find_Date) {
        this.find_Date = find_Date;
    }

    public String getFind_Address() {
        return find_Address;
    }

    public void setFind_Address(String find_Address) {
        this.find_Address = find_Address;
    }

    public String getLost_Thing_Describe() {
        return lost_Thing_Describe;
    }

    public void setLost_Thing_Describe(String lost_Thing_Describe) {
        this.lost_Thing_Describe = lost_Thing_Describe;
    }

    public Boolean getIs_In_Ready() {
        return is_In_Ready;
    }

    public void setIs_In_Ready(Boolean is_In_Ready) {
        this.is_In_Ready = is_In_Ready;
    }

    public String getFind_Item() {
        return find_Item;
    }

    public void setFind_Item(String find_Item) {
        this.find_Item = find_Item;
    }
}
