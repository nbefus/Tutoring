/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import tutoring.entity.Client;
import tutoring.entity.Course;

/**
 *
 * @author Nathaniel
 */
public class Validate 
{
    public Validate()
    {
        
    }
    
    public static boolean validateClient(Client c)
    {
        if(HibernateTest.select("from Client as c where c.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'").size() > 0)
            return true;
        return false;
    }
    
    public static boolean validateCourse(Course c)
    {
        if(HibernateTest.regularSelect("from Course as c join Teacher as t on c.teacherID=t.teacherID join Subject as s on c.subjectID=s.subjectID where c.level='"+c.getLevel()+"' and t.lName='"+c.getTeacherID().getlName()+"' and t.fname="+c.getTeacherID().getlName()+" s.abbrevName='"+c.getSubjectID().getAbbrevName()+"'").size() > 0)
            return true;
        return false;
    }
    
    /*public boolean validateSession(ParaprofessionalSession ps)
    {
        if(HibernateTest.select("from Client as c where c.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'").size() > 0)
            return true;
        return false;
    }
    
    public boolean validateParaprofessional(Paraprofessiona p)
    {
        if(HibernateTest.select("from Paraprofessional as p where p.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'").size() > 0)
            return true;
        return false;
    }*/
}
