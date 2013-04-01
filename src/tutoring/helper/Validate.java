/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        String query = "from Client as c where c.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'";
        System.out.println(query);
        if(HibernateTest.select(query).size() > 0)
            return true;
        return false;
    }
    
    public static boolean validateCourse(Course c)
    {
        if(HibernateTest.regularSelect("from Course as c join Teacher as t on c.teacherID=t.teacherID join Subject as s on c.subjectID=s.subjectID where c.level='"+c.getLevel()+"' and t.lName='"+c.getTeacherID().getlName()+"' and t.fname="+c.getTeacherID().getlName()+" s.abbrevName='"+c.getSubjectID().getAbbrevName()+"'").size() > 0)
            return true;
        return false;
    }
    
    public static boolean validateTimestamp(String t)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
         
            String[] split;
            sdf.setLenient(false);
            Date d = sdf.parse(t.trim());
            
            split = t.trim().split("[/ :]");
            
            if(split[2].length() != 4)
                throw new Exception();
            
            int month = Integer.parseInt(split[0]);
            int day = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);
            
            int hour = Integer.parseInt(split[3]);
            int min = Integer.parseInt(split[4]);
            
            String ampm = split[5];
            
            if(month <= 0 || month > 12)
                throw new Exception();
            if(day < 1 || day > 31)
                throw new Exception();
            if(year < 1000 || year > 9999)
                throw new Exception();
            if(hour < 1 || hour > 12)
                throw new Exception();
            if(min < 0 || min > 60)
                throw new Exception();
            if(!ampm.equalsIgnoreCase("am") && !ampm.equalsIgnoreCase("pm"))
                throw new Exception();
            //Calendar c = Calendar.getInstance();
            //c.setTimeInMillis(t.getTime());
            
        } catch (Exception e) {
            
            return false;
        }
        return true;
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
