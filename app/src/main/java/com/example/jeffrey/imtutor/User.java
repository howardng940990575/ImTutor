package com.example.jeffrey.imtutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    String userName;
    String userEmail;
    List<String> userClasses;
    List<Meeting> userMeetings;
    List<Post> userPosts;
    Map<String,Integer> userPoints;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<String> getUserClasses() {
        return userClasses;
    }

    public void setUserClasses(List<String> userClasses) {
        this.userClasses = userClasses;
    }

    public List<Meeting> getUserMeetings() {
        return userMeetings;
    }

    public void setUserMeetings(List<Meeting> userMeetings) {
        this.userMeetings = userMeetings;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public Map<String, Integer> getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(Map<String, Integer> userPoints) {
        this.userPoints = userPoints;
    }
    public User(){

    }

    public User(String userName, String userEmail, List<String> userClasses, List<Meeting> userMeetings, List<Post> userPosts, Map<String, Integer> userPoints) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userClasses = userClasses;
        this.userMeetings = userMeetings;
        this.userPosts = userPosts;
        this.userPoints = userPoints;
    }

}
