/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.editor;

import java.awt.Dialog;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import tutoring.ui.ClientEdit;

/**
 *
 * @author Nathaniel
 */
public class ComboBoxCellEditor extends DefaultCellEditor {  
  
      private JComboBox jcb;  
  
      
     // private ArrayList<String> choice;  
      
      public ComboBoxCellEditor(final JComboBox cb) 
      {
         super(cb);
        // this.choice = choice;
          this.clickCountToStart = 2;
         //this.clickCountToStart = 2;
      }  
  
      @Override  
      public JComponent getTableCellEditorComponent(JTable table, Object item, boolean isSelected, int row, int column) {  
         jcb = new JComboBox();
         
         
       // String[] hey = new String[choice.size()];
       // for(int i=0; i<choice.size(); i++)
       //     hey[i]=choice.get(i);
       // Java2sAutoComboBox comboBox = new Java2sAutoComboBox(listSomeString);
       // AutoCompleteComboBox comboBox = new AutoCompleteComboBox(jcb, choice, item+"");
          
          if(column == 1 || column == 2 || column == 3 || column == 4)
         {
             //table.editCellAt(row, column);
             
             /*
             table.editCellAt(row, column+1);
             
             
             table.editCellAt(row, column+2);
             table.editCellAt(row, column+3);*/
             JDialog dialog = null;
             ClientEdit dialogPanel = new ClientEdit();
             if (dialog == null) {
            Window win = SwingUtilities.getWindowAncestor(table.getRootPane().getContentPane());
            if (win != null) {
               dialog = new JDialog(win, "My Dialog",
                        Dialog.ModalityType.APPLICATION_MODAL);
               dialog.getContentPane().add(dialogPanel);
               dialog.pack();
               dialog.setLocationRelativeTo(null);
            }
      }
      dialog.setVisible(true); // here the modal dialog takes over

      // this line starts *after* the modal dialog has been disposed
      // **** here's the key where I get the String from JTextField in the GUI held
      // by the JDialog and put it into this GUI's JTextField.
      return null;
     ////   ((JTextComponent)jcb.getEditor().getEditorComponent()).setText(dialogPanel.getEmail());
         
         }
            
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