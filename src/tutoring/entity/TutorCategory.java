/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

/**
 *
 * @author dabeefinator
 */
public class TutorCategory {
    
    private Tutor tutor;
    private SubjectCategory category;

    public TutorCategory(Tutor tutor, SubjectCategory category) {
        this.tutor = tutor;
        this.category = category;
    }

    /**
     * @return the tutor
     */
    public Tutor getTutor() {
        return tutor;
    }

    /**
     * @param tutor the tutor to set
     */
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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
