package tutoring.helper;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */

public class AutoCompleteComboBox implements KeyListener, ActionListener, MouseListener
{
    private String[] keywords;// = {"hey", "How are you","howdy","hinting","hinter","hunter"};
    private String entry = "";
    private boolean isUpdating = false;
    private boolean zeroIndexSel = false;
    private JComboBox jcb;
    private boolean firstClick = true;
    private int lastSize=0;
     ArrayList<String> matches = new ArrayList<String>();
    //private MutableComboBoxModel mcbm;
    
    public AutoCompleteComboBox(JComboBox jcb, String[] keywords)
    {
        this.keywords = keywords;
        Arrays.sort(keywords);
        this.jcb = jcb;
        jcb.setEditable(true);
        updatelist();
        

        jcb.getEditor().getEditorComponent().addKeyListener(AutoCompleteComboBox.this);
        jcb.getEditor().getEditorComponent().addMouseListener(AutoCompleteComboBox.this);
        jcb.addActionListener(AutoCompleteComboBox.this);

        jcb.setMaximumRowCount(5);
        
    }
    
    public void setList(JList l, ArrayList<String> s)
    {
        //s.addAll(Arrays.asList(keywords));
        l.setListData(keywords);
        
    }
    
    public void updatelist()
    {
        boolean moreChars;
        
        
        String text = ((JTextComponent)jcb.getEditor().getEditorComponent()).getText();
        if(text.length() > lastSize)
            moreChars = true;
        else
            moreChars = false;
        lastSize = text.length();
        //mcbm.
        MutableComboBoxModel mcbm = (MutableComboBoxModel)jcb.getModel();
        
        System.out.println(text);
        
       
        //vect = new Vector<String>();
 
            if(moreChars)
            {
                System.out.println("MORE CHARS");
                int max = matches.size();
                Object[] values = matches.toArray();
                
                //ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
                for(int i=0; i<max; i++)
                {
                    if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                    {

                        mcbm.removeElement(((String)values[i]));
                        //indexesToRemove.add(i);
                        matches.remove(((String)values[i]));
                       // System.out.println("CONTAINS: "+keywords[i]);
                    }   
                }
                
               /* System.out.println("SIZE: "+max);
                for(int i=0; i<indexesToRemove.size(); i++)
                    matches.remove(matches.get(indexesToRemove.get(i).intValue()));
                System.out.println("AFTER: "+matches.size());*/
            }
            else
            {
                for(int i=0; i<keywords.length; i++)
                {
                
                    if(!matches.contains(keywords[i]) && keywords[i].toUpperCase().contains(text.toUpperCase()))
                    {
                        
                        //matches.add(keywords[i]);
                        mcbm.addElement(keywords[i]);

                        matches.add(keywords[i]);

                       // System.out.println("CONTAINS: "+keywords[i]);
                    }
            
                }
                
            
            }
            
        
        
       // System.out.println("SIZE: "+mcbm.getSize());
      
        
        //jcb.setMaximumRowCount(mcbm.getSize());
        if(!firstClick && mcbm.getSize()>0)
            jcb.setSelectedIndex(0);
        else
            System.out.println("FIRST CLICK OR MCBM <=0");
        //jcb.setMaximumRowCount(5);
        
        
        
        
    }

    public String getEntry()
    {
        return entry;
    }

    
    

    @Override
    public void keyReleased(KeyEvent evt) 
    {
        if(firstClick && evt.getKeyCode() != KeyEvent.VK_TAB)
        {
            firstClick=false;
            ((JTextComponent)jcb.getEditor().getEditorComponent()).setText("");
           
        }
        
        String text = ((JTextComponent)jcb.getEditor().getEditorComponent()).getText();

        System.out.println("CURRENT SELECTION: "+jcb.getSelectedIndex());
        if(jcb.getSelectedIndex() > -1)
            System.out.println("WITH" +jcb.getSelectedItem().toString());
        if (evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            isUpdating = true;
            zeroIndexSel = true;

            //originalText = text;
            jcb.setPopupVisible(true);

        }
        if(!isUpdating)
        {
            updatelist();
            jcb.setPopupVisible(true);
            ((JTextComponent)jcb.getEditor().getEditorComponent()).setText(text);

            int size = jcb.getModel().getSize();

            if(size == 0)
            {

                System.out.println("SIZE 0;;");
                jcb.setPopupVisible(false);

            }

        }
        else
        {
            if(!isUpdating)
            {
                jcb.setPopupVisible(false);

            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel)
        {
            System.out.println("UP PRESSED and ZERO INDEX");

            isUpdating=false;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            System.out.println("ENTER PRESSED");
            if(jcb.getSelectedIndex() >= 0)
                ((JTextComponent)jcb.getEditor().getEditorComponent()).setText(jcb.getSelectedItem().toString());

            isUpdating=false;

            jcb.setPopupVisible(false);
            updatelist();
            
            //jcb.requestFocusInWindow();
        }

        else if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN)
        {
            isUpdating=false;
        }

        if(jcb.getSelectedIndex() == 0)
        {
            zeroIndexSel = true;
        }
        else
        {
            zeroIndexSel = false;
        }

    }


    
    
    
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("ACTION" + ae.getActionCommand() + "   "+ae.paramString() + "   "+ae.getm );
        
        if(ae.getModifiers() == ActionEvent.MOUSE_EVENT_MASK)
        {
            System.out.println("ACION");
            updatelist();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        jcb.setPopupVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
