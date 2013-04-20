/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author dabeefinator
 */
public class Category {
    
    private int categoryID;
    private String name;

    public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }
    
     public Category()
    {
        
    }

    /**
     * @return the categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString()
    {
        return categoryID + " " + name;
    }
}
