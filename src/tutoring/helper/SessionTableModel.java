package tutoring.helper;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.ui.Admin;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
public class SessionTableModel extends AbstractTableModel {

    
    public enum Columns
    {
        ID(0, "First Name", String.class),
        CLIENTFIRSTNAME(1, "Last Name", String.class),
        CLIENTLASTNAME(2,"Phone", String.class),
        CLIENTPHONE(3, "Last Name", String.class),
        CLIENTEMAIL(4, "Email", String.class),
        COURSE(5, "Course", String.class),
        LEVEL(6, "Level", Integer.class),
        TEACHER(7, "Teacher", String.class),
        CATEGORY(8, "Category", String.class),
        NOTES(9, "Notes", String.class),
        PARAPROFESSIONAL(10, "Paraprofessional", String.class),
        GC(11, "GC", Boolean.class),
        ENTEREDDATE(12, "Entered Date", Timestamp.class),
        START(13, "Start Session", Timestamp.class),
        STOP(14, "Stop Session", Timestamp.class),
        MIN(15, "Min. Passed", Integer.class),
        LOCATION(16, "Location", String.class),
        CREATOR(17, "Creator", String.class),
        WALKOUT(18, "Walkout", Boolean.class);

        private int columnIndex;
        private String displayName;
        private Class<?> columnClass;
        private static HashMap<Integer, Class<?>> classMap = new HashMap<Integer, Class<?>>();
        
	private Columns(int i, String displayName, Class<?> columnClass) {
		columnIndex = i;
                this.displayName = displayName;
                this.columnClass = columnClass;
	}
        
        static {
            for (Columns v : Columns.values()) {
            classMap.put(v.columnIndex, v.columnClass);
            }
        }
        
        public static Class<?> getColumnClass(int columnIndex)
        {
            return classMap.get(columnIndex);
        }
        
        public Class<?> getColumnClass()
        {
            return columnClass;
        }
        
	public int getColumnIndex() {
		return columnIndex;
	}
        
        public String getDisplayName() {
		return displayName;
	}
 
    }
     
    private String[] columnNames;// = {"ID","fname","lname","phone", "email","course","level","teacher","notes","tutor","gc", "date","start","stop","min", "location","creator","walkout","category" };
    private  ParaprofessionalSession[] data;// = {{null,null,null,null,null,null,null,null}};
    
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList();

    public SessionTableModel(ArrayList<ParaprofessionalSession> list){
         this.tutorSessions = list;
         columnNames=generateColumns();
    }
    public SessionTableModel(){
        
    }
    
    private String[] generateColumns()
    {
        Columns[] c = Columns.class.getEnumConstants();
        String[] columnNames = new String[c.length];
        for(int i=0; i<c.length; i++)
        {
            columnNames[c[i].getColumnIndex()] = c[i].getDisplayName();
        }
        
        return columnNames;
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
    
    public void deleteRows(int[] r)
    {
        for(int i=0; i<r.length; i++)
            tutorSessions.remove(r[i]);
        fireTableDataChanged();
    }
    
    public boolean areEqual(Object one, Object two)
    {
        System.out.println("COMPARING: "+one.getClass().toString() + "  "+two.getClass().toString());
        if(one instanceof Timestamp)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            try {
                if(new Timestamp(sdf.parse(two.toString()).getTime()).toString().equals(((Timestamp)one).toString()))
                    return true;
               // System.out.println("COMPARE: "+((Timestamp)one).compareTo((Timestamp)two));
            } catch (ParseException ex) {
                System.out.println("EXCEPTIONS FO");
                return false;
            }
            return false;
            /*System.out.println(((Timestamp)one).toString() + "    "+((Timestamp)two).toString());
            if(((Timestamp)one).compareTo((Timestamp)two) == 0)
                return true;
            else
                return false;*/
        }
        else if(one instanceof Boolean && two instanceof Boolean)
        {
            if((Boolean)one && (Boolean)two)
                return true;
            else
                return false;
        }
        else
        {
            if(one.toString().equals(two.toString()))
                return true;
            else
                return false;
        }
    }
        
    @Override
    public void setValueAt(Object o, int r, int c)
    {
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        System.out.println("SetValue at :"+ r + " "+c);
        if(!o.toString().equals("CURRENT"))
        {
            if(!areEqual(getValueAt(r,c),o))
            {
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
                        HibernateTest.update(ts);
                        break;
                    case 2:
                        ts.getClientID().setlName((String)o);
                        HibernateTest.update(ts);
                        break;
                    case 3:
                        ts.getClientID().setPhone((String)o);
                        HibernateTest.update(ts);
                        break;
                    case 4:
                        ts.getClientID().setEmail((String)o);
                        HibernateTest.update(ts);
                        break;
                    case 5:
                        ts.getCourseID().getSubjectID().setAbbrevName((String)o);
                        HibernateTest.update(ts);
                        break;
                    case 6:
                        ts.getCourseID().setLevel(Integer.parseInt((String)o));
                        HibernateTest.update(ts);
                        break;
                    case 7:

                        //ts.getCourseID().getTeacherID().getfName() + " "+ts.getCourseID().getTeacherID().getlName();
                        break;
                    case 8:
                        ts.setNotes((String)o);
                        HibernateTest.update(ts);
                        break;
                    case 9:
                        //ts.getParaprofessionalID().getfName() + " "+ts.getParaprofessionalID().getlName();
                        break;
                    case 10:
                        ts.setGrammarCheck((Boolean)o);
                        HibernateTest.update(ts);
                        System.out.println("DONE");
                        break;
                    case 11:
                        System.out.println("NOW ON CASE 11");
                    try {
                        ts.setTimeAndDateEntered(new Timestamp(sdf.parse((String)o).getTime()));
                    } catch (ParseException ex) {
                        System.out.println("ERROR AT CASE 11 sessiontablemodel");
                    }
                        HibernateTest.update(ts);
                        break;
                    case 12:
                        if(ts.getSessionStart() != null)
                            ts.getSessionStart();
                        else
                            Timestamp.valueOf("9999-12-31 12:00:00");
                        break;
                    case 13:
                        if(ts.getSessionStart() != null)
                            ts.getSessionEnd();
                        else
                            Timestamp.valueOf("9999-12-31 12:00:00");
                        break;
                    case 14:
                    case 15:
                        ts.getLocationID().getName();
                    case 16:
                        //ts.getParaprofessionalCreatorID().getfName() + " "+ts.getParaprofessionalCreatorID().getlName();
                    case 17:
                        ts.setWalkout((Boolean)o);
                        //HibernateTest.update(ts);
                        tutorSessions.remove(ts);
                        break;
                    case 18:
                        ts.getCourseID().getSubjectID().getCategoryID().getName();
                   }
                   // fireTableCellUpdated(r, c);
                    fireTableDataChanged();
                   //return null;

                }
                else
                    System.out.println("CANCELLED");
            }
        }
        else
        {
            ParaprofessionalSession ts = tutorSessions.get(r);
            if(c == Columns.START.getColumnIndex())
                ts.setSessionStart(new Timestamp((new Date()).getTime()));
            else if(c == Columns.STOP.getColumnIndex())
            {
                ts.setSessionEnd(new Timestamp((new Date()).getTime()));
                //HibernateTest.update(ts);
                tutorSessions.remove(ts);
            }
            
            
            fireTableDataChanged();
           // fireTableRowsUpdated(r, c);
            //HibernateTest.update(ts);
        }
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
      //  System.out.println(getValueAt(i,j).getClass().toString());
      //  System.out.println((getValueAt(i, j-1) instanceof Timestamp));
      //  System.out.println(((Timestamp)getValueAt(i, j-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")));
        if(j != 0 && !(getValueAt(i, j-1) instanceof Timestamp && ((Timestamp)getValueAt(i, j-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")) && j == Columns.STOP.getColumnIndex()))
            return true;
        return false;
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
                return ts.getClientID().getPhone()+"";
            case 4:
                return ts.getClientID().getEmail();
            case 5:
                return ts.getCourseID().getSubjectID().getAbbrevName();
            case 6:
                return ts.getCourseID().getLevel();
            case 7:
                return ts.getCourseID().getTeacherID().getfName() + " "+ts.getCourseID().getTeacherID().getlName();
            case 8:
                return ts.getNotes(); 
            case 9:
                return ts.getParaprofessionalID().getfName() + " "+ts.getParaprofessionalID().getlName();
            case 10:
                return ts.isGrammarCheck();
            case 11:
                return ts.getTimeAndDateEntered();
            case 12:
                if(ts.getSessionStart() != null)
                    return ts.getSessionStart();
                else
                    return Timestamp.valueOf("9999-12-31 12:00:00");
            case 13:
                if(ts.getSessionStart() != null)
                    return ts.getSessionEnd();
                else
                    return Timestamp.valueOf("9999-12-31 12:00:00");
            case 14:
                if(ts.getSessionStart() != null && ts.getSessionEnd() == null)
                    return minutesOf(new Date(ts.getSessionStart().getTime()), new Date());
                else if(ts.getSessionStart() != null && ts.getSessionEnd() != null)
                    return minutesOf(new Date(ts.getSessionStart().getTime()), new Date(ts.getSessionEnd().getTime()));
                else
                    return 0;
            case 15:
                return ts.getLocationID().getName();
            case 16:
                return ts.getParaprofessionalCreatorID().getfName() + " "+ts.getParaprofessionalCreatorID().getlName();
            case 17:
                return ts.isWalkout();
            case 18:
                return ts.getCourseID().getSubjectID().getCategoryID().getName();
           }
           return null;
   }
    
    public int minutesOf(Date eDate, Date lDate)
    {
        if(eDate == null || lDate == null ) 
            return 0;

        return (int)((lDate.getTime()/60000) - (eDate.getTime()/60000));
    }

   @Override
   public Class<?> getColumnClass(int columnIndex){

       return Columns.getColumnClass(columnIndex);
          //   return null;
      }
   
   
      
 }
      
   