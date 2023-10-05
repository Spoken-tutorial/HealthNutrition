package com.health.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Brochure Object to store brochure related data on database
 * 
 * @author Om Prakash Soni
 * @version 1.0
 *
 */
@Entity
public class Brouchure implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * unique brochure id
     */

    @Id
    private int id;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Location to store brochure
     */
    private String posterPath;

    /**
     * Boolean value to show on Homepage or not
     */
    private boolean showOnHomepage = false;

    private int primaryVersion;

    public int getPrimaryVersion() {
        return primaryVersion;
    }

    public void setPrimaryVersion(int primaryVersion) {
        this.primaryVersion = primaryVersion;
    }

    @OneToMany(mappedBy = "brouchure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Version> versions = new HashSet<Version>();

    public Set<Version> getVersions() {
        return versions;
    }

    public void setVersions(Set<Version> versions) {
        this.versions = versions;
    }

    /**
     * Language mapped object it is associated with
     */
    @ManyToOne
    @JoinColumn(name = "lan_id")
    private Language lan;

    /**
     * Topic category Mapped object to which it belongs
     */
    @ManyToOne
    @JoinColumn(name = "topicCat_id")
    private TopicCategoryMapping topicCatId;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category catId;

    public Category getCatId() {
        return catId;
    }

    public void setCatId(Category catId) {
        this.catId = catId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public TopicCategoryMapping getTopicCatId() {
        return topicCatId;
    }

    public void setTopicCatId(TopicCategoryMapping topicCatId) {
        this.topicCatId = topicCatId;
    }

    public boolean isShowOnHomepage() {
        return showOnHomepage;
    }

    public void setShowOnHomepage(boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Brouchure [id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", posterPath=").append(posterPath);
        sb.append(", showOnHomepage=").append(showOnHomepage);
        sb.append(", primaryVersion=").append(primaryVersion);

        sb.append("]");
        return sb.toString();
    }

}
