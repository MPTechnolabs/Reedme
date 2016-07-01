package com.example.reedme.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jolly on 30/6/16.
 */
public class UserDetailList  implements Serializable {

    int AppVersion;
    List<UserDetail> UserDeatilList;

    public UserDetailList(){}
    public UserDetailList(List<UserDetail> items, int appVersion) {
        this.UserDeatilList = items;
        this.AppVersion = appVersion;
    }

    public void setUserDetailItem(List<UserDetail> categories) {
        this.UserDeatilList = categories;
    }


    public List<UserDetail> getCategoryItems() {
        return this.UserDeatilList;
    }



}
