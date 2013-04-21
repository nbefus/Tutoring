package tutoring.helper;

import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import tutoring.entity.Course;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.util.HibernateUtil;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nathaniel
 */
public class HibernateTest {

    
    public static void joinExampleOld() {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.getCurrentSession();
        Transaction tr = sess.beginTransaction();
        // select course from Course as course join course.groups as group join group.events as groupEvent where groupEvent.eventReleased = 1
        //select w.WellName from AM.Library.AMWell as w inner join
//AM.Library.AMOperator op where w.OperatorId = op.Id

        //select w.WellName from AM.Library.AMWell as w join
//w.Operator

        Query query = sess.createSQLQuery("select * from Paraprofessional as p join ParaprofessionalCategory as c on p.paraProfessionalID=c.paraProfessionalID");//"from tutoring.entity.Paraprofessional as p join p.paraProfessionalID");
        List result = query.list();
        Iterator it = result.iterator();
        System.out.println("id  sname  sroll  scourse");
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();
            for (int i = 0; i < row.length; i++) {
                System.out.print("\t\t" + row[i]);
            }

            System.out.println();
        }
        sessFact.close();
    }

    public static List selectOld(String sqlQuery) {
        Transaction trns = null;
        List result = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //if(session == null)
        //    session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Query query = session.createQuery(sqlQuery);
            result = query.list();
            //result.toString();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        
        
      /*  SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.getCurrentSession();
        Transaction tr = sess.beginTransaction();
        Query query = sess.createQuery(sqlQuery);
        List result = query.list();
        sess.close();
        sessFact.close();*/
        return result;
        /*
         Iterator it = result.iterator();
         while(it.hasNext()){
         Client st = (Client)it.next();
         System.out.print(st.toString());
         System.out.print(" "+st.getPassword());
         System.out.print(" "+st.getfName());
         System.out.print(" "+st.getlName());
         System.out.println();
         }
         sessFact.close();*/
    }

    public static List regularSelectOld(String sqlQuery) {
        Transaction trns = null;
        List result = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();//.openSession();
       // if(session == null)
        //    session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Query query = session.createSQLQuery(sqlQuery);
            
            result = query.list();
           // result.toString();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
           session.flush();
           session.close();
        }
        
        
       /* SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.getCurrentSession();
        Transaction tr = sess.beginTransaction();
        Query query = sess.createSQLQuery(sqlQuery);
        List result = query.list();*/

        //sess.close();
        //sessFact.close();
        return result;
        /*
         Iterator it = result.iterator();
         while(it.hasNext()){
         Client st = (Client)it.next();
         System.out.print(st.toString());
         System.out.print(" "+st.getPassword());
         System.out.print(" "+st.getfName());
         System.out.print(" "+st.getlName());
         System.out.println();
         }
         sessFact.close();*/
    }

    public static void updateOld(Object obj) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();
        session.update(obj);
        tx.commit();

        System.out.println("Object Updated successfully.....!!");
        session.close();
        factory.close();
    }

    public static void createOld(Object obj) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        /*
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = sess.beginTransaction();

        sess.save(obj);
        tr.commit();*/
        //sessFact.close();
    }

    public static void batchCreateOld(Object[] obj) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            for (int i = 0; i < obj.length; i++) {
            session.save(obj[i]);
            if (i % 50 == 0 && i != 0) {
                session.flush();
                session.clear();
            }
        }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        /*
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = sess.beginTransaction();

        

        tr.commit();
        sessFact.close();*/
    }

    public static void deleteOld(Object obj) {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.getCurrentSession();
        Transaction tr = sess.beginTransaction();
        sess.delete(obj);

        System.out.println("Deleted Successfully");
        tr.commit();
        sessFact.close();

    }

    public static void fillTableWithQueryOld(String query, JTable table, String[] columns) {
        List l = DatabaseHelper.selectAll(query);
        //List c = HibernateTest.regularSelect("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'User'");

        String[][] data = null;
        boolean firstTime = true;
        Iterator it = l.iterator();

        int count = 0;
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();

            if (firstTime) {
                data = new String[l.size()][row.length];
                firstTime = false;
            }

            for (int i = 0; i < row.length; i++) {
                if(row[i] != null)
                    data[count][i] = row[i].toString();
                else
                    data[count][i] = "";
               // System.out.print("\t\t" + row[i] + "--" + row[i].getClass().toString());
            }

            count++;
        }

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(data, columns);
        table.setModel(dtm);
    }
    
    
}
