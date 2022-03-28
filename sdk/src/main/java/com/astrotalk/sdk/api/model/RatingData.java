package com.astrotalk.sdk.api.model;

import java.io.Serializable;

public class RatingData implements Serializable {

    String userName;
    int rating;
    long creatingtime;
    long updationtime;
    String review;
    String reply;
    String consultantName;
    String userPic;
    private long userId;
    private Boolean nameVisible;
    private String profile_pic;

    public Boolean getNameVisible() {
        return nameVisible;
    }

    public void setNameVisible(Boolean nameVisible) {
        this.nameVisible = nameVisible;
    }

    public boolean isIshelpfull() {
        return ishelpfull;
    }

    public void setIshelpfull(boolean ishelpfull) {
        this.ishelpfull = ishelpfull;
    }

    private boolean ishelpfull;

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public long getUpdationtime() {
        return updationtime;
    }

    public void setUpdationtime(long updationtime) {
        this.updationtime = updationtime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getCreatingtime() {
        return creatingtime;
    }

    public void setCreatingtime(long creatingtime) {
        this.creatingtime = creatingtime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}