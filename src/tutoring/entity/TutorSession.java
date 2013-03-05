package tutoring.entity;


import java.sql.Timestamp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class TutorSession {
    private int tutorSessionID;
    private String fName, lName;
    private int tutorID;
    private int subjectID;
    private int level;
    private Timestamp timeAndDateEntered;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private boolean future;
    private boolean grammerCheck;
    private String notes;

    public TutorSession(int tutorSessionID, String fName, String lName, int tutorID, int subjectID, int level, Timestamp timeAndDateEntered, Timestamp sessionStart, Timestamp sessionEnd, boolean future, boolean grammerCheck, String notes) {
        this.tutorSessionID = tutorSessionID;
        this.fName = fName;
        this.lName = lName;
        this.tutorID = tutorID;
        this.subjectID = subjectID;
        this.level = level;
        this.timeAndDateEntered = timeAndDateEntered;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.future = future;
        this.grammerCheck = grammerCheck;
        this.notes = notes;
    }

    public int getTutorSessionID() {
        return tutorSessionID;
    }

    public void setTutorSessionID(int tutorSessionID) {
        this.tutorSessionID = tutorSessionID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getTutorID() {
        return tutorID;
    }

    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Timestamp getTimeAndDateEntered() {
        return timeAndDateEntered;
    }

    public void setTimeAndDateEntered(Timestamp timeAndDateEntered) {
        this.timeAndDateEntered = timeAndDateEntered;
    }

    public Timestamp getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Timestamp getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public boolean isFuture() {
        return future;
    }

    public void setFuture(boolean future) {
        this.future = future;
    }

    public boolean isGrammerCheck() {
        return grammerCheck;
    }

    public void setGrammerCheck(boolean grammerCheck) {
        this.grammerCheck = grammerCheck;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
}