package tutoring.helper;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
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

    private String[] columnNames = {"SessionID","fname","lname","course","level","teacher","notes","tutor","gc", "date","start","stop", "location","creator","walkout" };
    private  ParaprofessionalSession[] data;// = {{null,null,null,null,null,null,null,null}};
    
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList();

    public SessionTableModel(ArrayList<ParaprofessionalSession> list){
         this.tutorSessions = list;
    }
    public SessionTableModel(){
        
    }
    
    /*
    public void addRow(String fname, String lname, Subject subject, int level, Teacher teacher, String notes, Paraprofessional tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        ParaprofessionalSession tutorSession = new ParaprofessionalSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
        fireTableDataChanged();
    }*/
    
    public void addRow(ParaprofessionalSession ts)
    {
        tutorSessions.add(ts);
        fireTableDataChanged();
    }
        
    @Override
    public void setValueAt(Object o, int r, int c)
    {
        System.out.println("SetValue at");
        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to change value "+getValueAt(r,c)+" to "+o.toString());
        if(option == JOptionPane.YES_OPTION)
        {
            System.out.println("EDITED at setValueAt STM: "+o.toString());
           // tutorSessions.get(r)
                    
            ParaprofessionalSession ts = tutorSessions.get(r);
            switch (c) {
            case 0: 
                break;
            case 1:
                ts.getClientID().setfName((String)o);
            case 2:
                ts.getClientID().setlName((String)o);
            case 3:
                ts.getCourseID().getSubjectID().setAbbrevName((String)o);
            case 4:
                ts.getCourseID().setLevel(((Integer)o).intValue());
            case 5:
                //ts.getCourseID().getTeacherID().getfName() + " "+ts.getCourseID().getTeacherID().getlName();
            case 6:
                ts.getNotes(); 
            case 7:
                //ts.getParaprofessionalID().getfName() + " "+ts.getParaprofessionalID().getlName();
            case 8:
                ts.isGrammarCheck();
            case 9:
                ts.getTimeAndDateEntered();
            case 10:
                if(ts.getSessionStart() != null)
                    ts.getSessionStart();
                else
                    Timestamp.valueOf("9999-12-31 12:00:00");
            case 11:
                if(ts.getSessionStart() != null)
                    ts.getSessionEnd();
                else
                    Timestamp.valueOf("9999-12-31 12:00:00");
            case 12:
                ts.getLocationID().getName();
            case 13:
                //ts.getParaprofessionalCreatorID().getfName() + " "+ts.getParaprofessionalCreatorID().getlName();
            case 14:
                ts.isWalkout();
           }
           //return null;
        }
        else
            System.out.println("CANCELLED");
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
                return ts.getParaprofessionalSessionID();
            case 1:
                return ts.getClientID().getfName();
            case 2:
                return ts.getClientID().getlName();
            case 3:
                return ts.getCourseID().getSubjectID().getAbbrevName();
            case 4:
                return ts.getCourseID().getLevel();
            case 5:
                return ts.getCourseID().getTeacherID().getfName() + " "+ts.getCourseID().getTeacherID().getlName();
            case 6:
                return ts.getNotes(); 
            case 7:
                return ts.getParaprofessionalID().getfName() + " "+ts.getParaprofessionalID().getlName();
            case 8:
                return ts.isGrammarCheck();
            case 9:
                return ts.getTimeAndDateEntered();
            case 10:
                if(ts.getSessionStart() != null)
                    return ts.getSessionStart();
                else
                    return Timestamp.valueOf("9999-12-31 12:00:00");
            case 11:
                if(ts.getSessionStart() != null)
                    return ts.getSessionEnd();
                else
                    return Timestamp.valueOf("9999-12-31 12:00:00");
            case 12:
                return ts.getLocationID().getName();
            case 13:
                return ts.getParaprofessionalCreatorID().getfName() + " "+ts.getParaprofessionalCreatorID().getlName();
            case 14:
                return ts.isWalkout();
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
               return Timestamp.class;
             case 10:
               return Timestamp.class;
             case 11:
               return Timestamp.class;
             case 12:
               return String.class;
             case 13:
               return String.class;
             case 14:
               return Boolean.class;
                 
             }
             return null;
      }
   
   
      
 }
      
   