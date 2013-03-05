package tutoring.helper;


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
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

    private String[] columnNames = {"fname","lname","course","level","teacher","notes","tutor","gc"};
    private  TutorSession[] data;// = {{null,null,null,null,null,null,null,null}};
    
    private ArrayList<TutorSession> tutorSessions = new ArrayList();

    public SessionTableModel(ArrayList<TutorSession> list){
         this.tutorSessions = list;
    }
    public SessionTableModel(){
        
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
        return 7; 
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
                return ts.getSubjectID();
            case 4:
                return ts.getTimerInMinutesSeconds();
            case 5:
                return ts.getTimerEndingText();
            case 6:
                return ts.isContainsOtherInstructions(); 
           }
           return null;
   }

   @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return String.class;
             case 1:
               return Integer.class;
             case 2:
               return String.class;
             case 3:
               return Double.class;
             case 4:
               return Double.class;
             case 5:
               return String.class;
             case 6:
               return Boolean.class;
             }
             return null;
      }
 }
      
   