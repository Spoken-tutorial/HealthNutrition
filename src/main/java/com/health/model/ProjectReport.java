
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
@Table(name = "project_report")
public class ProjectReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_rep_id", nullable = false, updatable = false)
    private int projectReportId;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "pdf_path", length = 1000)
    private String pdfPath;

    @Column(name = "doc_path", length = 1000)
    private String docPath;

    @Column(name = "excel_path", length = 1000)
    private String excelPath;

    @Column(name = "img_path", length = 1000)
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "state_district_id")
    private StateDistrictMapping stateDistrictMapping;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public int getProjectReportId() {
        return projectReportId;
    }

    public void setProjectReportId(int projectReportId) {
        this.projectReportId = projectReportId;
    }

    public StateDistrictMapping getStateDistrictMapping() {
        return stateDistrictMapping;
    }

    public void setStateDistrictMapping(StateDistrictMapping stateDistrictMapping) {
        this.stateDistrictMapping = stateDistrictMapping;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public ProjectReport(Timestamp dateAdded, StateDistrictMapping stateDistrictMapping, String pdfPath, String docPath,
            String excelPath, String imgPath) {

        this.dateAdded = dateAdded;
        this.stateDistrictMapping = stateDistrictMapping;
        this.pdfPath = pdfPath;
        this.docPath = docPath;
        this.excelPath = excelPath;
        this.imgPath = imgPath;
    }

    public ProjectReport() {

    }

}
