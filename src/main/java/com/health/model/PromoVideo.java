package com.health.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;


@Entity
public class PromoVideo {
	
	@Id
	int promoId;
	
	@Column(name="title")
	String title;
	
	@Column(name = "date_added")
	private Timestamp dateAdded;
	
	private boolean showOnHomepage=false;
	
	@OneToMany(mappedBy = "promoVideo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("date_added")
	private Set<PathofPromoVideo> pathofPromoVideo=new HashSet<PathofPromoVideo>();
	

	public PromoVideo() {
		
	}


	public PromoVideo(int promoId, String title, Timestamp dateAdded) {
		super();
		this.promoId = promoId;
		this.title = title;
		this.dateAdded = dateAdded;
		
		
	}
	
	public PromoVideo( String title, Timestamp dateAdded) {
		super();
		this.title = title;
		this.dateAdded = dateAdded;
		
		
	}


	public PromoVideo(int promoId, String title, Timestamp dateAdded, Set<PathofPromoVideo> pathofPromoVideo) {
		super();
		this.promoId = promoId;
		this.title = title;
		this.dateAdded = dateAdded;
		this.pathofPromoVideo = pathofPromoVideo;
	}


	public Set<PathofPromoVideo> getPathofPromoVideo() {
		return pathofPromoVideo;
	}


	public void setPathofPromoVideo(Set<PathofPromoVideo> pathofPromoVideo) {
		this.pathofPromoVideo = pathofPromoVideo;
	}


	public int getPromoId() {
		return promoId;
	}


	public void setPromoId(int promoId) {
		this.promoId = promoId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	


	public boolean isShowOnHomepage() {
		return showOnHomepage;
	}


	public void setShowOnHomepage(boolean showOnHomepage) {
		this.showOnHomepage = showOnHomepage;
	}


	public Timestamp getDateAdded() {
		return dateAdded;
	}


	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	
	
	
	public List<Language> findAlllanguages() {
		
		List<Language> languageList =new ArrayList<>();
		
		for (PathofPromoVideo temp: pathofPromoVideo) {
			languageList.add(temp.getLan());
		}
		return languageList;
	}
	
	
	public String findAlllangNames() {
		if (pathofPromoVideo.size() == 0)
			return "";
		StringBuilder names = new StringBuilder();
		for (PathofPromoVideo temp: pathofPromoVideo) {
			names.append(", ").append(temp.getLan().getLangName());
		}
		return names.substring(2);
	}
	
	
	
	
	public String findVideoFileofEnglish() {
		if (pathofPromoVideo.size() == 0)
			return "";
		String videoFile="";
		for (PathofPromoVideo temp: pathofPromoVideo) {
			if(temp.getLan().getLanId()==22) {
				videoFile=temp.getVideoPath();
				break;
				
			}
		}
		
		if(videoFile==null) {
			return "";
		}
		return videoFile;
	}
	
	public String GetVideoFileofFirstLan() {
		if (pathofPromoVideo.size() == 0)
			return "";
		PathofPromoVideo first = pathofPromoVideo.iterator().next();
		String videoPath = first.getVideoPath();
		if(videoPath==null)
			return "";
		return videoPath;
	}
	
	
	public HashMap<Integer, String> getVideoFiles() {
		HashMap<Integer, String> videoFiles = new HashMap<>();
		for (PathofPromoVideo video : pathofPromoVideo) {
			videoFiles.put(video.getLan().getLanId(), video.getVideoPath());
		}
		return videoFiles;
		
	}
	
	


	@Override
	public String toString() {
		return "PromoVideo [promoId=" + promoId + ", title=" + title + ", dateAdded=" + dateAdded + "]";
	}
	
	
	
	

	
}
