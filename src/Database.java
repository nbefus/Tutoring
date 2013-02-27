/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Nathaniel
 */
import java.sql.*;
import java.text.SimpleDateFormat;
 
public class Database {
        private static Connection connect = null;
        private static Statement statement = null;
        private static PreparedStatement preparedStatement = null;
        private static ResultSet resultSet = null;
    public static void main(String[] args) {
 
        // creates three different Connection objects
        
 
        try {
            // connect way #1
            String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
            String user = "nbefus_me";
            String password = "deathnow10";
 
            connect = DriverManager.getConnection(url1, user, password);
            
            if (connect != null) {
                
                System.out.println("Connected to the database test1");
                
                statement = connect.createStatement();
               // resultSet = statement.executeQuery("");
               // writeResultSet(resultSet);
                
                /*
                preparedStatement = connect.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
                // "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
                // Parameters start with 1
                preparedStatement.setString(1, "Test");
                preparedStatement.setString(2, "TestEmail");
                preparedStatement.setString(3, "TestWebpage");
                preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
                preparedStatement.setString(5, "TestSummary");
                preparedStatement.setString(6, "TestComment");
                preparedStatement.executeUpdate();
*/
                preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from COMMENTS");
                resultSet = preparedStatement.executeQuery();
                writeResultSet(resultSet);

                /*
                // Remove again the insert comment
                preparedStatement = connect
                .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
                preparedStatement.setString(1, "Test");
                preparedStatement.executeUpdate();
*/
                resultSet = statement.executeQuery("select * from COMMENTS");
                writeMetaData(resultSet);

                
            }
            
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        finally{
            close();
        }
    }

     private static void writeMetaData(ResultSet resultSet) throws SQLException 
     {
        //   Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
          System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
      }

      private static void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
          // It is possible to get the columns via name
          // also possible to get the columns via the column number
          // which starts at 1
          // e.g. resultSet.getSTring(2);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            
          String user = resultSet.getString("myuser");
          String website = resultSet.getString("webpage");
          String summery = resultSet.getString("summery");
          Date date = resultSet.getDate("datum");
          Timestamp date2 = resultSet.getTimestamp("datum");
          System.out.println("Date new new new "+date2.toString());
          
          System.out.println("DATE TIME IS THIS: "+df.format(date));
          String comment = resultSet.getString("comments");
          System.out.println("User: " + user);
          System.out.println("Website: " + website);
          System.out.println("Summery: " + summery);
          System.out.println("Date: " + date);
          System.out.println("Comment: " + comment);
        }
      }

      // You need to close the resultSet
      private static void close() {
        try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

          if (connect != null) {
            connect.close();
          }
        } catch (Exception e) {

        }
      }
}

