package com.astrotalk.sdk.api.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UniversalAstrologerListModel implements Serializable, Comparable {

    private String firstname;
    private String profilePic;
    private String language;
    private boolean isNotify;
    private boolean verified;
    private boolean isLiveEventOnline;
    private boolean isOffer ;
    private boolean isPo;

    public boolean isPo() {
        return isPo;
    }

    public void setPo(boolean po) {
        isPo = po;
    }

    public boolean isOffer() {
        return isOffer;
    }

    public void setOffer(boolean offer) {
        isOffer = offer;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }

    private String productTag;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    private boolean isNew;
    private String experience;
    private String url;
    private int price;
    private boolean visibleLocal;
    private boolean visible;
    private String status = "";
    private String skill;
    private int noOfRating;
    private double avgRating;
    private long id;
    private int waitListWaitTime;
    private int offlineWaitListWaitTime=0;
    private String offlineNextOnlineTime;
    private Long offlineNextOnlineTimeLong;
    private Boolean isWaitListJoined = false;
    private Boolean isWaitListJoinedChat = false;
    private Boolean isWaitListJoinedCall = false;
    private Boolean isWaitListJoinedVideoCall = false;
    private int sessionPrice;

    public int getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(int sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public Boolean getWaitListJoinedChat() {
        return isWaitListJoinedChat;
    }

    public void setWaitListJoinedChat(Boolean waitListJoinedChat) {
        isWaitListJoinedChat = waitListJoinedChat;
    }

    public Boolean getWaitListJoinedCall() {
        return isWaitListJoinedCall;
    }

    public void setWaitListJoinedCall(Boolean waitListJoinedCall) {
        isWaitListJoinedCall = waitListJoinedCall;
    }

    public Boolean getWaitListJoinedVideoCall() {
        return isWaitListJoinedVideoCall;
    }

    public void setWaitListJoinedVideoCall(Boolean waitListJoinedVideoCall) {
        isWaitListJoinedVideoCall = waitListJoinedVideoCall;
    }

    private Boolean hasOffer = false;
    private boolean isFavourite ;
    private boolean isLike=false ;
    private int waitListSize;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    private String nextOnlineTimeChat;
    private String nextOnlineTimeCall;
    private String nextOnlineTimeVideoCall;
    private int cashbackOfferValue;
    private String title;
    private boolean hasPromotionalOffer;
    private boolean isFirstSession;
    private int promotionalOfferMin;
    private String tag;
    private boolean isIntroVideoActive=false;
    private String introVideo;
    private Boolean isPoSo = false;
    private String nextCall;
    private String nextChat;
    private String nextVideoCall;

    private String statusCall;
    private String statusChat;
    private String statusVideoCall;

    private String statusCallNew;

    public String getStatusCallNew() {
        return statusCallNew;
    }

    public void setStatusCallNew(String statusCallNew) {
        this.statusCallNew = statusCallNew;
    }

    public String getStatusChatNew() {
        return statusChatNew;
    }

    public void setStatusChatNew(String statusChatNew) {
        this.statusChatNew = statusChatNew;
    }

    public String getStatusVideoCallNew() {
        return statusVideoCallNew;
    }

    public void setStatusVideoCallNew(String statusVideoCallNew) {
        this.statusVideoCallNew = statusVideoCallNew;
    }

    private String statusChatNew;
    private String statusVideoCallNew;

    private boolean visibleForChatLocal;

    public String getNextCall() {
        return nextCall;
    }

    public void setNextCall(String nextCall) {
        this.nextCall = nextCall;
    }

    public String getNextChat() {
        return nextChat;
    }

    public void setNextChat(String nextChat) {
        this.nextChat = nextChat;
    }

    public String getNextVideoCall() {
        return nextVideoCall;
    }

    public void setNextVideoCall(String nextVideoCall) {
        this.nextVideoCall = nextVideoCall;
    }

    public String getStatusCall() {
        return statusCall;
    }

    public void setStatusCall(String statusCall) {
        this.statusCall = statusCall;
    }

    public String getStatusChat() {
        return statusChat;
    }

    public void setStatusChat(String statusChat) {
        this.statusChat = statusChat;
    }

    public String getStatusVideoCall() {
        return statusVideoCall;
    }

    public void setStatusVideoCall(String statusVideoCall) {
        this.statusVideoCall = statusVideoCall;
    }

    public boolean isVisibleForChatLocal() {
        return visibleForChatLocal;
    }

    public void setVisibleForChatLocal(boolean visibleForChatLocal) {
        this.visibleForChatLocal = visibleForChatLocal;
    }

    public boolean isVisibleForVideoLocal() {
        return visibleForVideoLocal;
    }

    public void setVisibleForVideoLocal(boolean visibleForVideoLocal) {
        this.visibleForVideoLocal = visibleForVideoLocal;
    }

    public boolean isVisibleForVideoCallLocal() {
        return visibleForVideoCallLocal;
    }

    public void setVisibleForVideoCallLocal(boolean visibleForVideoCallLocal) {
        this.visibleForVideoCallLocal = visibleForVideoCallLocal;
    }

    private boolean visibleForVideoLocal;
    private boolean visibleForVideoCallLocal;



    public Boolean getPoSo() {
        return isPoSo;
    }

    public void setPoSo(Boolean poSo) {
        isPoSo = poSo;
    }

    public String getNextOnlineTimeVideoCall() {
        return nextOnlineTimeVideoCall;
    }

    public void setNextOnlineTimeVideoCall(String nextOnlineTimeVideoCall) {
        this.nextOnlineTimeVideoCall = nextOnlineTimeVideoCall;
    }

    public boolean isLiveEventOnline() {
        return isLiveEventOnline;
    }

    public void setLiveEventOnline(boolean liveEventOnline) {
        isLiveEventOnline = liveEventOnline;
    }

    public int getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(int offerPercent) {
        this.offerPercent = offerPercent;
    }

    private boolean isBoostOn;
    private String offerDisplayName;

    private String label;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(int offerPrice) {
        this.offerPrice = offerPrice;
    }

    private int offerPrice;
    private int offerPercent;

    private ArrayList<RatingData> ratingDataArrayList;

    public ArrayList<RatingData> getRatingDataArrayList() {
        return ratingDataArrayList;
    }

    public void setRatingDataArrayList(ArrayList<RatingData> ratingDataArrayList) {
        this.ratingDataArrayList = ratingDataArrayList;
    }

    public boolean isBoostOn() {
        return isBoostOn;
    }

    public void setBoostOn(boolean boostOn) {
        isBoostOn = boostOn;
    }

    public int getOfflineWaitListWaitTime() {
        return offlineWaitListWaitTime;
    }

    public void setOfflineWaitListWaitTime(int offlineWaitListWaitTime) {
        this.offlineWaitListWaitTime = offlineWaitListWaitTime;
    }

    public String getOfflineNextOnlineTime() {
        return offlineNextOnlineTime;
    }

    public void setOfflineNextOnlineTime(String offlineNextOnlineTime) {
        this.offlineNextOnlineTime = offlineNextOnlineTime;
    }

    public Long getOfflineNextOnlineTimeLong() {
        return offlineNextOnlineTimeLong;
    }

    public void setOfflineNextOnlineTimeLong(Long offlineNextOnlineTimeLong) {
        this.offlineNextOnlineTimeLong = offlineNextOnlineTimeLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCashbackOfferValue() {
        return cashbackOfferValue;
    }

    public void setCashbackOfferValue(int cashbackOfferValue) {
        this.cashbackOfferValue = cashbackOfferValue;
    }

    public Boolean getHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(Boolean hasOffer) {
        this.hasOffer = hasOffer;
    }

    public int getWaitListWaitTime() {
        return waitListWaitTime;
    }

    public void setWaitListWaitTime(int waitListWaitTime) {
        this.waitListWaitTime = waitListWaitTime;
    }

    public Boolean getWaitListJoined() {
        return isWaitListJoined;
    }

    public void setWaitListJoined(Boolean waitListJoined) {
        isWaitListJoined = waitListJoined;
    }

    public int getWaitListSize() {
        return waitListSize;
    }

    public void setWaitListSize(int waitListSize) {
        this.waitListSize = waitListSize;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVisibleLocal() {
        return visibleLocal;
    }

    public void setVisibleLocal(boolean visibleLocal) {
        this.visibleLocal = visibleLocal;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getNoOfRating() {
        return noOfRating;
    }

    public void setNoOfRating(int noOfRating) {
        this.noOfRating = noOfRating;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextOnlineTimeChat() {
        return nextOnlineTimeChat;
    }

    public void setNextOnlineTimeChat(String nextOnlineTimeChat) {
        this.nextOnlineTimeChat = nextOnlineTimeChat;
    }

    public String getNextOnlineTimeCall() {
        return nextOnlineTimeCall;
    }

    public void setNextOnlineTimeCall(String nextOnlineTimeCall) {
        this.nextOnlineTimeCall = nextOnlineTimeCall;
    }

    public boolean isHasPromotionalOffer() {
        return hasPromotionalOffer;
    }

    public void setHasPromotionalOffer(boolean hasPromotionalOffer) {
        this.hasPromotionalOffer = hasPromotionalOffer;
    }

    public int getPromotionalOfferMin() {
        return promotionalOfferMin;
    }

    public void setPromotionalOfferMin(int promotionalOfferMin) {
        this.promotionalOfferMin = promotionalOfferMin;
    }

    public boolean isFirstSession() {
        return isFirstSession;
    }

    public void setFirstSession(boolean firstSession) {
        isFirstSession = firstSession;
    }

    @Override
    public int compareTo(Object o) {
        UniversalAstrologerListModel model = (UniversalAstrologerListModel) o;

        if(model.getId()==this.id && model.getFirstname().equalsIgnoreCase(this.firstname) && model.getProfilePic().equalsIgnoreCase(this.profilePic))
            return 0;

        return 1;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isIntroVideoActive() {
        return isIntroVideoActive;
    }

    public void setIntroVideoActive(boolean introVideoActive) {
        isIntroVideoActive = introVideoActive;
    }

    public String getIntroVideo() {
        return introVideo;
    }

    public void setIntroVideo(String introVideo) {
        this.introVideo = introVideo;
    }


    public String getOfferDisplayName() {
        return offerDisplayName;
    }

    public void setOfferDisplayName(String offerDisplayName) {
        this.offerDisplayName = offerDisplayName;
    }
}