/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Nathaniel
 */
public class ComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor {  
      private static final long serialVersionUID = 4170056738609945778L;  
  
      private JComboBox jcb;  
  
      private ArrayList<String> choice;  
      
      public ComboBoxCellEditor(ArrayList<String> choice) {  
         this.choice = choice;
         //this.clickCountToStart = 2;
      }  
  
      @Override  
      public JComponent getTableCellEditorComponent(JTable table, Object item, boolean isSelected, int row, int column) {  
         jcb = new JComboBox();
         
         
        String[] hey = new String[choice.size()];
        for(int i=0; i<choice.size(); i++)
            hey[i]=choice.get(i);
       // Java2sAutoComboBox comboBox = new Java2sAutoComboBox(listSomeString);
        AutoCompleteComboBox comboBox = new AutoCompleteComboBox(jcb, hey);
          
          
            
        // jcb.setEditable(true);  
  /*
         if (item != null) {  
            jcb.setSelectedItem(item);  
         }  */
  
         return jcb;  
      }  
  
      @Override  
      public Object getCellEditorValue() {  
         return jcb.getSelectedItem(); // change to return jcb.getEditor().getItem();  
      }  
   }  