package com.health.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.health.domain.security.Authority;
import com.health.domain.security.UserRole;

@Entity
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String username;

    private Date dob;

    private String password;

    private String firstName;

    private String gender;

    private String lastName;

    @Column(length = 1000)
    private String address;

    @Column(name = "token")
    private String token;

    @Column(name = "age")
    private int age;

    @Column(name = "master_trainer_organization")
    private String organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_role_id")
    private OrganizationRole orgRolev;

    public OrganizationRole getOrgRolev() {
        return orgRolev;
    }

    public void setOrgRolev(OrganizationRole orgRolev) {
        this.orgRolev = orgRolev;
    }

    @Column(name = "master_trainer_experience")
    private int experience;

    @Column(name = "master_trainer_aadhar")
    private long aadharNumber;

    @Column(name = "profilePic")
    private String profilePic;

    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    private long phone;

    private boolean registered = true;

    @Column(name = "email_verification_code", nullable = false, updatable = false)
    private String emailVerificationCode;

    @Column(name = "dateAdded", updatable = false)
    private Timestamp dateAdded;

    @Column(name = "loggedintime")
    private Timestamp loggedInTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @OrderBy("role, cat, lan")
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Topic> topics = new HashSet<Topic>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Language> languages = new HashSet<Language>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Question> questions = new HashSet<Question>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Event> events = new HashSet<Event>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Testimonial> testi = new HashSet<Testimonial>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Consultant> consults = new HashSet<Consultant>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TrainingInformation> trainingInfo = new HashSet<TrainingInformation>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LogManegement> logs = new HashSet<LogManegement>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserIndianLanguageMapping> userKnownLans = new HashSet<UserIndianLanguageMapping>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PostQuestionaire> questionsPost = new HashSet<PostQuestionaire>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ContributorAssignedMultiUserTutorial> multiUserOnTutorial = new HashSet<ContributorAssignedMultiUserTutorial>();

    @OneToMany(mappedBy = "scriptUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tutorial> tutorialScriptUser = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "slideUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tutorial> tutorialSlideUser = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "keywordUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tutorial> tutorialKeywordUser = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "outlineUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tutorial> tutorialOutlineUser = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "videoUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tutorial> tutorialVideoUser = new HashSet<Tutorial>();

    @OneToMany(mappedBy = "preRequiticUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tutorial> tutorialPreReqUser = new HashSet<Tutorial>();

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorites = new HashSet<>();

        for (UserRole x : userRoles) {
            if (x.getStatus()) {
                authorites.add(new Authority(x.getRole().getName()));
            }
        }

        return authorites;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {
        return registered;
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public long getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(long aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setLoggedInTime(Timestamp loggedInTime) {
        this.loggedInTime = loggedInTime;
    }

    public Timestamp getLoggedInTime() {
        return loggedInTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Testimonial> getTesti() {
        return testi;
    }

    public void setTesti(Set<Testimonial> testi) {
        this.testi = testi;
    }

    public Set<Consultant> getConsults() {
        return consults;
    }

    public void setConsults(Set<Consultant> consults) {
        this.consults = consults;
    }

    public Set<TrainingInformation> getTrainingInfo() {
        return trainingInfo;
    }

    public void setTrainingInfo(Set<TrainingInformation> trainingInfo) {
        this.trainingInfo = trainingInfo;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<UserIndianLanguageMapping> getUserKnownLans() {
        return userKnownLans;
    }

    public void setUserKnownLans(Set<UserIndianLanguageMapping> userKnownLans) {
        this.userKnownLans = userKnownLans;
    }

    public Set<PostQuestionaire> getQuestionsPost() {
        return questionsPost;
    }

    public void setQuestionsPost(Set<PostQuestionaire> questionsPost) {
        this.questionsPost = questionsPost;
    }

    public Set<ContributorAssignedMultiUserTutorial> getMultiUserOnTutorial() {
        return multiUserOnTutorial;
    }

    public void setMultiUserOnTutorial(Set<ContributorAssignedMultiUserTutorial> multiUserOnTutorial) {
        this.multiUserOnTutorial = multiUserOnTutorial;
    }

    public Set<Tutorial> getTutorialScriptUser() {
        return tutorialScriptUser;
    }

    public void setTutorialScriptUser(Set<Tutorial> tutorialScriptUser) {
        this.tutorialScriptUser = tutorialScriptUser;
    }

    public Set<Tutorial> getTutorialSlideUser() {
        return tutorialSlideUser;
    }

    public void setTutorialSlideUser(Set<Tutorial> tutorialSlideUser) {
        this.tutorialSlideUser = tutorialSlideUser;
    }

    public Set<Tutorial> getTutorialKeywordUser() {
        return tutorialKeywordUser;
    }

    public void setTutorialKeywordUser(Set<Tutorial> tutorialKeywordUser) {
        this.tutorialKeywordUser = tutorialKeywordUser;
    }

    public Set<Tutorial> getTutorialOutlineUser() {
        return tutorialOutlineUser;
    }

    public void setTutorialOutlineUser(Set<Tutorial> tutorialOutlineUser) {
        this.tutorialOutlineUser = tutorialOutlineUser;
    }

    public Set<Tutorial> getTutorialVideoUser() {
        return tutorialVideoUser;
    }

    public void setTutorialVideoUser(Set<Tutorial> tutorialVideoUser) {
        this.tutorialVideoUser = tutorialVideoUser;
    }

    public Set<Tutorial> getTutorialPreReqUser() {
        return tutorialPreReqUser;
    }

    public void setTutorialPreReqUser(Set<Tutorial> tutorialPreReqUser) {
        this.tutorialPreReqUser = tutorialPreReqUser;
    }

    public String getEmailVerificationCode() {
        return emailVerificationCode;
    }

    public void setEmailVerificationCode(String emailVerificationCode) {
        this.emailVerificationCode = emailVerificationCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User [id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", dob=").append(dob);
        sb.append(", password=").append(password);
        sb.append(", firstName=").append(firstName);
        sb.append(", gender=").append(gender);
        sb.append(", lastName=").append(lastName);
        sb.append(", address=").append(address);
        sb.append(", token=").append(token);
        sb.append(", age=").append(age);
        sb.append(", organization=").append(organization);

        sb.append(", aadharNumber=").append(aadharNumber);

        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", registered=").append(registered);
        sb.append(", emailVerificationCode=").append(emailVerificationCode);
        sb.append(", dateAdded=").append(dateAdded);

        sb.append("]");
        return sb.toString();
    }

}
