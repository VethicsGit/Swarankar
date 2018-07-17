
package com.swarankar.Activity.Model.UpdateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUpadateProfile {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getProfile_completion() {
        return profile_completion;
    }

    public void setProfile_completion(String profile_completion) {
        this.profile_completion = profile_completion;
    }

    @SerializedName("profile_completion")
    @Expose
    private String profile_completion;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
