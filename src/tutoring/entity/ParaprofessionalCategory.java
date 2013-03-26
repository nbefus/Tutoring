/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.io.Serializable;

/**
 *
 * @author dabeefinator
 */
public class ParaprofessionalCategory implements Serializable{
    
    private Paraprofessional paraprofessionalID;  // primary key
    private Category categoryID;                  // primary key

    public ParaprofessionalCategory(Paraprofessional paraprofessional, Category category) {
        this.paraprofessionalID = paraprofessional;
        this.categoryID = category;
    }
      public ParaprofessionalCategory()
    {
        
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
    
    public String toString()
    {
        return paraprofessionalID.toString() + " " + categoryID.toString();
    }

    
}
