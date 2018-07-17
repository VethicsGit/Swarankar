
package com.swarankar.Activity.Model.ModelProfession;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profession {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("profession")
    @Expose
    private String profession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

}
