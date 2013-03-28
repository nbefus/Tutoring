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
    private int subjectID;  // primary key
    private String abbrevName;
    private Category categoryID;  // foreign key

    public Subject()
    {
        
    }
      
    public Subject(int subjectID, String abbrevName, Category category) {
        this.subjectID = subjectID;
        this.abbrevName = abbrevName;
        this.categoryID = category;
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
     * @return the categoryID
     */

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
     * @return the categoryID
     */
    public Category getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(Category categoryID) {
        this.categoryID = categoryID;
    }
    
    public boolean equals(Subject s)
    {
        if(this.abbrevName.equals(s.getAbbrevName()))
            return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        return subjectID + " " + abbrevName + " " + categoryID.getName();
    }

}