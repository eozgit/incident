package com.example;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class Incident {
    private int id;
    private String reportedTo;
    private String location;
    private Timestamp incidentDate;
    private String reportedBy;
    private String nature;
    private String detail;
    private String reviewer;
    private Date completeDate;
    private boolean experiencingConcernsListenedTo;
    private boolean experiencingSatisfied;
    private boolean displayingConcernsListenedTo;
    private boolean displayingSatisfied;
    private String procedures;
    private String conclusion;

    private List<Person> pupilsExperiencing = new LinkedList<Person>();
    private List<Person> staffExperiencing = new LinkedList<Person>();
    private List<Person> pupilsDisplaying = new LinkedList<Person>();
    private List<Person> staffDisplaying = new LinkedList<Person>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportedTo() {
        return reportedTo;
    }

    public void setReportedTo(String reportedTo) {
        this.reportedTo = reportedTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Timestamp incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public boolean isExperiencingConcernsListenedTo() {
        return experiencingConcernsListenedTo;
    }

    public void setExperiencingConcernsListenedTo(boolean experiencingConcernsListenedTo) {
        this.experiencingConcernsListenedTo = experiencingConcernsListenedTo;
    }

    public boolean isExperiencingSatisfied() {
        return experiencingSatisfied;
    }

    public void setExperiencingSatisfied(boolean experiencingSatisfied) {
        this.experiencingSatisfied = experiencingSatisfied;
    }

    public boolean isDisplayingConcernsListenedTo() {
        return displayingConcernsListenedTo;
    }

    public void setDisplayingConcernsListenedTo(boolean displayingConcernsListenedTo) {
        this.displayingConcernsListenedTo = displayingConcernsListenedTo;
    }

    public boolean isDisplayingSatisfied() {
        return displayingSatisfied;
    }

    public void setDisplayingSatisfied(boolean displayingSatisfied) {
        this.displayingSatisfied = displayingSatisfied;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public List<Person> getStaffDisplaying() {
        return staffDisplaying;
    }

    public void setStaffDisplaying(List<Person> staffDisplaying) {
        this.staffDisplaying = staffDisplaying;
    }

    public List<Person> getPupilsDisplaying() {
        return pupilsDisplaying;
    }

    public void setPupilsDisplaying(List<Person> pupilsDisplaying) {
        this.pupilsDisplaying = pupilsDisplaying;
    }

    public List<Person> getStaffExperiencing() {
        return staffExperiencing;
    }

    public void setStaffExperiencing(List<Person> staffExperiencing) {
        this.staffExperiencing = staffExperiencing;
    }

    public List<Person> getPupilsExperiencing() {
        return pupilsExperiencing;
    }

    public void setPupilsExperiencing(List<Person> pupilsExperiencing) {
        this.pupilsExperiencing = pupilsExperiencing;
    }
}
