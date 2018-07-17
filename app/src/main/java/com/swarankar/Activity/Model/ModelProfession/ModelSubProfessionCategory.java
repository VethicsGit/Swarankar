package com.swarankar.Activity.Model.ModelProfession;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSubProfessionCategory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("professionslist_id")
    @Expose
    private String professionslist_id;
    @SerializedName("sub_name")
    @Expose
    private String sub_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfessionslist_id() {
        return professionslist_id;
    }

    public void setProfessionslist_id(String professionslist_id) {
        this.professionslist_id = professionslist_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
}
