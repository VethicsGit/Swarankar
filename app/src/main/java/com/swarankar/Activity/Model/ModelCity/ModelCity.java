
package com.swarankar.Activity.Model.ModelCity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCity {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("ct_status")
    @Expose
    private String ctStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCtStatus() {
        return ctStatus;
    }

    public void setCtStatus(String ctStatus) {
        this.ctStatus = ctStatus;
    }

}
