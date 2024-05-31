package com.health.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserIndianLanguageMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private boolean reading = false;

    private boolean writing = false;

    private boolean speaking = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lan_id")
    private IndianLanguage indianlan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return reading;
    }

    public void setRead(boolean read) {
        this.reading = read;
    }

    public boolean isWrite() {
        return writing;
    }

    public void setWrite(boolean write) {
        this.writing = write;
    }

    public boolean isSpeak() {
        return speaking;
    }

    public void setSpeak(boolean speak) {
        this.speaking = speak;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IndianLanguage getIndianlan() {
        return indianlan;
    }

    public void setIndianlan(IndianLanguage indianlan) {
        this.indianlan = indianlan;
    }

    public UserIndianLanguageMapping(int id, boolean read, boolean write, boolean speak, User user,
            IndianLanguage indianlan) {
        super();
        this.id = id;
        this.reading = read;
        this.writing = write;
        this.speaking = speak;
        this.user = user;
        this.indianlan = indianlan;
    }

    public UserIndianLanguageMapping() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserIndianLanguageMapping [id=").append(id);
        sb.append(", reading=").append(reading);
        sb.append(", writing=").append(writing);
        sb.append(", speaking=").append(speaking);
        sb.append("]");
        return sb.toString();
    }

}
