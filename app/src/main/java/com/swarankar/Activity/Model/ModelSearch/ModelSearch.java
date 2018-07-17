
package com.swarankar.Activity.Model.ModelSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSearch {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("oauth_provider")
    @Expose
    private String oauthProvider;
    @SerializedName("oauth_uid")
    @Expose
    private String oauthUid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("reset_pass_status")
    @Expose
    private String resetPassStatus;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("subcaste")
    @Expose
    private String subcaste;
    @SerializedName("other_subcast")
    @Expose
    private String otherSubcast;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("educational_qual")
    @Expose
    private String educationalQual;
    @SerializedName("institution")
    @Expose
    private String institution;
    @SerializedName("area_study")
    @Expose
    private String areaStudy;
    @SerializedName("area_study_others")
    @Expose
    private String areaStudyOthers;
    @SerializedName("status_of_education")
    @Expose
    private String statusOfEducation;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("profession_others")
    @Expose
    private String professionOthers;
    @SerializedName("profession_status")
    @Expose
    private String professionStatus;
    @SerializedName("profession_status_others")
    @Expose
    private String professionStatusOthers;
    @SerializedName("profession_industry")
    @Expose
    private String professionIndustry;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("gotra_self")
    @Expose
    private String gotraSelf;
    @SerializedName("gotra_mother")
    @Expose
    private String gotraMother;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("r_country")
    @Expose
    private String rCountry;
    @SerializedName("r_state")
    @Expose
    private String rState;
    @SerializedName("r_city")
    @Expose
    private String rCity;
    @SerializedName("address_same_per_res")
    @Expose
    private String addressSamePerRes;
    @SerializedName("p_country")
    @Expose
    private String pCountry;
    @SerializedName("p_state")
    @Expose
    private String pState;
    @SerializedName("p_city")
    @Expose
    private String pCity;
    @SerializedName("p_house_no")
    @Expose
    private String pHouseNo;
    @SerializedName("p_area")
    @Expose
    private String pArea;
    @SerializedName("p_ward_no")
    @Expose
    private String pWardNo;
    @SerializedName("p_constituency")
    @Expose
    private String pConstituency;
    @SerializedName("p_village")
    @Expose
    private String pVillage;
    @SerializedName("p_tehsil")
    @Expose
    private String pTehsil;
    @SerializedName("r_house_no")
    @Expose
    private String rHouseNo;
    @SerializedName("r_area")
    @Expose
    private String rArea;
    @SerializedName("r_ward_no")
    @Expose
    private String rWardNo;
    @SerializedName("r_constituency")
    @Expose
    private String rConstituency;
    @SerializedName("r_village")
    @Expose
    private String rVillage;
    @SerializedName("r_tehsil")
    @Expose
    private String rTehsil;
    @SerializedName("mobile2")
    @Expose
    private String mobile2;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthUid() {
        return oauthUid;
    }

    public void setOauthUid(String oauthUid) {
        this.oauthUid = oauthUid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResetPassStatus() {
        return resetPassStatus;
    }

    public void setResetPassStatus(String resetPassStatus) {
        this.resetPassStatus = resetPassStatus;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSubcaste() {
        return subcaste;
    }

    public void setSubcaste(String subcaste) {
        this.subcaste = subcaste;
    }

    public String getOtherSubcast() {
        return otherSubcast;
    }

    public void setOtherSubcast(String otherSubcast) {
        this.otherSubcast = otherSubcast;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEducationalQual() {
        return educationalQual;
    }

    public void setEducationalQual(String educationalQual) {
        this.educationalQual = educationalQual;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAreaStudy() {
        return areaStudy;
    }

    public void setAreaStudy(String areaStudy) {
        this.areaStudy = areaStudy;
    }

    public String getAreaStudyOthers() {
        return areaStudyOthers;
    }

    public void setAreaStudyOthers(String areaStudyOthers) {
        this.areaStudyOthers = areaStudyOthers;
    }

    public String getStatusOfEducation() {
        return statusOfEducation;
    }

    public void setStatusOfEducation(String statusOfEducation) {
        this.statusOfEducation = statusOfEducation;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfessionOthers() {
        return professionOthers;
    }

    public void setProfessionOthers(String professionOthers) {
        this.professionOthers = professionOthers;
    }

    public String getProfessionStatus() {
        return professionStatus;
    }

    public void setProfessionStatus(String professionStatus) {
        this.professionStatus = professionStatus;
    }

    public String getProfessionStatusOthers() {
        return professionStatusOthers;
    }

    public void setProfessionStatusOthers(String professionStatusOthers) {
        this.professionStatusOthers = professionStatusOthers;
    }

    public String getProfessionIndustry() {
        return professionIndustry;
    }

    public void setProfessionIndustry(String professionIndustry) {
        this.professionIndustry = professionIndustry;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getGotraSelf() {
        return gotraSelf;
    }

    public void setGotraSelf(String gotraSelf) {
        this.gotraSelf = gotraSelf;
    }

    public String getGotraMother() {
        return gotraMother;
    }

    public void setGotraMother(String gotraMother) {
        this.gotraMother = gotraMother;
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

    public String getRCountry() {
        return rCountry;
    }

    public void setRCountry(String rCountry) {
        this.rCountry = rCountry;
    }

    public String getRState() {
        return rState;
    }

    public void setRState(String rState) {
        this.rState = rState;
    }

    public String getRCity() {
        return rCity;
    }

    public void setRCity(String rCity) {
        this.rCity = rCity;
    }

    public String getAddressSamePerRes() {
        return addressSamePerRes;
    }

    public void setAddressSamePerRes(String addressSamePerRes) {
        this.addressSamePerRes = addressSamePerRes;
    }

    public String getPCountry() {
        return pCountry;
    }

    public void setPCountry(String pCountry) {
        this.pCountry = pCountry;
    }

    public String getPState() {
        return pState;
    }

    public void setPState(String pState) {
        this.pState = pState;
    }

    public String getPCity() {
        return pCity;
    }

    public void setPCity(String pCity) {
        this.pCity = pCity;
    }

    public String getPHouseNo() {
        return pHouseNo;
    }

    public void setPHouseNo(String pHouseNo) {
        this.pHouseNo = pHouseNo;
    }

    public String getPArea() {
        return pArea;
    }

    public void setPArea(String pArea) {
        this.pArea = pArea;
    }

    public String getPWardNo() {
        return pWardNo;
    }

    public void setPWardNo(String pWardNo) {
        this.pWardNo = pWardNo;
    }

    public String getPConstituency() {
        return pConstituency;
    }

    public void setPConstituency(String pConstituency) {
        this.pConstituency = pConstituency;
    }

    public String getPVillage() {
        return pVillage;
    }

    public void setPVillage(String pVillage) {
        this.pVillage = pVillage;
    }

    public String getPTehsil() {
        return pTehsil;
    }

    public void setPTehsil(String pTehsil) {
        this.pTehsil = pTehsil;
    }

    public String getRHouseNo() {
        return rHouseNo;
    }

    public void setRHouseNo(String rHouseNo) {
        this.rHouseNo = rHouseNo;
    }

    public String getRArea() {
        return rArea;
    }

    public void setRArea(String rArea) {
        this.rArea = rArea;
    }

    public String getRWardNo() {
        return rWardNo;
    }

    public void setRWardNo(String rWardNo) {
        this.rWardNo = rWardNo;
    }

    public String getRConstituency() {
        return rConstituency;
    }

    public void setRConstituency(String rConstituency) {
        this.rConstituency = rConstituency;
    }

    public String getRVillage() {
        return rVillage;
    }

    public void setRVillage(String rVillage) {
        this.rVillage = rVillage;
    }

    public String getRTehsil() {
        return rTehsil;
    }

    public void setRTehsil(String rTehsil) {
        this.rTehsil = rTehsil;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
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
