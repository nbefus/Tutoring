package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class Course
{
    
    public enum CourseTable {

        COURSEID("courseId", true, getTableAlias()+".courseId"),
        TEACHERID("teacherID", true, getTableAlias()+".teacherID"),
        SUBJECTID("subjectID", true, getTableAlias()+".subjectID"),
        LEVEL("level", true, getTableAlias()+".level"),
        SUBJECTABBREVNAME("abbrevName", false, getSubjectAlias()+".abbrevName"),
        SUBJECTCATEGORYID("categoryID", false, getSubjectAlias()+".categoryID"),
        SUBJECTCATEGORYNAME("name", false, getCategoryAlias()+".name"),
        TEACHERLNAME("lName", false, getTeacherAlias()+".lName"),
        TEACHERFNAME("fName", false, getTeacherAlias()+".fName");
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "course";
        private static final String subjectAlias = "subject";
        private static final String teacherAlias = "teacher";
        private static final String categoryAlias = "category";
        
        
        private static final String table = "Course";
        private CourseTable(String name, boolean mainTableColumn, String withAlias) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
        }

        public String getName() {
            return name;
        }

        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        public String getWithAlias() {
            return withAlias;
        } 
        
        public static String getTableAlias()
        {
            return tableAlias;
        } 
        
        public static String getTable()
        {
            return table;
        } 
        
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Course.CourseTable[] columns = Course.CourseTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }

        public static String getSubjectAlias()
        {
            return subjectAlias;
        }

        public static String getTeacherAlias()
        {
            return teacherAlias;
        }

        public static String getCategoryAlias()
        {
            return categoryAlias;
        }
    }
    
    
    private int courseID;       // primary key
    private Teacher teacherID;    // foreign key
    private Subject subjectID;    // foreign key
    private int level;

    public Course(int course, Teacher teacher, Subject subject, int level) {
        this.courseID = course;
        this.teacherID = teacher;
        this.subjectID = subject;
        this.level = level;
    }
    
    public Course()
    {
        
    }
    
   public static Object[] getValues(Course c)
    {
        Object[] values = new Object[4];
        values[0]=c.getCourseID();
        values[1]=c.getTeacherID();
        values[2]=c.getSubjectID();
        values[3]=c.getLevel();
        return values;
    }
   
    public static ArrayList<Course> selectAllCourse(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Course> courses = new ArrayList<Course>();
        
        try {
            // connect way #1
         //   String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
         //   String user = "nbefus_me";
        //    String password = "heythere";

         //   connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                Course.CourseTable [] cols = Course.CourseTable.class.getEnumConstants();
                String columnSetUp = "";
                for(int i=0; i<cols.length; i++)
                {
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
                }
                columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
                
                statement = connect.createStatement();

                String query = "SELECT "+columnSetUp+" FROM Course "+CourseTable.getTableAlias()+" join Teacher "+CourseTable.getTeacherAlias()+" on "+CourseTable.TEACHERID.getWithAlias()+"="+CourseTable.getTeacherAlias()+"."+CourseTable.TEACHERID.getName()+
                        " join Subject "+CourseTable.getSubjectAlias()+" on "+CourseTable.SUBJECTID.getWithAlias()+"="+CourseTable.getSubjectAlias()+"."+CourseTable.SUBJECTID.getName()+" join Category "+CourseTable.getCategoryAlias()+" on "+CourseTable.SUBJECTCATEGORYID.getWithAlias()+" = "+CourseTable.getCategoryAlias()+"."+CourseTable.SUBJECTCATEGORYID.getName();
                query+= " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    courses.add(new Course(resultSet.getInt(CourseTable.COURSEID.getWithAlias()), new Teacher(resultSet.getInt(CourseTable.TEACHERID.getWithAlias()), resultSet.getString(CourseTable.TEACHERLNAME.getWithAlias()), resultSet.getString(CourseTable.TEACHERFNAME.getWithAlias())), new Subject(resultSet.getInt(CourseTable.SUBJECTID.getWithAlias()), resultSet.getString(CourseTable.SUBJECTABBREVNAME.getWithAlias()), new Category(resultSet.getInt(CourseTable.SUBJECTCATEGORYID.getWithAlias()),resultSet.getString(CourseTable.SUBJECTCATEGORYNAME.getWithAlias()))), resultSet.getInt(CourseTable.LEVEL.getWithAlias())));
                }
                
                 return courses;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

         /* if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return courses;
        }
    }
    
    /**
     * @return the teacherID
     */
    public Teacher getTeacherID() {
        return teacherID;
    }

    /**
     * @param teacherID the teacherID to set
     */
    public void setTeacherID(Teacher teacherID) {
        this.teacherID = teacherID;
    }

    /**
     * @return the subjectID
     */
    public Subject getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(Subject subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public boolean equals(Course c)
    {
        if(this.level == c.getLevel() && this.subjectID.equals(c.getSubjectID()) && this.teacherID.equals(c.getTeacherID()))
            return true;
        else
            return false;
    }
    
    @Override
    public String toString()
    {
        return courseID + " " + teacherID.toString() + " " + subjectID.toString() + " " + level;
    }
}
    
