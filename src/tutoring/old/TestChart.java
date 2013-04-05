package tutoring.old;


import tutoring.old.BarChartTest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
public class TestChart {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          ChartTest demo = new ChartTest("Comparison", "Which operating system are you using?");
          demo.pack();
          demo.setVisible(true);
          
          /*
         BarChartTest demo2 = new BarChartTest("Bar Chart Demo");
        demo2.pack();
        //RefineryUtilities.centerFrameOnScreen(demo2);
        demo2.setVisible(true);*/
    }
}
