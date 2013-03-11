/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author dabeefinator
 */
public class ParaprofessionalCategory {
    
    private Paraprofessional paraprofessional;  // primary key
    private Category category;                  // primary key

    public ParaprofessionalCategory(Paraprofessional paraprofessional, Category category) {
        this.paraprofessional = paraprofessional;
        this.category = category;
    }

    /**
     * @return the paraprofessional
     */
    public Paraprofessional getParaprofessional() {
        return paraprofessional;
    }

    /**
     * @param paraprofessional the paraprofessional to set
     */
    public void setParaprofessional(Paraprofessional paraprofessional) {
        this.paraprofessional = paraprofessional;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
}
