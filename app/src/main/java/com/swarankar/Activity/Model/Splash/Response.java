
package com.swarankar.Activity.Model.Splash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("gatepass")
    @Expose
    private String gatepass;

    public String getGatepass() {
        return gatepass;
    }

    public void setGatepass(String gatepass) {
        this.gatepass = gatepass;
    }

}
