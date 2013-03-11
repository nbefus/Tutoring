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
public class ParaprofessionalSession {
    private int paraprofessionalSessionID;      // primary key
    
    private Paraprofessional paraprofessionalID;        // foreign key
    private Client client;                              // foreign key
    private Course course;                              // foreign key
    private Term term;                                  // foreign key
    private Location location;                          // foreign key
    private Paraprofessional paraprofessionalCreator;   // foreign key
    
    private Timestamp timeAndDateEntered;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private boolean grammarCheck;
    private String notes;

    public ParaprofessionalSession(int paraprofessionalSessionID, Paraprofessional paraprofessionalID, Client client, Course course, Term term, Location location, Paraprofessional paraprofessionalCreator, Timestamp timeAndDateEntered, Timestamp sessionStart, Timestamp sessionEnd, boolean grammarCheck, String notes) {
        this.paraprofessionalSessionID = paraprofessionalSessionID;
        this.paraprofessionalID = paraprofessionalID;
        this.client = client;
        this.course = course;
        this.term = term;
        this.location = location;
        this.paraprofessionalCreator = paraprofessionalCreator;
        this.timeAndDateEntered = timeAndDateEntered;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.grammarCheck = grammarCheck;
        this.notes = notes;
    }

    /**
     * @return the paraprofessionalSessionID
     */
    public int getParaprofessionalSessionID() {
        return paraprofessionalSessionID;
    }

    /**
     * @param paraprofessionalSessionID the paraprofessionalSessionID to set
     */
    public void setParaprofessionalSessionID(int paraprofessionalSessionID) {
        this.paraprofessionalSessionID = paraprofessionalSessionID;
    }

    /**
     * @return the paraprofessionalID
     */
    public Paraprofessional getParaprofessionalID() {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessionalID to set
     */
    public void setParaprofessionalID(Paraprofessional paraprofessionalID) {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * @return the term
     */
    public Term getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(Term term) {
        this.term = term;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the paraprofessionalCreator
     */
    public Paraprofessional getParaprofessionalCreator() {
        return paraprofessionalCreator;
    }

    /**
     * @param paraprofessionalCreator the paraprofessionalCreator to set
     */
    public void setParaprofessionalCreator(Paraprofessional paraprofessionalCreator) {
        this.paraprofessionalCreator = paraprofessionalCreator;
    }

    /**
     * @return the timeAndDateEntered
     */
    public Timestamp getTimeAndDateEntered() {
        return timeAndDateEntered;
    }

    /**
     * @param timeAndDateEntered the timeAndDateEntered to set
     */
    public void setTimeAndDateEntered(Timestamp timeAndDateEntered) {
        this.timeAndDateEntered = timeAndDateEntered;
    }

    /**
     * @return the sessionStart
     */
    public Timestamp getSessionStart() {
        return sessionStart;
    }

    /**
     * @param sessionStart the sessionStart to set
     */
    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }

    /**
     * @return the sessionEnd
     */
    public Timestamp getSessionEnd() {
        return sessionEnd;
    }

    /**
     * @param sessionEnd the sessionEnd to set
     */
    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    /**
     * @return the grammarCheck
     */
    public boolean isGrammarCheck() {
        return grammarCheck;
    }

    /**
     * @param grammarCheck the grammarCheck to set
     */
    public void setGrammarCheck(boolean grammarCheck) {
        this.grammarCheck = grammarCheck;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}