/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.JTextComponent;
import tutoring.entity.Client;

/**
 *
 * @author Nathaniel
 */
public class UltimateAutoCompleteClient implements KeyListener, ActionListener, MouseListener, ItemListener
{
    private ArrayList<ArrayList<String>> keywords;
    private JComboBox[] boxes;
    private boolean[] isUpdating;
    private boolean[] zeroIndexSel;
    private boolean[] firstClick;
    private int[] lastSize;
    ArrayList<Client> clientsFirst;
    ArrayList<Client> clientsLast;
    ArrayList<Client> clientsEmail;
    ArrayList<Client> clientsPhone;
    ArrayList<ArrayList<String>> matches = new ArrayList<ArrayList<String>>();
    
    private ArrayList<Integer> activeBoxIndexes;

    public UltimateAutoCompleteClient(ArrayList<ArrayList<String>>keywords, JComboBox[] boxes, ArrayList<Client> clientsFirst, ArrayList<Client> clientsLast, ArrayList<Client> clientsPhone, ArrayList<Client> clientsEmail ) {
        this.keywords = keywords;
        this.boxes = boxes;
        this.clientsFirst = clientsFirst;
        this.clientsLast = clientsLast;
        this.clientsPhone = clientsPhone;
        this.clientsEmail = clientsEmail;
        
        isUpdating = new boolean[boxes.length];
        zeroIndexSel = new boolean[boxes.length];
        firstClick = new boolean[boxes.length];
        lastSize = new int[boxes.length];
        
        //this.jcb = jcb;
        
        for(int i=0; i<boxes.length; i++)
        {
           // Arrays.sort(keywords[i]);
            isUpdating[i]=false;
            zeroIndexSel[i]=false;
            firstClick[i]=true;
            lastSize[i]=0;
            boxes[i].setEditable(true);
            

            matches.add(new ArrayList<String>());
            boxes[i].getEditor().getEditorComponent().addKeyListener(this);
            boxes[i].getEditor().getEditorComponent().addMouseListener(this);

            addPopupMouseListener(boxes[i]);
            boxes[i].addActionListener(this);

            boxes[i].setMaximumRowCount(5);
            
        }
        for(int i=0; i<boxes.length; i++)
            updatelist(i, false);
    } 
    
    
    
    
    
 
    
    
    
    
    /*
    
    public void updateOther(int activeBoxIndex)
    {
        if(activeBoxIndex == 0)// && !firstClick[activeBoxIndex])
        {
            System.out.println("UPDATE LAST");
            int index = keywords.get(activeBoxIndex).indexOf(((MutableComboBoxModel)boxes[activeBoxIndex].getModel()).getSelectedItem());
            if(index != -1)
            {
                String text = ((JTextComponent)boxes[1].getEditor().getEditorComponent()).getText();
                if(!text.equals(clientsFirst.get(index).getlName()+""))
                {
                    ((JTextComponent)boxes[1].getEditor().getEditorComponent()).setText(clientsFirst.get(index).getlName()+"");
                    System.out.println("UPDATED Last with "+clientsLast.get(index).getlName());
                    updatelist(1, true);
                }
                else
                    System.out.println("NO NEED");
            }
        }
        if(activeBoxIndex == 1)// && !firstClick[activeBoxIndex])
        {
            System.out.println("UPDATE FIrSt");
            int index = keywords.get(activeBoxIndex).indexOf(((MutableComboBoxModel)boxes[activeBoxIndex].getModel()).getSelectedItem());
            if(index != -1)
            {
                String text = ((JTextComponent)boxes[0].getEditor().getEditorComponent()).getText();
                if(!text.equals(clientsFirst.get(index).getfName()+""))
                {
                    ((JTextComponent)boxes[0].getEditor().getEditorComponent()).setText(clientsLast.get(index).getfName()+"");
                    System.out.println("UPDATED FIrSt with "+clientsLast.get(index).getfName());
                    updatelist(0, true);
                }
                else
                    System.out.println("NO NEED");
            }
        }
        
    }*/
    
    public void updatelist(int activeBoxIndex, boolean updatedOtherBoxes)
    {
        boolean moreChars;

        String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
        if(text.length() > lastSize[activeBoxIndex])
            moreChars = true;
        else
            moreChars = false;
        
        
        
        lastSize[activeBoxIndex] = text.length();
        //mcbm.
        MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[activeBoxIndex].getModel();
        
        System.out.println("TEXT IN UPDATELIST: "+text);
        
       
        //vect = new Vector<String>();
 
        if(updatedOtherBoxes)
        {

            System.out.println("MORE CHARS");
            int max = matches.get(activeBoxIndex).size();
            Object[] values = matches.get(activeBoxIndex).toArray();

            //ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
            for(int i=0; i<max; i++)
            {
                if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                {

                    mcbm.removeElement(((String)values[i]));
                    //indexesToRemove.add(i);
                    matches.get(activeBoxIndex).remove(((String)values[i]));
                   // System.out.println("CONTAINS: "+keywords[i]);
                }   
            }

            for(int i=0; i<keywords.get(activeBoxIndex).size(); i++)
            {
                if(!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                {
                    //matches.add(keywords[i]);
                    mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                    matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));

                   // System.out.println("CONTAINS: "+keywords[i]);
                }

            }
            
        }
        else
        {
            if(moreChars)
            {
                System.out.println("MORE CHARS");
                int max = matches.get(activeBoxIndex).size();
                Object[] values = matches.get(activeBoxIndex).toArray();
                
                //ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
                for(int i=0; i<max; i++)
                {
                    if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                    {

                        mcbm.removeElement(((String)values[i]));
                        //indexesToRemove.add(i);
                        matches.get(activeBoxIndex).remove(((String)values[i]));
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
                for(int i=0; i<keywords.get(activeBoxIndex).size(); i++)
                {
                    if(!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                    {
                        //matches.add(keywords[i]);
                        mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                        matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));

                       // System.out.println("CONTAINS: "+keywords[i]);
                    }
            
                }
            }
        }
            
            
       // System.out.println("SIZE: "+mcbm.getSize());
      
        
    //System.out.println("Match is: "+matches.get(activeBoxIndex).get(0));
    //System.out.println("Actual is: "+mcbm.getSelectedItem() + " OR "+mcbm.getElementAt(0));
  
   //// if(!updatedOtherBoxes)
   ////     updateOther(activeBoxIndex);
      
    if(!firstClick[activeBoxIndex] && mcbm.getSize() > 0)
        updateOtherList(activeBoxIndex, mcbm.getSelectedItem().toString());
    
        //jcb.setMaximumRowCount(mcbm.getSize());
    if(!firstClick[activeBoxIndex] && mcbm.getSize()>0)
            boxes[activeBoxIndex].setSelectedIndex(0);
        else
            System.out.println("FIRST CLICK OR MCBM <=0");
        //jcb.setMaximumRowCount(5);
        
       // jcb.setLightWeightPopupEnabled(false);
        
        
        
    }
    
    
    public void updateOtherList(int activeBoxIndex, String value)
    {
        ////boolean moreChars;

       //// String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
       // if(text.length() > lastSize[activeBoxIndex])
       //     moreChars = true;
       // else
       //     moreChars = false;
        
        
        
       // lastSize[activeBoxIndex] = text.length();
        //mcbm.
        
        
       // System.out.println("TEXT IN UPDATELIST: "+text);
        
       
        //vect = new Vector<String>();

        for(int i=0; i<boxes.length; i++)
        { 
            MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[i].getModel();
            
            if(i != activeBoxIndex)
            {
                System.out.println("MORE CHARS");
                int max = matches.get(i).size();
                Object[] values = matches.get(i).toArray();

                System.out.println("UPDATE LAST");
                int index = keywords.get(activeBoxIndex).indexOf(value);
                //System.out.println("initial value: "+keywords.get(activeBoxIndex).get(index));
                if(index != -1)
                {
                    String otherText = ((JTextComponent)boxes[i].getEditor().getEditorComponent()).getText();
                    
                    if(activeBoxIndex == 0)
                    {
                        if(!otherText.equals(clientsFirst.get(index).getlName()+""))
                        {
                            for(int j=0; j<max; j++)
                            {
                                mcbm.removeElement(((String)values[j]));
                                //indexesToRemove.add(i);
                                matches.get(i).remove(((String)values[j]));
                               // System.out.println("CONTAINS: "+keywords[i]);
                            }

                            ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText(clientsFirst.get(index).getlName()+"");
                            System.out.println("UPDATED Last in other with "+clientsFirst.get(index).getlName());

                            boolean moreResults = true;
                            int j = index-1;
                            String find = value;
                            while(moreResults)
                            {

                                j++;
                               // System.out.println("IN THE LOOP"+i+ "  " + keywords.get(activeBoxIndex).get(i) + "    "+find);
                                if(j < keywords.get(activeBoxIndex).size() && keywords.get(activeBoxIndex).get(j).equals(find))
                                {
                                    System.out.println("FOUND AND ADDED: "+clientsFirst.get(j).getlName());
                                    mcbm.addElement(clientsFirst.get(j).getlName()+"");

                                    matches.get(i).add(clientsFirst.get(j).getlName()+"");
                                }
                                else
                                    moreResults = false;
                            }

                            boxes[i].setSelectedIndex(0);//mcbm.setSelectedItem(0);

                            //updatelist(1, true);
                        }
                        else
                            System.out.println("NO NEED");
                    }
                    else
                    {
                        if(!otherText.equals(clientsLast.get(index).getfName()+""))
                        {
                            for(int j=0; j<max; j++)
                            {
                                mcbm.removeElement(((String)values[j]));
                                //indexesToRemove.add(i);
                                matches.get(i).remove(((String)values[j]));
                               // System.out.println("CONTAINS: "+keywords[i]);
                            }

                            ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText(clientsLast.get(index).getfName()+"");
                            System.out.println("UPDATED Last in other with "+clientsLast.get(index).getfName());

                            boolean moreResults = true;
                            int j = index-1;
                            String find = value;
                            while(moreResults)
                            {

                                j++;
                               // System.out.println("IN THE LOOP"+i+ "  " + keywords.get(activeBoxIndex).get(i) + "    "+find);
                                if(j < keywords.get(activeBoxIndex).size() && keywords.get(activeBoxIndex).get(j).equals(find))
                                {
                                    System.out.println("FOUND AND ADDED: "+clientsLast.get(j).getfName());
                                    mcbm.addElement(clientsLast.get(j).getfName()+"");

                                    matches.get(i).add(clientsLast.get(j).getfName()+"");
                                }
                                else
                                    moreResults = false;
                            }

                            boxes[i].setSelectedIndex(0);//mcbm.setSelectedItem(0);

                            //updatelist(1, true);
                        }
                        else
                            System.out.println("NO NEED");
                    }
                }
            }
        }
        
        
        
        
/*
        //ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();
        for(int i=0; i<max; i++)
        {
            if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
            {
                mcbm.removeElement(((String)values[i]));
                //indexesToRemove.add(i);
                matches.get(activeBoxIndex).remove(((String)values[i]));
               // System.out.println("CONTAINS: "+keywords[i]);
            }   
        }

        for(int i=0; i<keywords.get(activeBoxIndex).size(); i++)
        {
            if(!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
            {
                //matches.add(keywords[i]);
                mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));

               // System.out.println("CONTAINS: "+keywords[i]);
            }

        }*/
            
            /*
       
    if(!updatedOtherBoxes)
        updateOther(activeBoxIndex);
    */
        //jcb.setMaximumRowCount(mcbm.getSize());
    
        /*
       if(!firstClick[activeBoxIndex] && mcbm.getSize()>0)
            boxes[activeBoxIndex].setSelectedIndex(0);
        else
            System.out.println("FIRST CLICK OR MCBM <=0");*/
        //jcb.setMaximumRowCount(5);
        
       // jcb.setLightWeightPopupEnabled(false);
        
        
        
    }
    
    public void updateOtherListNew(String value)
    {
        for(int i=0; i<boxes.length; i++)
        { 
            MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[i].getModel();
            
            if(!activeBoxIndexes.contains(i))//ns
            {
                System.out.println("MORE CHARS");
                int max = matches.get(i).size();
                Object[] values = matches.get(i).toArray();

                System.out.println("UPDATE LAST");
                ArrayList<Integer> indexesOfValue;
                for(int j=0; j<activeBoxIndexes.size(); j++)
                {
                    indexesOfValue.add(keywords.get(activeBoxIndexes.get(j)).indexOf(value));
                }
                
                
                //int index = keywords.get(activeBoxIndex).indexOf(value);
                //System.out.println("initial value: "+keywords.get(activeBoxIndex).get(index));
                if(index != -1)
                {
                    String otherText = ((JTextComponent)boxes[i].getEditor().getEditorComponent()).getText();
                    if(activeBoxIndex == 0)
                    {
                        if(!otherText.equals(clientsFirst.get(index).getlName()+""))
                        {
                            for(int j=0; j<max; j++)
                            {
                                mcbm.removeElement(((String)values[j]));
                                //indexesToRemove.add(i);
                                matches.get(i).remove(((String)values[j]));
                               // System.out.println("CONTAINS: "+keywords[i]);
                            }

                            ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText(clientsFirst.get(index).getlName()+"");
                            System.out.println("UPDATED Last in other with "+clientsFirst.get(index).getlName());

                            boolean moreResults = true;
                            int j = index-1;
                            String find = value;
                            while(moreResults)
                            {

                                j++;
                               // System.out.println("IN THE LOOP"+i+ "  " + keywords.get(activeBoxIndex).get(i) + "    "+find);
                                if(j < keywords.get(activeBoxIndex).size() && keywords.get(activeBoxIndex).get(j).equals(find))
                                {
                                    System.out.println("FOUND AND ADDED: "+clientsFirst.get(j).getlName());
                                    mcbm.addElement(clientsFirst.get(j).getlName()+"");

                                    matches.get(i).add(clientsFirst.get(j).getlName()+"");
                                }
                                else
                                    moreResults = false;
                            }

                            boxes[i].setSelectedIndex(0);//mcbm.setSelectedItem(0);

                            //updatelist(1, true);
                        }
                        else
                            System.out.println("NO NEED");
                    }
                    else
                    {
                        if(!otherText.equals(clientsLast.get(index).getfName()+""))
                        {
                            for(int j=0; j<max; j++)
                            {
                                mcbm.removeElement(((String)values[j]));
                                //indexesToRemove.add(i);
                                matches.get(i).remove(((String)values[j]));
                               // System.out.println("CONTAINS: "+keywords[i]);
                            }

                            ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText(clientsLast.get(index).getfName()+"");
                            System.out.println("UPDATED Last in other with "+clientsLast.get(index).getfName());

                            boolean moreResults = true;
                            int j = index-1;
                            String find = value;
                            while(moreResults)
                            {

                                j++;
                               // System.out.println("IN THE LOOP"+i+ "  " + keywords.get(activeBoxIndex).get(i) + "    "+find);
                                if(j < keywords.get(activeBoxIndex).size() && keywords.get(activeBoxIndex).get(j).equals(find))
                                {
                                    System.out.println("FOUND AND ADDED: "+clientsLast.get(j).getfName());
                                    mcbm.addElement(clientsLast.get(j).getfName()+"");

                                    matches.get(i).add(clientsLast.get(j).getfName()+"");
                                }
                                else
                                    moreResults = false;
                            }

                            boxes[i].setSelectedIndex(0);//mcbm.setSelectedItem(0);

                            //updatelist(1, true);
                        }
                        else
                            System.out.println("NO NEED");
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) 
    {
        int activeBoxIndex = -1;
        
        
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i].getEditor().getEditorComponent() == evt.getSource())
            {
                activeBoxIndex = i;
            }
        }
        if(activeBoxIndex != -1)
        {
            int cursorPos = ((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).getCaretPosition();
            
            if(firstClick[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_TAB)
            {
                firstClick[activeBoxIndex]=false;

                ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText((""+evt.getKeyChar()).trim());

            }

            String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();

            System.out.println("CURRENT SELECTION: "+boxes[activeBoxIndex].getSelectedIndex());
            if(boxes[activeBoxIndex].getSelectedIndex() > -1)
                System.out.println("WITH" +boxes[activeBoxIndex].getSelectedItem().toString());
            if (evt.getKeyCode() == KeyEvent.VK_DOWN)
            {
                isUpdating[activeBoxIndex] = true;
                zeroIndexSel[activeBoxIndex] = true;

                boxes[activeBoxIndex].setPopupVisible(true);
                System.out.println("UPDATE OTHER DOWN");
                //////updateOther(activeBoxIndex);
                updateOtherList(activeBoxIndex, text);
            }
            
            
            if(!isUpdating[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                boxes[activeBoxIndex].hidePopup();
                updatelist(activeBoxIndex, false);
                boxes[activeBoxIndex].showPopup();
                ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(text);
                if(((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText().length() >= cursorPos)
                    ((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).setCaretPosition(cursorPos);

                int size = boxes[activeBoxIndex].getModel().getSize();

                if(size == 0)
                {

                    System.out.println("SIZE 0;;");

                    boxes[activeBoxIndex].hidePopup();
                }

            }
            else
            {
                if(!isUpdating[activeBoxIndex])
                {
                    boxes[activeBoxIndex].hidePopup();
                }
            }

            if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel[activeBoxIndex])
            {
                System.out.println("UP PRESSED and ZERO INDEX");

                isUpdating[activeBoxIndex]=false;
                //jcb.setPopupVisible(false);
                boxes[activeBoxIndex].hidePopup();
            }
            else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
                String selected = boxes[activeBoxIndex].getSelectedItem().toString();
                updatelist(activeBoxIndex, false);
                System.out.println("ENTER PRESSED");
                if(boxes[activeBoxIndex].getSelectedIndex() >= 0)
                    ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(selected);

                isUpdating[activeBoxIndex]=false;

                System.out.println("LDSJLKFJSDLFJ: "+boxes[activeBoxIndex].getSelectedItem().toString());
                //jcb.setPopupVisible(false);
                boxes[activeBoxIndex].hidePopup();
                
                //try
                System.out.println("UPDATE OTHER ENTER");
               /////// updateOther(activeBoxIndex);
                updateOtherList(activeBoxIndex, text);

                //jcb.requestFocusInWindow();
            }

            else if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                isUpdating[activeBoxIndex]=false;
            }

            if(boxes[activeBoxIndex].getSelectedIndex() == 0)
            {
                zeroIndexSel[activeBoxIndex] = true;
            }
            else
            {
                zeroIndexSel[activeBoxIndex] = false;
            }
            
            if(evt.getKeyCode() == KeyEvent.VK_UP)
            {
                System.out.println("UPDATE OTHER UP");
                ////updateOther(activeBoxIndex);
                updateOtherList(activeBoxIndex, text);
            }
            
        }
        else
            System.out.println("ERRROR Type");

    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("ACTION" + ae.getActionCommand() + "   "+ae.paramString() + "   "+ae.getm );
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        System.out.println("MOUSE CLICKED");
        int activeBoxIndex = -1;
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i].getEditor().getEditorComponent() == me.getSource())
                activeBoxIndex = i;
        }
        if(activeBoxIndex != -1)
        {
            boxes[activeBoxIndex].setPopupVisible(true);
        }
        else
            System.out.println("ERRROR mouse click");
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.MOUSE_EVENT_MASK)
            System.out.println("MOUSE MASK");
        else if(e.getStateChange() == ItemEvent.SELECTED)
            System.out.println("SELECTED I");
    }

    
    private void addPopupMouseListener(JComboBox box) {
        try {
                  Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
                popupInBasicComboBoxUI.setAccessible(true);
                BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI());
                
                Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
                scrollerInBasicComboPopup.setAccessible(true);
                JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

                scroller.getViewport().getView().addMouseListener(listener());
//                ((JViewport) ((JScrollPane) ((BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI())).getComponents()[0]).getComponents()[0]).getComponents()[0].addMouseListener(this);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private MouseAdapter listener() {
        return new MouseAdapter(){
            public void mouseClicked(MouseEvent mouseEvent) {
                //System.out.println("mouseClicked");
            }

            public void mousePressed(MouseEvent mouseEvent) {
               // System.out.println("mousePressed");
            }

            public void mouseReleased(MouseEvent mouseEvent) {
                //System.out.println("mouseReleased");
                System.out.println("ACION");    
                int activeBoxIndex = -1;
                for(int i=0; i<boxes.length; i++)
                {
                   try{
                    Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
                popupInBasicComboBoxUI.setAccessible(true);
                BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(boxes[i].getUI());
                Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
                scrollerInBasicComboPopup.setAccessible(true);
                JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

                if(scroller.getViewport().getView() == mouseEvent.getComponent())
                            activeBoxIndex = i;
              
                 }
                    catch (NoSuchFieldException es) {
                        es.printStackTrace();
                    }
                    catch (IllegalAccessException es) {
                        es.printStackTrace();
                    }
                        
                    System.out.println(mouseEvent.getComponent());
                  
                }
                if(activeBoxIndex != -1)
                {

                    //if(.getModifiers() == ActionEvent.MOUSE_EVENT_MASK)
                    //{
                        System.out.println("ACION");
                        updatelist(activeBoxIndex, false);
                       // System.out.println("UPDATE OTHER MOUSE");
                        //updateOther(activeBoxIndex);
                   // }

                }
                else
                    System.out.println("ERRROR action");
            }

            public void mouseEntered(MouseEvent mouseEvent) {
                //System.out.println("mouseEntered");
            }

            public void mouseExited(MouseEvent mouseEvent) {
                //System.out.println("mouseExited");
            }
        };
    }
    
}
