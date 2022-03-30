package com.astrotalk.sdk.api.googleApi;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class GoogleAddressModel  implements Serializable {

    private String name, state, country,district,timezone;
    private Double latitude, longitude;
    private boolean isNew;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public GoogleAddressModel() {
    }

    public String getCity() {
        return name;
    }

    public void setCity(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return latitude;
    }

    public void setLat(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLon() {
        return longitude;
    }

    public void setLon(Double longitude) {
        this.longitude = longitude;
    }
}
