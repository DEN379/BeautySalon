package com.sakadel.salon.entity;

public class Master {
    private Long id;
    private Long user_id;
    private User user;
    private double mark;
    public Master(){

    }
    public Master(Long id, User user, double mark) {
        this.id = id;
        this.user = user;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}