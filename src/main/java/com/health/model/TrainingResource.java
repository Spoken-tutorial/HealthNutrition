package com.health.model;

import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Column(name = "pdf_path", length = 1000)
    private String pdfPath;

    @Column(name = "doc_path", length = 1000)
    private String docPath;

    @Column(name = "excel_path", length = 1000)
    private String excelPath;

    @Column(name = "img_path", length = 1000)
    private String imgPath;

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
