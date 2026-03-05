package com.health.model;

import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.health.utility.CommonData;

@Entity
@Table(name = "training_resource")
public class TrainingResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_res_id", nullable = false, updatable = false)
    private int trainingResourceId;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "topic_lan_id")
    private TopicLanMapping topicLanMapping;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @Column(name = "pdf_path", length = 1000)
    private String pdfPath;

    @Column(name = "doc_path", length = 1000)
    private String docPath;

    @Column(name = "excel_path", length = 1000)
    private String excelPath;

    @Column(name = "img_path", length = 1000)
    private String imgPath;

    @Column(unique = true, length = 1000)
    private String pdfToken;

    @Column(unique = true, length = 1000)
    private String docToken;

    @Column(unique = true, length = 1000)
    private String imgToken;

    @Column(unique = true, length = 1000)
    private String excelToken;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getTrainingResourceId() {
        return trainingResourceId;
    }

    public void setTrainingResourceId(int trainingResourceId) {
        this.trainingResourceId = trainingResourceId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TopicLanMapping getTopicLanMapping() {
        return topicLanMapping;
    }

    public void setTopicLanMapping(TopicLanMapping topicLanMapping) {
        this.topicLanMapping = topicLanMapping;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPdfToken() {
        return pdfToken;
    }

    public void setPdfToken(String pdfToken) {
        this.pdfToken = pdfToken;
    }

    public String getDocToken() {
        return docToken;
    }

    public void setDocToken(String docToken) {
        this.docToken = docToken;
    }

    public String getImgToken() {
        return imgToken;
    }

    public void setImgToken(String imgToken) {
        this.imgToken = imgToken;
    }

    public String getExcelToken() {
        return excelToken;
    }

    public void setExcelToken(String excelToken) {
        this.excelToken = excelToken;
    }

    public String getPdfFileNameWithorWitoutZip() {
        if (pdfPath == null || pdfPath.trim().isEmpty()) {
            return "NA";
        }
        String fileName = Paths.get(pdfPath).getFileName().toString();
        return fileName;
    }

    public String getDocFileNameWithorWitoutZip() {
        if (docPath == null || docPath.trim().isEmpty()) {
            return "NA";
        }
        String fileName = Paths.get(docPath).getFileName().toString();
        return fileName;
    }

    public String getExcelFileNameWithorWitoutZip() {
        if (excelPath == null || excelPath.trim().isEmpty()) {
            return "NA";
        }
        String fileName = Paths.get(excelPath).getFileName().toString();
        return fileName;
    }

    public String getImageFileNameWithorWitoutZip() {
        if (imgPath == null || imgPath.trim().isEmpty()) {
            return "NA";
        }
        String fileName = Paths.get(imgPath).getFileName().toString();
        return fileName;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isAfterImplementationDate() {

        LocalDate resourceDate = this.dateAdded.toLocalDateTime().toLocalDate();

        return !resourceDate.isBefore(CommonData.IMPL_DATE_TRAINING_RESOURCE);
    }

    public TrainingResource(Timestamp dateAdded, TopicLanMapping topicLanMapping, Category category, String pdfPath,
            String docPath, String excelPath, String imgPath, String pdfToken, String docToken, String imgToken,
            String excelToken, User user) {
        super();
        this.dateAdded = dateAdded;
        this.topicLanMapping = topicLanMapping;
        this.category = category;
        this.pdfPath = pdfPath;
        this.docPath = docPath;
        this.excelPath = excelPath;
        this.imgPath = imgPath;
        this.pdfToken = pdfToken;
        this.docToken = docToken;
        this.imgToken = imgToken;
        this.excelToken = excelToken;
        this.user = user;
    }

    public TrainingResource(Timestamp dateAdded, TopicLanMapping topicLanMapping, String pdfPath, String docPath,
            String excelPath, String imgPath) {

        this.dateAdded = dateAdded;
        this.topicLanMapping = topicLanMapping;
        this.pdfPath = pdfPath;
        this.docPath = docPath;
        this.excelPath = excelPath;
        this.imgPath = imgPath;
    }

    public TrainingResource() {

    }

}
