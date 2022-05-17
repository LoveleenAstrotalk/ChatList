package com.astrotalk.sdk.api.model;

import java.io.Serializable;

public class ChatCompleteModel  implements Serializable {

    double pricePerMinute;
    long creationTime;
    String orderId;
    long consultantId;
    long fixedId;

    public long getFixedId() {
        return fixedId;
    }

    public void setFixedId(long fixedId) {
        this.fixedId = fixedId;
    }

    String consultantName;
    double totalPriceSpent;
    String status;
    long id;
    int duration;
    int rating;
    String review;
    long startTime;
    long endTime;
    String  consultantPic;
    long reportOrderId;
    public boolean isShowChatbtn;
    String consultantChatBtnstatus;
    private Boolean isWaitListJoined = false;
    private int waitListWaitTime;
    private String nextOnlineTimeChat;
    private String offlineNextOnlineTime;
    private Long offlineNextOnlineTimeLong;
    private int offlineWaitListWaitTime=0;
    int price;
    boolean isPrescribed;
    private String chatTypeToken;
    //These variables is used to check the PO offer PO @ 0 and PO @ 5
    private boolean isPromotional;
    private long offerV3id;
    private double pricePerMinuteNew;
    private Boolean isWaitListJoinedChat = false;
    private Boolean isWaitListJoinedCall = false;
    private Boolean isWaitListJoinedVideoCall = false;
    private String nextCall;
    private String nextChat;
    private String nextVideoCall;

    private String statusCall;
    private String statusCallNew;
    private boolean isOffer ;
    private boolean isPo;

    public boolean isOffer() {
        return isOffer;
    }

    public void setOffer(boolean offer) {
        isOffer = offer;
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

    public boolean isVisibleForChatLocal() {
        return visibleForChatLocal;
    }

    public void setVisibleForChatLocal(boolean visibleForChatLocal) {
        this.visibleForChatLocal = visibleForChatLocal;
    }

    public String getStatusCallNew() {
        return statusCallNew;
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

    public void setStatusCallNew(String statusCallNew) {
        this.statusCallNew = statusCallNew;
    }

    private String statusChat;
    private String statusVideoCall;
    private String statusChatNew;
    private String statusVideoCallNew;

    private boolean visibleForChatLocal;

    public double getPricePerMinuteNew() {
        return pricePerMinuteNew;
    }

    public void setPricePerMinuteNew(double pricePerMinuteNew) {
        this.pricePerMinuteNew = pricePerMinuteNew;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void setPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    public long getOfferV3id() {
        return offerV3id;
    }

    public void setOfferV3id(long offerV3id) {
        this.offerV3id = offerV3id;
    }

    public String getChatTypeToken() {
        return chatTypeToken;
    }

    public void setChatTypeToken(String chatTypeToken) {
        this.chatTypeToken = chatTypeToken;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(long consultantId) {
        this.consultantId = consultantId;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public double getTotalPriceSpent() {
        return totalPriceSpent;
    }

    public void setTotalPriceSpent(double totalPriceSpent) {
        this.totalPriceSpent = totalPriceSpent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }



    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getConsultantPic() {
        return consultantPic;
    }

    public void setConsultantPic(String consultantPic) {
        this.consultantPic = consultantPic;
    }

    public long getReportOrderId() {
        return reportOrderId;
    }

    public void setReportOrderId(long reportOrderId) {
        this.reportOrderId = reportOrderId;
    }

    public boolean isShowChatbtn() {
        return isShowChatbtn;
    }

    public void setShowChatbtn(boolean showChatbtn) {
        isShowChatbtn = showChatbtn;
    }

    public String getConsultantChatBtnstatus() {
        return consultantChatBtnstatus;
    }

    public void setConsultantChatBtnstatus(String consultantChatBtnstatus) {
        this.consultantChatBtnstatus = consultantChatBtnstatus;
    }

    public Boolean getWaitListJoined() {
        return isWaitListJoined;
    }

    public void setWaitListJoined(Boolean waitListJoined) {
        isWaitListJoined = waitListJoined;
    }

    public int getWaitListWaitTime() {
        return waitListWaitTime;
    }

    public void setWaitListWaitTime(int waitListWaitTime) {
        this.waitListWaitTime = waitListWaitTime;
    }

    public String getNextOnlineTimeChat() {
        return nextOnlineTimeChat;
    }

    public void setNextOnlineTimeChat(String nextOnlineTimeChat) {
        this.nextOnlineTimeChat = nextOnlineTimeChat;
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

    public int getOfflineWaitListWaitTime() {
        return offlineWaitListWaitTime;
    }

    public void setOfflineWaitListWaitTime(int offlineWaitListWaitTime) {
        this.offlineWaitListWaitTime = offlineWaitListWaitTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPrescribed() {
        return isPrescribed;
    }

    public void setPrescribed(boolean prescribed) {
        isPrescribed = prescribed;
    }
}