package com.example.jeffrey.imtutor;

import java.util.List;

public class Post {
    String postTitle;
    String postContent;
    String postOwner;
    String postDate;
    String postTime;
    List<Reply> postReplies;

    public Post(){

    }

    public Post(String postTitle, String postContent, String postOwner, String postDate, String postTime, List<Reply> postReplies) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postOwner = postOwner;
        this.postDate = postDate;
        this.postTime = postTime;
        this.postReplies = postReplies;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(String postOwner) {
        this.postOwner = postOwner;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public List<Reply> getPostReplies() {
        return postReplies;
    }

    public void setPostReplies(List<Reply> postReplies) {
        this.postReplies = postReplies;
    }
}
