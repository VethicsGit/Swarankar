
package com.swarankar.Activity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("event_title")
    @Expose
    private String eventTitle;
    @SerializedName("event_image")
    @Expose
    private String eventImage;
    @SerializedName("event_gallery_image")
    @Expose
    private String eventGalleryImage;
    @SerializedName("event_description")
    @Expose
    private String eventDescription;
    @SerializedName("event_venue")
    @Expose
    private String eventVenue;
    @SerializedName("event_start")
    @Expose
    private String eventStart;
    @SerializedName("event_end")
    @Expose
    private String eventEnd;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventGalleryImage() {
        return eventGalleryImage;
    }

    public void setEventGalleryImage(String eventGalleryImage) {
        this.eventGalleryImage = eventGalleryImage;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
