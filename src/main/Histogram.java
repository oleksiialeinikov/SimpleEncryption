package main;


import java.util.ArrayList;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author user
 */
public class Histogram {
    
    
    public static JFreeChart getChart(Map<Character,Double> map, String category){
        
        ArrayList<Map<Character,Double>> maps = new ArrayList<>();
        maps.add(map);
        
        ArrayList<String> categories = new ArrayList<>();
        categories.add(category);        
        
        return getChart(maps,categories);
    }
    
    public  static JFreeChart getChart(
      ArrayList<Map<Character,Double>> maps, ArrayList<String> categories){
    
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(int i=0;i<maps.size();i++)
        for(Map.Entry<Character,Double> e: maps.get(i).entrySet())
            dataset.addValue(e.getValue(), categories.get(i), e.getKey());

        final JFreeChart chart = ChartFactory.createBarChart(
            "Char frequencies histogram",         // chart title
            "Char",               // domain axis label
            "Frequency,%",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
        return chart;
    }
}
