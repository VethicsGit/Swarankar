
package com.swarankar.Activity.Model.ModelCaste;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCaste {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subcaste")
    @Expose
    private String subcaste;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubcaste() {
        return subcaste;
    }

    public void setSubcaste(String subcaste) {
        this.subcaste = subcaste;
    }

}
