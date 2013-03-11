package tutoring.helper;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nathaniel
 */
public class SessionTableModel extends AbstractTableModel {

    private String[] columnNames = {"sessionID","fname","lname","course","level","teacher","notes","tutor","future","gc", "date"};
    private  ParaprofessionalSession[] data;// = {{null,null,null,null,null,null,null,null}};
    
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList();

    public SessionTableModel(ArrayList<ParaprofessionalSession> list){
         this.tutorSessions = list;
    }
    public SessionTableModel(){
        
    }
    
    public void addRow(String fname, String lname, Subject subject, int level, Teacher teacher, String notes, Paraprofessional tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        ParaprofessionalSession tutorSession = new ParaprofessionalSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
        fireTableDataChanged();
    }
    
    public void addRow(ParaprofessionalSession ts)
    {
        tutorSessions.add(ts);
        fireTableDataChanged();
    }
    
    @Override
    public void setValueAt(Object o, int r, int c)
    {
        System.out.println("EDITED: "+o.toString());
    }
    
    /*
    public void addRow(String fname, String lname, String subjectAbbrevName, int level, String teacherLName, String notes, Paraprofessional tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        ParaprofessionalSession tutorSession = new ParaprofessionalSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
    }*/

    @Override
    public boolean isCellEditable(int i, int j)
    {
        return true;
    }
    
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
        ParaprofessionalSession ts = tutorSessions.get(rowIndex);
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
            case 10:
                return ts.getTimeAndDateEntered();
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
             case 10:
               return Timestamp.class;
                 
             }
             return null;
      }
   
   
      
 }
      
   