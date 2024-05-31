package com.health.model;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Version {

    @Id
    int verId;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "Image_path")
    private String versionPosterPath;

    @ManyToOne
    @JoinColumn(name = "bro_id")
    private Brouchure brouchure;

    @Column(name = "Brouchure_Version")
    int broVersion;

    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("date_added")
    private Set<FilesofBrouchure> filesofBrouchure = new HashSet<FilesofBrouchure>();

    public Set<FilesofBrouchure> getFilesofBrouchure() {
        return filesofBrouchure;
    }

    public void setFilesofBrouchure(Set<FilesofBrouchure> filesofBrouchure) {
        this.filesofBrouchure = filesofBrouchure;
    }

    public String findAlllangNames() {
        if (filesofBrouchure.size() == 0)
            return "";
        StringBuilder names = new StringBuilder();
        for (FilesofBrouchure files : filesofBrouchure) {
            names.append(", ").append(files.getLan().getLangName());
        }
        return names.substring(2);
    }

    public String findWebFileofEnglish() {
        if (filesofBrouchure.size() == 0)
            return "";
        String webFile = "";
        for (FilesofBrouchure file : filesofBrouchure) {
            if (file.getLan().getLanId() == 22) {
                webFile = file.getWebPath();
                break;

            }
        }

        if (webFile == null) {
            return "";
        }
        return webFile;
    }

    public String findWebThumbnailofEnglish() {
        if (filesofBrouchure.size() == 0)
            return "";
        String webFile = "";
        for (FilesofBrouchure file : filesofBrouchure) {
            if (file.getLan().getLanId() == 22) {
                webFile = file.getThumbnailPath();
                break;

            }
        }

        if (webFile == null) {
            return "";
        }
        return webFile;
    }

    public String GetWebFileofFirstLan() {
        if (filesofBrouchure.size() == 0)
            return "";
        FilesofBrouchure first = filesofBrouchure.iterator().next();
        String webPath = first.getWebPath();
        if (webPath == null)
            return "";
        return webPath;
    }

    public String GetWebThumbnailofFirstLan() {
        if (filesofBrouchure.size() == 0)
            return "";
        FilesofBrouchure first = filesofBrouchure.iterator().next();
        String webPath = first.getThumbnailPath();
        if (webPath == null)
            return "";
        return webPath;
    }

    public Version(int verId, Timestamp dateAdded, String versionPosterPath, Brouchure brouchure, int broVersion) {
        super();
        this.verId = verId;
        this.dateAdded = dateAdded;
        this.versionPosterPath = versionPosterPath;
        this.brouchure = brouchure;
        this.broVersion = broVersion;
    }

    public int getBroVersion() {
        return broVersion;
    }

    public void setBroVersion(int broVersion) {
        this.broVersion = broVersion;
    }

    public Version() {
        super();

    }

    public Version(int verId, Timestamp dateAdded, String versionPosterPath, Brouchure brouchure) {
        super();
        this.verId = verId;
        this.dateAdded = dateAdded;
        this.versionPosterPath = versionPosterPath;
        this.brouchure = brouchure;
    }

    public static Comparator<Version> SortByBroVersionInDesc = new Comparator<Version>() {

        @Override
        public int compare(Version v1, Version v2) {

            if (v1.getBroVersion() < v2.getBroVersion()) {
                return 1;
            } else {
                return -1;
            }

        }
    };

    public static Comparator<Version> SortByBroVersionTime = new Comparator<Version>() {

        @Override
        public int compare(Version v1, Version v2) {
            return v2.getDateAdded().compareTo(v1.getDateAdded());

        }
    };

    public Brouchure getBrouchure() {
        return brouchure;
    }

    public void setBrouchure(Brouchure brouchure) {
        this.brouchure = brouchure;
    }

    public int getVerId() {
        return verId;
    }

    public void setVerId(int verId) {
        this.verId = verId;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getVersionPosterPath() {
        return versionPosterPath;
    }

    public void setVersionPosterPath(String versionPosterPath) {
        this.versionPosterPath = versionPosterPath;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Version [verId=").append(verId);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", versionPosterPath=").append(versionPosterPath);
        sb.append("]");
        return sb.toString();
    }

}
