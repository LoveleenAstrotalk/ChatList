package com.astrotalk.sdk.api.model;

public class ChatFlagModel {

    private long activationAdminId;
    private long creationTime;
    private long adminId;
    private String flagValue;
    private long id;
    private boolean isActive;

    public long getActivationAdminId() {
        return activationAdminId;
    }

    public void setActivationAdminId(long activationAdminId) {
        this.activationAdminId = activationAdminId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getFlagValue() {
        return flagValue;
    }

    public void setFlagValue(String flagValue) {
        this.flagValue = flagValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
