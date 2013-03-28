/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.old;

import java.sql.Timestamp;
import java.util.ArrayList;
import tutoring.entity.Subject;
import tutoring.entity.Category;
import tutoring.entity.Teacher;
import tutoring.entity.Course;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalCategory;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.User;

/**
 *
 * @author dabeefinator
 */
public class FakeValues 
{
    private ArrayList<Paraprofessional> tutors = new ArrayList<Paraprofessional>();
    private ArrayList<Subject> subjects = new ArrayList<Subject>();
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private ArrayList<Category> subjectCategories = new ArrayList<Category>();
    private ArrayList<ParaprofessionalCategory> tutorCategories = new ArrayList<ParaprofessionalCategory>();
    private ArrayList<Course> teacherSubjects = new ArrayList<Course>();
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList<ParaprofessionalSession>();
    private ArrayList<User> users = new ArrayList<User>();
     
    
    public FakeValues()
    {
        users();
        tutors();
        teachers();
        subjectCategories();
        subjects();
        teacherSubjects();
        
        tutorCategories();
        tutorSessions();
        
        
        
    }
    
    public void users()
    {
        User u = new User(getUsers().size(), "uBinas", "uPrecious", "pbinas", "false");
        getUsers().add(u);
        User u1 = new User(getUsers().size(), "uBefus", "uNathaniel", "nbefus", "howsitgoing");
        getUsers().add(u1);
        User u2 = new User(getUsers().size(), "uShohei", "uSho", "sshohei", "idkwhatidk");
        getUsers().add(u2);
        User u3 = new User(getUsers().size(), "uPowley", "uCurt", "cpowley", "standupmeeting");
        getUsers().add(u3);
        User u4 = new User(getUsers().size(), "uPinada", "uAbe", "apinada", "whoa");
        getUsers().add(u4);
        User u5 = new User(getUsers().size(), "uMark", "uEvangelist", "mevangelist", "superprogrammer");
        getUsers().add(u5);
        
    }
    
    public void tutorSessions()
    {
        ParaprofessionalSession ts = new ParaprofessionalSession(getTutorSessions().size(), "sPrecious", "sBinas", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 1500, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), false, false, "Notes!!!");
        getTutorSessions().add(ts);
        ParaprofessionalSession ts1 = new ParaprofessionalSession(getTutorSessions().size(), "sNathaniel", "sBefus", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 2500, new Timestamp(System.currentTimeMillis()-1500000000), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), true, false, "Notes!!!");
        getTutorSessions().add(ts1);
        ParaprofessionalSession ts2 = new ParaprofessionalSession(getTutorSessions().size(), "sSho", "sShohei", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 3500, new Timestamp(System.currentTimeMillis()-800000000), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), false, true, "Notes!!!");
        getTutorSessions().add(ts2);
        ParaprofessionalSession ts3 = new ParaprofessionalSession(getTutorSessions().size(), "sCurt", "sPowley", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 4500, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), true, true, "Notes!!!");
        getTutorSessions().add(ts3);
        ParaprofessionalSession ts4 = new ParaprofessionalSession(getTutorSessions().size(), "sAbe", "sPinada", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 5500, new Timestamp(System.currentTimeMillis()-1000000000), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), true, false, "Notes!!!");
        getTutorSessions().add(ts4);
        ParaprofessionalSession ts5 = new ParaprofessionalSession(getTutorSessions().size(), "sMark", "sEvangelist", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 6500, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), false, true, "Notes!!!");
        getTutorSessions().add(ts5);
    }
    
    public void teacherSubjects()
    {
        Course ts = new Course(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 1000);
        getTeacherSubjects().add(ts);
        Course ts1 = new Course(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 2000);
        getTeacherSubjects().add(ts1);
        Course ts2 = new Course(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 3000);
        getTeacherSubjects().add(ts2);
        Course ts3 = new Course(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 4000);
        getTeacherSubjects().add(ts3);
        Course ts4 = new Course(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 5000);
        getTeacherSubjects().add(ts4);
        Course ts5 = new Course(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 6000);
        getTeacherSubjects().add(ts5);
    }
    
    public void tutorCategories()
    {
        ParaprofessionalCategory tc = new ParaprofessionalCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc);
        ParaprofessionalCategory tc1 = new ParaprofessionalCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc1);
        ParaprofessionalCategory tc2 = new ParaprofessionalCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc2);
        ParaprofessionalCategory tc3 = new ParaprofessionalCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc3);
        ParaprofessionalCategory tc4 = new ParaprofessionalCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc4);
        ParaprofessionalCategory tc5 = new ParaprofessionalCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc5);
    }   
    
    public void subjects()
    {
        Subject s = new Subject(getSubjects().size(), "ACCT", "Accounting", getSubjectCategories().get(getSubjects().size()));
        getSubjects().add(s);
        Subject s1 = new Subject(getSubjects().size(), "CSCI", "Computer Science", getSubjectCategories().get(getSubjects().size()));
        getSubjects().add(s1);
        Subject s2 = new Subject(getSubjects().size(), "MATH", "Math", getSubjectCategories().get(getSubjects().size()));
        getSubjects().add(s2);
        Subject s3 = new Subject(getSubjects().size(), "BIOL", "Biology", getSubjectCategories().get(getSubjects().size()));
        getSubjects().add(s3);
        Subject s4 = new Subject(getSubjects().size(), "LIT", "Literature", getSubjectCategories().get(getSubjects().size()));
        getSubjects().add(s4);
        Subject s5 = new Subject(getSubjects().size(), "WRI", "Writing", getSubjectCategories().get(getSubjects().size()));
        getSubjects().add(s5);

    }
    
    public void subjectCategories()
    {
        Category sc = new Category(getSubjectCategories().size(), "MABS");
        getSubjectCategories().add(sc);
        Category sc1 = new Category(getSubjectCategories().size(), "ENG");
        getSubjectCategories().add(sc1);
        Category sc2 = new Category(getSubjectCategories().size(), "LANG");
        getSubjectCategories().add(sc2);
        Category sc3 = new Category(getSubjectCategories().size(), "SSM");
        getSubjectCategories().add(sc3);
        Category sc4 = new Category(getSubjectCategories().size(), "OTHER");
        getSubjectCategories().add(sc4);
        Category sc5 = new Category(getSubjectCategories().size(), "IDK");
        getSubjectCategories().add(sc5);
    }
    
    public void tutors()
    {
        Paraprofessional t = new Paraprofessional(getTutors().size(), "Precious", "Binas", true);
        getTutors().add(t);
        Paraprofessional t1 = new Paraprofessional(getTutors().size(), "Nathaniel", "Befus", true);
        getTutors().add(t1);
        Paraprofessional t2 = new Paraprofessional(getTutors().size(), "Sho", "Shohei", true);
         getTutors().add(t2);
        Paraprofessional t3 = new Paraprofessional(getTutors().size(), "Curt", "Powley", false);
        getTutors().add(t3);
        Paraprofessional t4 = new Paraprofessional(getTutors().size(), "Abe", "Pinada", false);
        getTutors().add(t4);
        Paraprofessional t5 = new Paraprofessional(getTutors().size(), "Mark", "Evangelist", false);
        getTutors().add(t5);
    }
    
    public void teachers()
    {
        Teacher teach = new Teacher(getTeachers().size(), "tBinas", "tPrecious");
        getTeachers().add(teach);
        Teacher teach1 = new Teacher(getTeachers().size(), "tBefus", "tNathaniel");
        getTeachers().add(teach1);
        Teacher teach2 = new Teacher(getTeachers().size(), "tShohei", "tSho");
        getTeachers().add(teach2);
        Teacher teach3 = new Teacher(getTeachers().size(), "tPowley", "tCurt");
        getTeachers().add(teach3);
        Teacher teach4 = new Teacher(getTeachers().size(), "tPinada", "tAbe");
        getTeachers().add(teach4);
        Teacher teach5 = new Teacher(getTeachers().size(), "tEvangelist", "tMark");
        getTeachers().add(teach5);
        
    }

    /**
     * @return the tutors
     */
    public ArrayList<Paraprofessional> getTutors() {
        return tutors;
    }

    /**
     * @return the subjects
     */
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    /**
     * @return the teachers
     */
    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * @return the subjectCategories
     */
    public ArrayList<Category> getSubjectCategories() {
        return subjectCategories;
    }

    /**
     * @return the tutorCategories
     */
    public ArrayList<ParaprofessionalCategory> getTutorCategories() {
        return tutorCategories;
    }

    /**
     * @return the teacherSubjects
     */
    public ArrayList<Course> getTeacherSubjects() {
        return teacherSubjects;
    }

    /**
     * @return the tutorSessions
     */
    public ArrayList<ParaprofessionalSession> getTutorSessions() {
        return tutorSessions;
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

}
