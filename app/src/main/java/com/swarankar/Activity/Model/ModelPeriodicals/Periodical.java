
package com.swarankar.Activity.Model.ModelPeriodicals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Periodical {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("periodical_name")
    @Expose
    private String periodicalName;
    @SerializedName("image_front")
    @Expose
    private String imageFront;
    @SerializedName("image_back")
    @Expose
    private String imageBack;
    @SerializedName("pdf")
    @Expose
    private String pdf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriodicalName() {
        return periodicalName;
    }

    public void setPeriodicalName(String periodicalName) {
        this.periodicalName = periodicalName;
    }

    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }

    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

}
