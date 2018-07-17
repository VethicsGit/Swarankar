
package com.swarankar.Activity.Model.ModelCountry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCountry {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sortname")
    @Expose
    private String sortname;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("c_status")
    @Expose
    private String cStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCStatus() {
        return cStatus;
    }

    public void setCStatus(String cStatus) {
        this.cStatus = cStatus;
    }

}
