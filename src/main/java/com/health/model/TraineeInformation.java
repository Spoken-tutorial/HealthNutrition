
package com.health.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TraineeInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TraineeId")
    private int TraineeId;

    private String name;

    private String email;

    private long phone;

    private int age;

    private long aadhar;

    private String gender;

    private String organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Training_id")
    private TrainingInformation traineeInfos;

    public int getTrainingId() {
        return TraineeId;
    }

    public void setTrainingId(int trainingId) {
        TraineeId = trainingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getAadhar() {
        return aadhar;
    }

    public void setAadhar(long aadhar) {
        this.aadhar = aadhar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public TrainingInformation getTraineeInfos() {
        return traineeInfos;
    }

    public void setTraineeInfos(TrainingInformation traineeInfos) {
        this.traineeInfos = traineeInfos;
    }

    public TraineeInformation(int trainingId, String name, String email, long phone, int age, long aadhar,
            String gender, String organization, TrainingInformation traineeInfos) {
        super();
        TraineeId = trainingId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.aadhar = aadhar;
        this.gender = gender;
        this.organization = organization;
        this.traineeInfos = traineeInfos;
    }

    public TraineeInformation() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TraineeInformation [TraineeId=").append(TraineeId);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", age=").append(age);
        sb.append(", aadhar=").append(aadhar);
        sb.append(", gender=").append(gender);
        sb.append(", organization=").append(organization);
        sb.append("]");
        return sb.toString();
    }

}
