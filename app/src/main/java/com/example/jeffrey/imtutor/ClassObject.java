package com.example.jeffrey.imtutor;

import java.util.List;

public class ClassObject {
    String className;
    String classDescription;
    String classInstructor;
    List<String> classTAs;
    List<User> classStudents;
    List<Meeting> classMeetings;
    List<Post> classPosts;
    public ClassObject(){
        //empty for firestore
    }
    public ClassObject(String className, String classDescription, String classInstructor, List<String> classTA, List<User> classStudents,List<Meeting> classMeetings,List<Post> classPosts) {
        this.className = className;
        this.classDescription = classDescription;
        this.classInstructor = classInstructor;
        this.classTAs = classTA;
        this.classStudents = classStudents;
        this.classMeetings = classMeetings;
        this.classPosts = classPosts;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public String getClassInstructor() {
        return classInstructor;
    }

    public void setClassInstructor(String classInstructor) {
        this.classInstructor = classInstructor;
    }

    public List<String> getClassTAs() {
        return classTAs;
    }

    public void setClassTAs(List<String> classTA) {
        this.classTAs = classTA;
    }

    public List<User> getClassStudents() {
        return classStudents;
    }

    public void setClassStudents(List<User> classStudents) {
        this.classStudents = classStudents;
    }

    public List<Meeting> getClassMeetings() {
        return classMeetings;
    }

    public void setClassMeetings(List<Meeting> classMeetings) {
        this.classMeetings = classMeetings;
    }

    public List<Post> getClassPosts() {
        return classPosts;
    }

    public void setClassPosts(List<Post> classPosts) {
        this.classPosts = classPosts;
    }
}
