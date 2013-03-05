package tutoring.entity;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class Subject
{
    private int subjectID;
    private String abbrevName;
    private String fullName;
    private SubjectCategory category;

    public Subject(int subjectID, String abbrevName, String fullName, SubjectCategory category) {
        this.subjectID = subjectID;
        this.abbrevName = abbrevName;
        this.fullName = fullName;
        this.category = category;
    }

    /**
     * @return the subjectID
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the abbrevName
     */
    public String getAbbrevName() {
        return abbrevName;
    }

    /**
     * @param abbrevName the abbrevName to set
     */
    public void setAbbrevName(String abbrevName) {
        this.abbrevName = abbrevName;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the category
     */
    public SubjectCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(SubjectCategory category) {
        this.category = category;
    }

    
    
}