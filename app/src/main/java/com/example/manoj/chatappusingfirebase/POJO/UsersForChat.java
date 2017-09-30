package com.example.manoj.chatappusingfirebase.POJO;

/**
 * Created by Manoj on 8/2/2017.
 */

public class UsersForChat {

    String name,phoneNo,profile_Pic_Url;
    String id;

    public UsersForChat()
    {

    }

    public UsersForChat(String name, String phoneNo, String profile_Pic_Url,String id) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.profile_Pic_Url = profile_Pic_Url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfile_Pic_Url() {
        return profile_Pic_Url;
    }

    public void setProfile_Pic_Url(String profile_Pic_Url) {
        this.profile_Pic_Url = profile_Pic_Url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
