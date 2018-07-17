
package com.swarankar.Activity.Model.ModelState;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelState {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("s_status")
    @Expose
    private String sStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getSStatus() {
        return sStatus;
    }

    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }

}
