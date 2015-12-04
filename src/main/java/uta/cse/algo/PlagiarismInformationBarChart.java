package uta.cse.algo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
/**
 * Created by ADMIN on 03-12-2015.
 */
public class PlagiarismInformationBarChart extends ApplicationFrame{
    public PlagiarismInformationBarChart(String applicationTitle, String chartTitle){
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(chartTitle,
                "Category",
                "Score",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500,300));
        setContentPane(chartPanel);

    }

    private CategoryDataset createDataset(){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(PerformanceDetails pd : FileController.performanceDetailsList) {
            dataset.addValue(pd.plagarismPercent,pd.algoName,"Plagiarized Percent");
        }
        return dataset;
    }

    public static void buildChart(){
        RunTimeAnalysisBarChart barChart = new RunTimeAnalysisBarChart("Plagiarism Percent Analysis","Comparing Plagiarism Results given by algorithms");
        barChart.pack();
        RefineryUtilities.centerFrameOnScreen(barChart);
        barChart.setVisible(true);
    }
}
