package com.astrotalk.sdk.api.model;

public class WaitlistModel {
    private String consultantName;
    private String tokenStatus;
    private String consultantPic;
    private String displayStatus;
    private String tokenType;
    private String wt;
    private boolean isRecharge;
    private boolean isPo;
    private int wtInSecs;

    public int getWtInSecs() {
        return wtInSecs;
    }

    public void setWtInSecs(int wtInSecs) {
        this.wtInSecs = wtInSecs;
    }

    public boolean isPo() {
        return isPo;
    }

    public void setPo(boolean po) {
        isPo = po;
    }

    private boolean isConsultantOffline;
    private boolean isAgoraVoip;
    private String callSubStatus;
    private String holdReason;
    private long offerV3id;
    private long fixedSessionId;
    private int duration;
    private double amount;
    private long liveEventId;
    private String liveEventType;
    private boolean isFromProfile=false;
    private int sessionPrice;

    public int getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(int sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public boolean isFromProfile() {
        return isFromProfile;
    }

    public void setFromProfile(boolean fromProfile) {
        isFromProfile = fromProfile;
    }

    public boolean isVideoCall() {
        return isVideoCall;
    }

    public void setVideoCall(boolean videoCall) {
        isVideoCall = videoCall;
    }

    private boolean isVideoCall;

    public String getLeStatus() {
        return leStatus;
    }

    public void setLeStatus(String leStatus) {
        this.leStatus = leStatus;
    }

    private String leStatus;

    public long getLiveEventId() {
        return liveEventId;
    }

    public void setLiveEventId(long liveEventId) {
        this.liveEventId = liveEventId;
    }

    public String getLiveEventType() {
        return liveEventType;
    }

    public void setLiveEventType(String liveEventType) {
        this.liveEventType = liveEventType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getFixedSessionId() {
        return fixedSessionId;
    }

    public void setFixedSessionId(long fixedSessionId) {
        this.fixedSessionId = fixedSessionId;
    }

    public long getOfferV3id() {
        return offerV3id;
    }

    public void setOfferV3id(long offerV3id) {
        this.offerV3id = offerV3id;
    }

    public String getHoldReason() {
        return holdReason;
    }

    public void setHoldReason(String holdReason) {
        this.holdReason = holdReason;
    }

    private int callRate;
    private boolean isHold;
    private boolean isholdUsed;
    private int waitlistsize;

    public boolean isIsholdUsed() {
        return isholdUsed;
    }

    public void setIsholdUsed(boolean isholdUsed) {
        this.isholdUsed = isholdUsed;
    }

    public int getWaitlistsize() {
        return waitlistsize;
    }

    public void setWaitlistsize(int waitlistsize) {
        this.waitlistsize = waitlistsize;
    }

    public boolean isHold() {
        return isHold;
    }

    public void setHold(boolean hold) {
        isHold = hold;
    }

    public long getReportOrderId() {
        return reportOrderId;
    }

    public void setReportOrderId(long reportOrderId) {
        this.reportOrderId = reportOrderId;
    }

    private long reportOrderId;

    public int getCallRate() {
        return callRate;
    }

    public void setCallRate(int callRate) {
        this.callRate = callRate;
    }

    public String getCallSubStatus() {
        return callSubStatus;
    }

    public void setCallSubStatus(String callSubStatus) {
        this.callSubStatus = callSubStatus;
    }

    public boolean isAgoraVoip() {
        return isAgoraVoip;
    }

    public void setAgoraVoip(boolean agoraVoip) {
        isAgoraVoip = agoraVoip;
    }

    private long id;
    private long chatOrderId;

    public long getCallingTransactionId() {
        return callingTransactionId;
    }

    public void setCallingTransactionId(long callingTransactionId) {
        this.callingTransactionId = callingTransactionId;
    }

    private long consultantId;
    private long callingTransactionId;

    public long getInprogressRemainingTimeInSec() {
        return inprogressRemainingTimeInSec;
    }

    public void setInprogressRemainingTimeInSec(long inprogressRemainingTimeInSec) {
        this.inprogressRemainingTimeInSec = inprogressRemainingTimeInSec;
    }

    private String chatStatus;
    private long inprogressRemainingTimeInSec;

    public long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(long consultantId) {
        this.consultantId = consultantId;
    }

    public long getChatOrderId() {
        return chatOrderId;
    }

    public void setChatOrderId(long chatOrderId) {
        this.chatOrderId = chatOrderId;
    }

    public String getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(String chatStatus) {
        this.chatStatus = chatStatus;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public String getConsultantPic() {
        return consultantPic;
    }

    public void setConsultantPic(String consultantPic) {
        this.consultantPic = consultantPic;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public boolean isRecharge() {
        return isRecharge;
    }

    public void setRecharge(boolean recharge) {
        isRecharge = recharge;
    }

    public boolean isConsultantOffline() {
        return isConsultantOffline;
    }

    public void setConsultantOffline(boolean consultantOffline) {
        isConsultantOffline = consultantOffline;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}