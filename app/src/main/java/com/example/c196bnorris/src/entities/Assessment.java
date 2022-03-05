package com.example.c196bnorris.src.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="assessment_table")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String type;
    private String title;
    private String startDate;
    private String endDate;
    private int courseID;

    public Assessment(int assessmentID, String type, String title, String startDate, String endDate, int courseID) {
        this.assessmentID = assessmentID;
        this.type = type;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }

    public void setAssessmentID(int assessmentID) {this.assessmentID = assessmentID;}
    public void setType(String type) {this.type = type;}
    public void setTitle(String title) {this.title = title;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public void setCourseID(int courseID) {this.courseID = courseID;}
    public int getAssessmentID() {return assessmentID;}
    public String getType() {return type;}
    public String getTitle() {return title;}
    public String getStartDate() {return startDate;}
    public String getEndDate() {return endDate;}
    public int getCourseID() {return courseID;}
}
