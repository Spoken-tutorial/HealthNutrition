package com.health.model;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
public class FilesofBrouchure {
	
	@Id
	int broFileId;
	
	@Column(name = "date_added")
	private Timestamp dateAdded;
	
	@Column(name = "Web_path")
	private String webPath;
	
	@Column(name = "Print_path")
	private String printPath;
	
	@Column(name = "thumbnail_path")
	private String thumbnailPath;
	
	@ManyToOne
	@JoinColumn(name = "ver_id")
	private Version version;
	
	@ManyToOne
	@JoinColumn(name = "lan_id")
	private Language lan;

	
	public FilesofBrouchure() {
	super();
	
}
	
	public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, String printPath, Version version,
			Language lan) {
		super();
		this.broFileId = broFileId;
		this.dateAdded = dateAdded;
		this.webPath = webPath;
		this.printPath = printPath;
		this.version = version;
		this.lan = lan;
	}
	 
	public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, String printPath) {
		super();
		this.broFileId = broFileId;
		this.dateAdded = dateAdded;
		this.webPath = webPath;
		this.printPath = printPath;
		
	}
	 
	public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, String printPath, Language lan) {
		super();
		this.broFileId = broFileId;
		this.dateAdded = dateAdded;
		this.webPath = webPath;
		this.printPath = printPath;
		this.lan = lan;
	} 
	
	public FilesofBrouchure(int broFileId, Timestamp dateAdded, String webPath, String printPath, Version version) {
		super();
		this.broFileId = broFileId;
		this.dateAdded = dateAdded;
		this.webPath = webPath;
		this.printPath = printPath;
		this.version = version;
		
	}
	

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public int getBroFileId() {
		return broFileId;
	}

	public void setBroFileId(int broFileId) {
		this.broFileId = broFileId;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public String getPrintPath() {
		return printPath;
	}

	public void setPrintPath(String printPath) {
		this.printPath = printPath;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Language getLan() {
		return lan;
	}

	public void setLan(Language lan) {
		this.lan = lan;
	}

	@Override
	public String toString() {
		return "FilesofBrouchure [broFileId=" + broFileId + ", version=" + version + ", lan=" + lan + "]";
	}
	
	
	
	
	
}
