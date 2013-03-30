/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package preciousAreaSoNoMergeHappensLOL;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import tutoring.entity.*;
import tutoring.helper.HibernateTest;

/**
 *
 * @author pres_is
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ArrayList listOfParaprofessionals = new ArrayList();
        ArrayList<Paraprofessional> list = (ArrayList<Paraprofessional>) HibernateTest.select("from Paraprofessional where terminationDate is not null");

        
        //nameCombo.setModel(new DefaultComboBoxModel(listOfParaprofessionals.toArray()));
        
        
        
        System.out.println(list.get(2).toString());
        
        
        
        
    }
}
