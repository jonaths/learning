/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

/**
 *
 * @author jonathan
 */
public class Plots {
    
    public static void plotSimpleLineXY(ArrayList<double[][]> data, ArrayList<String> seriesName, String title, String xname, String yname){
        DefaultXYDataset ds = new DefaultXYDataset();
        
        for(int i = 0; i<data.size(); i++){
            System.out.println(data.get(i)[0][0]);
            ds.addSeries(seriesName.get(i), data.get(i));
        }

        JFreeChart chart
                = ChartFactory.createXYLineChart(title,
                        xname, yname, ds, PlotOrientation.VERTICAL, true, true,
                        false);
        ChartFrame frame = new ChartFrame(title, chart);
        frame.pack();
        frame.setVisible(true);
    }
    
}
