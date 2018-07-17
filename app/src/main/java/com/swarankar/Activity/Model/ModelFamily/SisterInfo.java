
package com.swarankar.Activity.Model.ModelFamily;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SisterInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("s_husbund_name")
    @Expose
    private String sHusbundName;
    @SerializedName("s_child")
    @Expose
    private String sChild;
    @SerializedName("relation")
    @Expose
    private String relation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSHusbundName() {
        return sHusbundName;
    }

    public void setSHusbundName(String sHusbundName) {
        this.sHusbundName = sHusbundName;
    }

    public String getSChild() {
        return sChild;
    }

    public void setSChild(String sChild) {
        this.sChild = sChild;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}
