
package com.swarankar.Activity.Model.ModelFamily;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Family {

    @SerializedName("brother_info")
    @Expose
    private List<BrotherInfo> brotherInfo = null;
    @SerializedName("father_info")
    @Expose
    private List<FatherInfo> fatherInfo = null;
    @SerializedName("mother_info")
    @Expose
    private List<MotherInfo> motherInfo = null;
    @SerializedName("sister_info")
    @Expose
    private List<SisterInfo> sisterInfo = null;
    @SerializedName("child_info")
    @Expose
    private List<Object> childInfo = null;
    @SerializedName("wife_info")
    @Expose
    private List<Object> wifeInfo = null;
    @SerializedName("husband_info")
    @Expose
    private List<Object> husbandInfo = null;

    public List<BrotherInfo> getBrotherInfo() {
        return brotherInfo;
    }

    public void setBrotherInfo(List<BrotherInfo> brotherInfo) {
        this.brotherInfo = brotherInfo;
    }

    public List<FatherInfo> getFatherInfo() {
        return fatherInfo;
    }

    public void setFatherInfo(List<FatherInfo> fatherInfo) {
        this.fatherInfo = fatherInfo;
    }

    public List<MotherInfo> getMotherInfo() {
        return motherInfo;
    }

    public void setMotherInfo(List<MotherInfo> motherInfo) {
        this.motherInfo = motherInfo;
    }

    public List<SisterInfo> getSisterInfo() {
        return sisterInfo;
    }

    public void setSisterInfo(List<SisterInfo> sisterInfo) {
        this.sisterInfo = sisterInfo;
    }

    public List<Object> getChildInfo() {
        return childInfo;
    }

    public void setChildInfo(List<Object> childInfo) {
        this.childInfo = childInfo;
    }

    public List<Object> getWifeInfo() {
        return wifeInfo;
    }

    public void setWifeInfo(List<Object> wifeInfo) {
        this.wifeInfo = wifeInfo;
    }

    public List<Object> getHusbandInfo() {
        return husbandInfo;
    }

    public void setHusbandInfo(List<Object> husbandInfo) {
        this.husbandInfo = husbandInfo;
    }

}
