package com.example.c196bnorris.src.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String title;
    private String startDate;
    private String endDate;

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public Term(int termID, String title, String startDate, String endDate) {
        this.termID = termID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setTermID(int termID) {this.termID = termID;}
    public void setTitle(String title) {this.title = title;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public int getTermID() {return termID;}
    public String getTitle() {return title;}
    public String getStartDate() {return startDate;}
    public String getEndDate() {return endDate;}
}
