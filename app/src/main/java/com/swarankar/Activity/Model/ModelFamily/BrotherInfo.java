
package com.swarankar.Activity.Model.ModelFamily;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrotherInfo {

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
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("brotherwife")
    @Expose
    private String brotherwife;
    @SerializedName("child")
    @Expose
    private String child;

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getBrotherwife() {
        return brotherwife;
    }

    public void setBrotherwife(String brotherwife) {
        this.brotherwife = brotherwife;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

}
