/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.sql.Timestamp;
import java.util.ArrayList;
import tutoring.entity.Subject;
import tutoring.entity.SubjectCategory;
import tutoring.entity.Teacher;
import tutoring.entity.TeacherSubject;
import tutoring.entity.Tutor;
import tutoring.entity.TutorCategory;
import tutoring.entity.TutorSession;
import tutoring.entity.User;

/**
 *
 * @author dabeefinator
 */
public class FakeValues 
{
    private ArrayList<Tutor> tutors = new ArrayList<Tutor>();
    private ArrayList<Subject> subjects = new ArrayList<Subject>();
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private ArrayList<SubjectCategory> subjectCategories = new ArrayList<SubjectCategory>();
    private ArrayList<TutorCategory> tutorCategories = new ArrayList<TutorCategory>();
    private ArrayList<TeacherSubject> teacherSubjects = new ArrayList<TeacherSubject>();
    private ArrayList<TutorSession> tutorSessions = new ArrayList<TutorSession>();
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
        TutorSession ts = new TutorSession(getTutorSessions().size(), "sPrecious", "sBinas", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 1500, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), false, false, "Notes!!!");
        getTutorSessions().add(ts);
        TutorSession ts1 = new TutorSession(getTutorSessions().size(), "sNathaniel", "sBefus", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 2500, new Timestamp(System.currentTimeMillis()-1500000000), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), true, false, "Notes!!!");
        getTutorSessions().add(ts1);
        TutorSession ts2 = new TutorSession(getTutorSessions().size(), "sSho", "sShohei", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 3500, new Timestamp(System.currentTimeMillis()-800000000), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), false, true, "Notes!!!");
        getTutorSessions().add(ts2);
        TutorSession ts3 = new TutorSession(getTutorSessions().size(), "sCurt", "sPowley", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 4500, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), true, true, "Notes!!!");
        getTutorSessions().add(ts3);
        TutorSession ts4 = new TutorSession(getTutorSessions().size(), "sAbe", "sPinada", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 5500, new Timestamp(System.currentTimeMillis()-1000000000), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), true, false, "Notes!!!");
        getTutorSessions().add(ts4);
        TutorSession ts5 = new TutorSession(getTutorSessions().size(), "sMark", "sEvangelist", getTutors().get(getTutorSessions().size()), getSubjects().get(getTutorSessions().size()), getTeachers().get(getTutorSessions().size()), 6500, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), false, true, "Notes!!!");
        getTutorSessions().add(ts5);
    }
    
    public void teacherSubjects()
    {
        TeacherSubject ts = new TeacherSubject(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 1000);
        getTeacherSubjects().add(ts);
        TeacherSubject ts1 = new TeacherSubject(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 2000);
        getTeacherSubjects().add(ts1);
        TeacherSubject ts2 = new TeacherSubject(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 3000);
        getTeacherSubjects().add(ts2);
        TeacherSubject ts3 = new TeacherSubject(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 4000);
        getTeacherSubjects().add(ts3);
        TeacherSubject ts4 = new TeacherSubject(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 5000);
        getTeacherSubjects().add(ts4);
        TeacherSubject ts5 = new TeacherSubject(getTeachers().get(getTeacherSubjects().size()), getSubjects().get(getTeacherSubjects().size()), 6000);
        getTeacherSubjects().add(ts5);
    }
    
    public void tutorCategories()
    {
        TutorCategory tc = new TutorCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc);
        TutorCategory tc1 = new TutorCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc1);
        TutorCategory tc2 = new TutorCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc2);
        TutorCategory tc3 = new TutorCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc3);
        TutorCategory tc4 = new TutorCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
        getTutorCategories().add(tc4);
        TutorCategory tc5 = new TutorCategory(getTutors().get(getTutorCategories().size()), getSubjectCategories().get(getTutorCategories().size()));
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
        SubjectCategory sc = new SubjectCategory(getSubjectCategories().size(), "MABS");
        getSubjectCategories().add(sc);
        SubjectCategory sc1 = new SubjectCategory(getSubjectCategories().size(), "ENG");
        getSubjectCategories().add(sc1);
        SubjectCategory sc2 = new SubjectCategory(getSubjectCategories().size(), "LANG");
        getSubjectCategories().add(sc2);
        SubjectCategory sc3 = new SubjectCategory(getSubjectCategories().size(), "SSM");
        getSubjectCategories().add(sc3);
        SubjectCategory sc4 = new SubjectCategory(getSubjectCategories().size(), "OTHER");
        getSubjectCategories().add(sc4);
        SubjectCategory sc5 = new SubjectCategory(getSubjectCategories().size(), "IDK");
        getSubjectCategories().add(sc5);
    }
    
    public void tutors()
    {
        Tutor t = new Tutor(getTutors().size(), "Precious", "Binas", true);
        getTutors().add(t);
        Tutor t1 = new Tutor(getTutors().size(), "Nathaniel", "Befus", true);
        getTutors().add(t1);
        Tutor t2 = new Tutor(getTutors().size(), "Sho", "Shohei", true);
         getTutors().add(t2);
        Tutor t3 = new Tutor(getTutors().size(), "Curt", "Powley", false);
        getTutors().add(t3);
        Tutor t4 = new Tutor(getTutors().size(), "Abe", "Pinada", false);
        getTutors().add(t4);
        Tutor t5 = new Tutor(getTutors().size(), "Mark", "Evangelist", false);
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
    public ArrayList<Tutor> getTutors() {
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
    public ArrayList<SubjectCategory> getSubjectCategories() {
        return subjectCategories;
    }

    /**
     * @return the tutorCategories
     */
    public ArrayList<TutorCategory> getTutorCategories() {
        return tutorCategories;
    }

    /**
     * @return the teacherSubjects
     */
    public ArrayList<TeacherSubject> getTeacherSubjects() {
        return teacherSubjects;
    }

    /**
     * @return the tutorSessions
     */
    public ArrayList<TutorSession> getTutorSessions() {
        return tutorSessions;
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

}
