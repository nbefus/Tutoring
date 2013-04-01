package tutoring.old;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class testfilechooser implements Runnable {
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new testfilechooser());
  }

  @Override
  public void run() {
    final MainWindow window = new MainWindow();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}

final class MainWindow extends JFrame {
  private static final long serialVersionUID = 6933840657337621389L;

  int index = 0;

  final String[] lookAndFeels = {
      "javax.swing.plaf.metal.MetalLookAndFeel",
      "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
      "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
  };

  public MainWindow() {
    super("Look and Feel Demo");
    this.setSize(400, 100);
    this.setLayout(new FlowLayout());
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    final JButton button = new JButton("Button");
    final JLabel label = new JLabel("Default Look and Feel");
    this.add(button);
    this.add(label);
    

    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent evt) {
        final MainWindow owner = MainWindow.this;
        try {
          final String lookAndFeel = owner.lookAndFeels[owner.index++ % 3];
          UIManager.setLookAndFeel(lookAndFeel);
          SwingUtilities.updateComponentTreeUI(owner);
          label.setText(lookAndFeel);
          JFileChooser jf = new JFileChooser();
          jf.showSaveDialog(jf);
          
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}