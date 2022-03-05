package com.example.c196bnorris.src.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;
    private String courseNote;
    private int termID;

    public Course(int courseID,
                  String title,
                  String startDate,
                  String endDate,
                  String status,
                  String instructorName,
                  String instructorPhoneNumber,
                  String instructorEmail,
                  String courseNote,
                  int termID) {
        this.courseID = courseID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
        this.courseNote = courseNote;
        this.termID = termID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", instructorPhoneNumber='" + instructorPhoneNumber + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", courseNote='" + courseNote + '\'' +
                ", termID=" + termID +
                '}';
    }

    public void setCourseID(int courseID) {this.courseID = courseID;}
    public void setTitle(String title) {this.title = title;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public void setStatus(String status) {this.status = status;}
    public void setInstructorName(String instructorName) {this.instructorName = instructorName;}
    public void setInstructorPhoneNumber(String instructorPhoneNumber) {this.instructorPhoneNumber = instructorPhoneNumber;}
    public void setInstructorEmail(String instructorEmail) {this.instructorEmail = instructorEmail;}
    public void setCourseNote(String courseNote) {this.courseNote = courseNote;}
    public void setTermID(int termID) {this.termID = termID;}
    public int getCourseID() {return courseID;}
    public String getTitle() {return title;}
    public String getStartDate() {return startDate;}
    public String getEndDate() {return endDate;}
    public String getStatus() {return status;}
    public String getInstructorName() {return instructorName;}
    public String getInstructorPhoneNumber() {return instructorPhoneNumber;}
    public String getInstructorEmail() {return instructorEmail;}
    public String getCourseNote() {return courseNote;}
    public int getTermID() {return termID;}
}
