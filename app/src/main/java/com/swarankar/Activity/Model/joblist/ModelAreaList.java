
package com.swarankar.Activity.Model.joblist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAreaList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("professionslist")
    @Expose
    private String professionslist;
    @SerializedName("sub")
    @Expose
    private String sub;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfessionslist() {
        return professionslist;
    }

    public void setProfessionslist(String professionslist) {
        this.professionslist = professionslist;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

}
