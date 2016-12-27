package com.newapplication.bean;

import cn.bmob.v3.BmobObject;

public class Lost extends BmobObject {
    //失物名称
    private String name_Item;
    //失物描述
    private String describe_Item;
    //联系电话
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName_Item() {
        return name_Item;
    }

    public void setName_Item(String name_Item) {
        this.name_Item = name_Item;
    }

    public String getDescribe_Item() {
        return describe_Item;
    }

    public void setDescribe_Item(String describe_Item) {
        this.describe_Item = describe_Item;
    }


}
