package com.health.model;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.health.utility.CommonData;

/**
 * Tutorial object
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
@Entity
@Table(name = "tutorial_resource")
public class Tutorial implements Comparable<Tutorial>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * unique id of object
     */
    @Id
    @Column(name = "tutorial_id", updatable = false, nullable = false)
    private int tutorialId;

    /**
     * number of user visit
     */
    @Column(name = "userVisit")
    private int UserVisit = 0;

    @Column(name = "resourceVisit")
    @ColumnDefault("0")
    private int resourceVisit = 0;

    /**
     * relative path of script
     */
    @Column(name = "script", length = 2000)
    private String script;

    /**
     * scrpt status
     */
    @Column(name = "script_status")
    private int scriptStatus = 0;

//	@Column(name = "script_user")
//	private User scriptUser = null;

    /**
     * user object added script
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_script")
    private User scriptUser;

    /**
     * relative path of slide
     */
    @Column(name = "slide", length = 2000)
    private String slide;

    /**
     * slide status
     */
    @Column(name = "slide_status")
    private int slideStatus = 0;

//	@Column(name = "slide_user")
//	private User slideUser = null;

    /**
     * user object who added slide
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_slide")
    private User slideUser;

    /**
     * keyword
     */
    @Column(name = "keyword", length = 2000)
    private String keyword;

    /**
     * keyword status
     */
    @Column(name = "keyword_status")
    private int keywordStatus = 0;

//	@Column(name = "keyword_user")
//	private User keywordUser = null;

    /**
     * user object who added slide
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_keyword")
    private User keywordUser;

    /**
     * outline
     */
    @Column(name = "outline", length = 3500)
    private String outline;

    /**
     * outline status
     */
    @Column(name = "outline_status")
    private int outlineStatus = 0;

//	@Column(name = "outline_user")
//	private User outlineUser = null;

    /**
     * user object who added slide
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_outline")
    private User outlineUser;

    /**
     * tutorial object for pre-rrquisite
     */
    @ManyToOne
    @JoinColumn(name = "preRequistics")
    private Tutorial preRequistic;

    /**
     * pre-requisite status
     */
    @Column(name = "preRequistic_status")
    private int preRequisticStatus = 0;

    /**
     * user object who added slide
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preRequistic")
    private User preRequiticUser;

    public User getPreRequiticUser() {
        return preRequiticUser;
    }

    public void setPreRequiticUser(User preRequiticUser) {
        this.preRequiticUser = preRequiticUser;
    }

    /**
     * tutorial object when other language except English used
     */
    @ManyToOne
    @JoinColumn(name = "relatedVideo")
    private Tutorial relatedVideo;

    /**
     * timestamp when object created
     */
    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    /**
     * boolean value to show /disable
     */
    @Column(name = "enabled")
    private boolean status;

    @Column(name = "added_queue", nullable = false)
    private boolean addedQueue = false;

    /**
     * relative path of timescript
     */
    @Column(name = "timescript", length = 1000)
    private String timeScript;

    /**
     * name of topic name
     */
    @Column(name = "topicName", length = 1000)
    private String topicName;

    /**
     * relative path of video
     */
    @Column(name = "video", length = 1000)
    private String video;

    @Column(name = "outlinePath", length = 1000)
    private String outlinePath;

    /**
     * video status
     */
    @Column(name = "video_status")
    private int videoStatus = 0;

//	@Column(name = "video_user")
//	private User videoUser = null;

    /**
     * user object who added slide
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_video")
    private User videoUser;

    /**
     * tutorial topic category information
     */
    @ManyToOne
    @JoinColumn(name = "conAssignedTutorial")
    private ContributorAssignedTutorial conAssignedTutorial;

    @OneToMany(mappedBy = "tutorialInfos", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(mappedBy = "preRequistic", cascade = CascadeType.ALL)
    private Set<Tutorial> preRequisticTutorial = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "relatedVideo", cascade = CascadeType.ALL)
    private Set<Tutorial> relatedTutorial = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "tutorialInfos", cascade = CascadeType.ALL)
    private Set<LogManegement> logs = new HashSet<LogManegement>();

    public int getResourceVisit() {
        return resourceVisit;
    }

    public boolean isAddedQueue() {
        return addedQueue;
    }

    public void setAddedQueue(boolean addedQueue) {
        this.addedQueue = addedQueue;
    }

    public void setResourceVisit(int resourceVisit) {
        this.resourceVisit = resourceVisit;
    }

    public int getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(int tutorialId) {
        this.tutorialId = tutorialId;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getOutlinePath() {
        return outlinePath;
    }

    public void setOutlinePath(String outlinePath) {
        this.outlinePath = outlinePath;
    }

    public String loadOutline(String mediaRoot) throws IOException {
        Path path = Paths.get(mediaRoot + CommonData.uploadDirectoryOutline + getTutorialId() + ".txt");

        List<String> lines = Files.readAllLines(path);
        String outline = "";
        boolean firstLine = true;
        for (String line : lines) {
            if (firstLine)
                firstLine = false;
            else
                outline += "\n";
            outline += line;
        }
        return outline;

    }

    public void saveOutline(String mediaRoot, String outline) throws IOException {
        Path path = Paths.get(mediaRoot, CommonData.uploadDirectoryOutline);

        Files.createDirectories(path);

        Path filePath = Paths.get(mediaRoot, CommonData.uploadDirectoryOutline, getTutorialId() + ".txt");
        if (outline != null) {
            Files.writeString(filePath, outline);
        } else {
            Files.writeString(filePath, "");
        }
        String temp = filePath.toString();

        int indexToStart = temp.indexOf("Media");

        String document = temp.substring(indexToStart, temp.length());
        setOutlinePath(document);

    }

    public int getScriptStatus() {
        return scriptStatus;
    }

    public void setScriptStatus(int scriptStatus) {
        this.scriptStatus = scriptStatus;
    }

    public String getSlide() {
        return slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public int getSlideStatus() {
        return slideStatus;
    }

    public void setSlideStatus(int slideStatus) {
        this.slideStatus = slideStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getKeywordStatus() {
        return keywordStatus;
    }

    public void setKeywordStatus(int keywordStatus) {
        this.keywordStatus = keywordStatus;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public int getOutlineStatus() {
        return outlineStatus;
    }

    public void setOutlineStatus(int outlineStatus) {
        this.outlineStatus = outlineStatus;
    }

    public Tutorial getPreRequistic() {
        return preRequistic;
    }

    public void setPreRequistic(Tutorial preRequistic) {
        this.preRequistic = preRequistic;
    }

    public int getPreRequisticStatus() {
        return preRequisticStatus;
    }

    public void setPreRequisticStatus(int preRequisticStatus) {
        this.preRequisticStatus = preRequisticStatus;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTimeScript() {
        return timeScript;
    }

    public void setTimeScript(String timeScript) {
        this.timeScript = timeScript;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(int videoStatus) {
        this.videoStatus = videoStatus;
    }

    public ContributorAssignedTutorial getConAssignedTutorial() {
        return conAssignedTutorial;
    }

    public void setConAssignedTutorial(ContributorAssignedTutorial conAssignedTutorial) {
        this.conAssignedTutorial = conAssignedTutorial;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<LogManegement> getLogs() {
        return logs;
    }

    public void setLogs(Set<LogManegement> logs) {
        this.logs = logs;
    }

    public Tutorial getRelatedVideo() {
        return relatedVideo;
    }

    public void setRelatedVideo(Tutorial relatedVideo) {
        this.relatedVideo = relatedVideo;
    }

    public Set<Tutorial> getPreRequisticTutorial() {
        return preRequisticTutorial;
    }

    public void setPreRequisticTutorial(Set<Tutorial> preRequisticTutorial) {
        this.preRequisticTutorial = preRequisticTutorial;
    }

    public Set<Tutorial> getRelatedTutorial() {
        return relatedTutorial;
    }

    public void setRelatedTutorial(Set<Tutorial> relatedTutorial) {
        this.relatedTutorial = relatedTutorial;
    }

    public User getScriptUser() {
        return scriptUser;
    }

    public void setScriptUser(User scriptUser) {
        this.scriptUser = scriptUser;
    }

    public User getSlideUser() {
        return slideUser;
    }

    public void setSlideUser(User slideUser) {
        this.slideUser = slideUser;
    }

    public User getKeywordUser() {
        return keywordUser;
    }

    public void setKeywordUser(User keywordUser) {
        this.keywordUser = keywordUser;
    }

    public User getOutlineUser() {
        return outlineUser;
    }

    public void setOutlineUser(User outlineUser) {
        this.outlineUser = outlineUser;
    }

    public User getVideoUser() {
        return videoUser;
    }

    public void setVideoUser(User videoUser) {
        this.videoUser = videoUser;
    }

    public Integer getOrdering() {
        return this.getConAssignedTutorial().getTopicCatId().getOrder();
    }

    @Override
    public int compareTo(Tutorial o) {
        // TODO Auto-generated method stub
        return this.getOrdering().compareTo(o.getOrdering());
    }

    /*
     * A function to Sort Tutorial BY UserVisit in descending order Author: Alok
     * Kumar
     */
    public static Comparator<Tutorial> UserVisitComp = new Comparator<Tutorial>() {

        // Method
        @Override
        public int compare(Tutorial t1, Tutorial t2) {

            if (t1.getUserVisit() == t2.getUserVisit()) {
                return t1.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()
                        .compareTo(t2.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
            }

            else if (t1.getUserVisit() < t2.getUserVisit()) {
                return 1;
            } else {
                return -1;
            }

        }
    };

    /*
     * A function to Sort Tutorial BY OrderValue in Ascending order Author: Alok
     * Kumar
     */
    public static Comparator<Tutorial> SortByOrderValue = new Comparator<Tutorial>() {

        // Method
        @Override
        public int compare(Tutorial t1, Tutorial t2) {

            if (t1.getConAssignedTutorial().getTopicCatId().getOrder() == t2.getConAssignedTutorial().getTopicCatId()
                    .getOrder()) {
                return t1.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()
                        .compareTo(t2.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
            }

            else if (t1.getConAssignedTutorial().getTopicCatId().getOrder() > t2.getConAssignedTutorial()
                    .getTopicCatId().getOrder()) {
                return 1;
            } else {
                return -1;
            }

        }
    };

    public int getUserVisit() {
        return UserVisit;
    }

    public void setUserVisit(int userVisit) {
        UserVisit = userVisit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tutorial [tutorialId=").append(tutorialId);
        sb.append(", UserVisit=").append(UserVisit);
        sb.append(", script=").append(script);
        sb.append(", scriptStatus=").append(scriptStatus);
        sb.append(", slide=").append(slide);
        sb.append(", slideStatus=").append(slideStatus);
        sb.append(", keyword=").append(keyword);
        sb.append(", keywordStatus=").append(keywordStatus);
        sb.append(", outline=").append(outline);
        sb.append(", outlineStatus=").append(outlineStatus);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", status=").append(status);
        sb.append(", timeScript=").append(timeScript);
        sb.append(", video=").append(video);
        sb.append(", videoStatus=").append(videoStatus);
        sb.append("]");
        return sb.toString();
    }

}
