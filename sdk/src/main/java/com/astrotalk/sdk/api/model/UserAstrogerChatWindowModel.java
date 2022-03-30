package com.astrotalk.sdk.api.model;

import java.io.Serializable;

public class UserAstrogerChatWindowModel implements Serializable {

    long time_ago;
    long vendor_id;
    long id;
    String message;
    long chat_id;
    boolean isSentByAdmin;
    boolean isConsultant;

    public Boolean getParentMessageSentByUser() {
        return parentMessageSentByUser;
    }

    public void setParentMessageSentByUser(Boolean parentMessageSentByUser) {
        this.parentMessageSentByUser = parentMessageSentByUser;
    }

    Boolean parentMessageSentByUser;
    long creation_time;
    long user_id;
    boolean is_read;
    long to_user_id;
    long from_user_id;
    boolean message_from_vendor_user;
    String type;
    long chatOrderId;
    private boolean isDelivered = true;
    private boolean lowBalanceText=false;
    private boolean isHighlight=false;
    private String orderStatus;
    boolean isSelectedForDelete=false;
    boolean isMessageDelete=false;
    boolean deletedForConsultant=false;
    boolean deletedForUser=false;
    boolean  sent=true;
    private boolean parentReply=false;
    private String astrologerName="";
    private boolean isShowDate = false;

    public boolean isShowDate() {
        return isShowDate;
    }

    public void setShowDate(boolean showDate) {
        isShowDate = showDate;
    }

    public String getAstrologerName() {
        return astrologerName;
    }

    public void setAstrologerName(String astrologerName) {
        this.astrologerName = astrologerName;
    }

    public String getParentMessageType() {
        return parentMessageType;
    }

    public void setParentMessageType(String parentMessageType) {
        this.parentMessageType = parentMessageType;
    }


    public boolean isParentReply() {
        return parentReply;
    }

    public void setParentReply(boolean parentReply) {
        this.parentReply = parentReply;
    }

    public String getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(String parentMessage) {
        this.parentMessage = parentMessage;
    }

    public long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public String parentMessageType;
    public String parentMessage;
    public long parentMessageId;

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    public long getTime_ago() {
        return time_ago;
    }

    public void setTime_ago(long time_ago) {
        this.time_ago = time_ago;
    }

    public long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public boolean isSentByAdmin() {
        return isSentByAdmin;
    }

    public void setSentByAdmin(boolean sentByAdmin) {
        isSentByAdmin = sentByAdmin;
    }

    public long getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(long creation_time) {
        this.creation_time = creation_time;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean is_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public long getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(long to_user_id) {
        this.to_user_id = to_user_id;
    }

    public long getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(long from_user_id) {
        this.from_user_id = from_user_id;
    }

    public boolean isMessage_from_vendor_user() {
        return message_from_vendor_user;
    }

    public void setMessage_from_vendor_user(boolean message_from_vendor_user) {
        this.message_from_vendor_user = message_from_vendor_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }


    public boolean isConsultant() {
        return isConsultant;
    }

    public void setConsultant(boolean consultant) {
        isConsultant = consultant;
    }

    public boolean isIs_read() {
        return is_read;
    }


    public long getChatOrderId() {
        return chatOrderId;
    }

    public void setChatOrderId(long chatOrderId) {
        this.chatOrderId = chatOrderId;
    }

    public boolean isLowBalanceText() {
        return lowBalanceText;
    }

    public void setLowBalanceText(boolean lowBalanceText) {
        this.lowBalanceText = lowBalanceText;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isSelectedForDelete() {
        return isSelectedForDelete;
    }

    public void setSelectedForDelete(boolean selectedForDelete) {
        isSelectedForDelete = selectedForDelete;
    }

    public boolean isMessageDelete() {
        return isMessageDelete;
    }

    public void setMessageDelete(boolean messageDelete) {
        isMessageDelete = messageDelete;
    }

    public boolean isDeletedForConsultant() {
        return deletedForConsultant;
    }

    public void setDeletedForConsultant(boolean deletedForConsultant) {
        this.deletedForConsultant = deletedForConsultant;
    }

    public boolean isDeletedForUser() {
        return deletedForUser;
    }

    public void setDeletedForUser(boolean deletedForUser) {
        this.deletedForUser = deletedForUser;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}