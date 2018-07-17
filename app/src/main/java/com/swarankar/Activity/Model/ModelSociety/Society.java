
package com.swarankar.Activity.Model.ModelSociety;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Society {

//        [{"id":"3","user_id":"0","origin":"","profession":"","e_status":"","level_education":"","area_education":"","e_qualification":"","o_qualification":"","society_name":"rinesh","reg_status":"Unregistered","Present_p_name":"rinesh","mobile":"3625145263","Present_s_name":"rinesh","Present_s_mobile":"1456987456","email":"","address":"rinesh","country":"15","state":"297","city":"7182","pincode":"0","picture":"1497517738lcd3.jpg","total_member_counter":"10","created_at":"2017-06-15 13:45:03","updated_at":"2017-06-15 14:38:58"},{"id":"5","user_id":"0","origin":"","profession":"","e_status":"","level_education":"","area_education":"","e_qualification":"","o_qualification":"","society_name":"sdfgsdfg","reg_status":"Registered","Present_p_name":"gfhfgh","mobile":"9099265365","Present_s_name":"qqqqqqqq","Present_s_mobile":"111111111","email":"","address":"Address","country":"14","state":"","city":"","pincode":"0","picture":"1497518696lcd3.jpg","total_member_counter":"7","created_at":"2017-06-15 14:48:48","updated_at":"2017-06-15 14:54:56"}]

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("e_status")
    @Expose
    private String eStatus;
    @SerializedName("level_education")
    @Expose
    private String levelEducation;
    @SerializedName("area_education")
    @Expose
    private String areaEducation;
    @SerializedName("e_qualification")
    @Expose
    private String eQualification;
    @SerializedName("o_qualification")
    @Expose
    private String oQualification;
    @SerializedName("society_name")
    @Expose
    private String societyName;
    @SerializedName("reg_status")
    @Expose
    private String regStatus;
    @SerializedName("Present_p_name")
    @Expose
    private String presentPName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("Present_s_name")
    @Expose
    private String presentSName;
    @SerializedName("Present_s_mobile")
    @Expose
    private String presentSMobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("total_member_counter")
    @Expose
    private String totalMemberCounter;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;


    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    @SerializedName("joined")
    @Expose
    private String joined;



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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEStatus() {
        return eStatus;
    }

    public void setEStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String getLevelEducation() {
        return levelEducation;
    }

    public void setLevelEducation(String levelEducation) {
        this.levelEducation = levelEducation;
    }

    public String getAreaEducation() {
        return areaEducation;
    }

    public void setAreaEducation(String areaEducation) {
        this.areaEducation = areaEducation;
    }

    public String getEQualification() {
        return eQualification;
    }

    public void setEQualification(String eQualification) {
        this.eQualification = eQualification;
    }

    public String getOQualification() {
        return oQualification;
    }

    public void setOQualification(String oQualification) {
        this.oQualification = oQualification;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    public String getPresentPName() {
        return presentPName;
    }

    public void setPresentPName(String presentPName) {
        this.presentPName = presentPName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPresentSName() {
        return presentSName;
    }

    public void setPresentSName(String presentSName) {
        this.presentSName = presentSName;
    }

    public String getPresentSMobile() {
        return presentSMobile;
    }

    public void setPresentSMobile(String presentSMobile) {
        this.presentSMobile = presentSMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTotalMemberCounter() {
        return totalMemberCounter;
    }

    public void setTotalMemberCounter(String totalMemberCounter) {
        this.totalMemberCounter = totalMemberCounter;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
