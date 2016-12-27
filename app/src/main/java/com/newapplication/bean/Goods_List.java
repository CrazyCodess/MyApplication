package com.newapplication.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Goods_List extends BmobObject {
    String username, adress, goodsName, thingsDetails, newPrice, oldPrice, mobileNumber, belongTo;
    Date uploadTime;
    BmobFile icon, thingsPhoto;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods_List() {
        this.setTableName("Goods_List");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGoodsName(){
        return goodsName;
    }

    public void setGoodsName(String goodsName){
        this.goodsName = goodsName;
    }

    public String getThingsDetails() {
        return thingsDetails;
    }

    public void setThingsDetails(String thingsDetails) {
        this.thingsDetails = thingsDetails;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public BmobFile getThingsPhoto() {
        return thingsPhoto;
    }

    public void setThingsPhoto(BmobFile thingsPhoto) {
        this.thingsPhoto = thingsPhoto;
    }
}
