package com.example.jeffrey.imtutor;

import java.util.List;

public class Meeting {
    String meetingTitle;
    String meetingTime;
    String meetingDate;
    String meetingLocation;
    String meetingDetails;
    String organizer;
    List<String> participants;

    public Meeting(String meetingTitle,
                   String meetingTime,
                   String meetingDate,
                   String meetingLocation,
                   String meetingDetails,
                   String organizer,
                   List<String> participants) {
        this.meetingTitle = meetingTitle;
        this.meetingTime = meetingTime;
        this.meetingDate = meetingDate;
        this.meetingLocation = meetingLocation;
        this.meetingDetails = meetingDetails;
        this.organizer = organizer;
        this.participants = participants;
    }

    public Meeting(){//如果要加自定义object到firestore，必须要有一个空constructor，忘记了你就GG了，老陈记得！！！

    };

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public String getMeetingDetails() {
        return meetingDetails;
    }

    public void setMeetingDetails(String meetingDetails) {
        this.meetingDetails = meetingDetails;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }



}
