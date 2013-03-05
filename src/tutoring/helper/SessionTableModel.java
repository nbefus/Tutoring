package tutoring.helper;


import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.Tutor;
import tutoring.entity.TutorSession;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nathaniel
 */
public class SessionTableModel extends AbstractTableModel {

    private String[] columnNames = {"sessionID","fname","lname","course","level","teacher","notes","tutor","future","gc"};
    private  TutorSession[] data;// = {{null,null,null,null,null,null,null,null}};
    
    private ArrayList<TutorSession> tutorSessions = new ArrayList();

    public SessionTableModel(ArrayList<TutorSession> list){
         this.tutorSessions = list;
    }
    public SessionTableModel(){
        
    }
    
    public void addRow(String fname, String lname, Subject subject, int level, Teacher teacher, String notes, Tutor tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        TutorSession tutorSession = new TutorSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
        fireTableDataChanged();
    }
    
    public void addRow(TutorSession ts)
    {
        tutorSessions.add(ts);
        fireTableDataChanged();
    }
    /*
    public void addRow(String fname, String lname, String subjectAbbrevName, int level, String teacherLName, String notes, Tutor tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        TutorSession tutorSession = new TutorSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
    }*/

    @Override
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    @Override     
    public int getRowCount() {
        return tutorSessions.size();
    }

    @Override        
    public int getColumnCount() {
        return columnNames.length; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TutorSession ts = tutorSessions.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return rowIndex;
            case 1:
                return ts.getfName();
            case 2:
                return ts.getlName();
            case 3:
                return ts.getSubject().getAbbrevName();
            case 4:
                return ts.getLevel();
            case 5:
                return ts.getTeacher().getlName();
            case 6:
                return ts.getNotes(); 
            case 7:
                return ts.getTutor().getfName();
            case 8:
                return ts.isFuture();
            case 9:
                return ts.isGrammerCheck();
            
           }
           return null;
   }

   @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return Integer.class;
             case 1:
               return String.class;
             case 2:
               return String.class;
             case 3:
               return String.class;
             case 4:
               return Integer.class;
             case 5:
               return String.class;
             case 6:
               return String.class;
             case 7:
               return String.class;
             case 8:
               return Boolean.class;
             case 9:
               return Boolean.class;
                 
             }
             return null;
      }
 }
      
   