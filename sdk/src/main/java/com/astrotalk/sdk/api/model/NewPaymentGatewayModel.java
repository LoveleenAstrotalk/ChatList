package com.astrotalk.sdk.api.model;

import java.io.Serializable;

public class NewPaymentGatewayModel implements Serializable {

    private String icon;
    private String methodName;
    private String type;
    private long gatewayId;
    private String description;
    private long typeId;
    private Boolean isChecked=false;
    private Boolean isLastUsed=false;

    public Boolean getLastUsed() {
        return isLastUsed;
    }

    public void setLastUsed(Boolean lastUsed) {
        isLastUsed = lastUsed;
    }

    private String offerDescription="";

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(long gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getOffer_description() {
        return offerDescription;
    }

    public void setOffer_description(String offer_description) {
        this.offerDescription = offer_description;
    }
}
