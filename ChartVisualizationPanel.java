// ============================================================
// File: ChartVisualizationPanel.java
// Purpose: Display multiple chart types (bar, line, pie)
// ============================================================

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartVisualizationPanel extends JPanel {
    private DataSet dataSet;
    private String xAxisColumn;
    private String yAxisColumn;
    private int chartType;  // 0=Bar, 1=Line, 2=Pie
    
    private final int BAR_CHART = 0;
    private final int LINE_CHART = 1;
    private final int PIE_CHART = 2;
    
    private int padding = 50;
    private int labelPadding = 25;
    
    // Constructor
    public ChartVisualizationPanel(DataSet dataSet, String xAxis, String yAxis) {
        this.dataSet = dataSet;
        this.xAxisColumn = xAxis;
        this.yAxisColumn = yAxis;
        this.chartType = BAR_CHART;
        setBackground(Color.WHITE);
    }
    
    // Set chart type
    public void setChartType(int type) {
        this.chartType = type;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        switch (chartType) {
            case BAR_CHART:
                drawBarChart(g2d, width, height);
                break;
            case LINE_CHART:
                drawLineChart(g2d, width, height);
                break;
            case PIE_CHART:
                drawPieChart(g2d, width, height);
                break;
        }
    }
    
    // Draw bar chart
    private void drawBarChart(Graphics2D g2d, int width, int height) {
        List<Double> yValues = dataSet.getColumnAsNumbers(yAxisColumn);
        List<String> xValues = dataSet.getColumn(xAxisColumn);
        
        if (yValues.isEmpty()) {
            g2d.drawString("No data to display", width / 2 - 50, height / 2);
            return;
        }
        
        // Find max value for scaling
        double maxValue = 0;
        for (double val : yValues) {
            if (val > maxValue) maxValue = val;
        }
        
        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String title = "Bar Chart: " + yAxisColumn + " by " + xAxisColumn;
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, padding - 10);
        
        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawLine(padding, padding, padding, height - padding - labelPadding);
        g2d.drawLine(padding, height - padding - labelPadding, width - padding, height - padding - labelPadding);
        
        // Draw bars
        int chartWidth = width - padding * 2;
        int chartHeight = height - padding * 2 - labelPadding;
        int barWidth = chartWidth / yValues.size();
        
        for (int i = 0; i < yValues.size(); i++) {
            double value = yValues.get(i);
            int barHeight = (int) ((value / maxValue) * chartHeight);
            int x = padding + i * barWidth + 5;
            int y = height - padding - labelPadding - barHeight;
            
            // Draw bar
            g2d.setColor(new Color(70, 130, 180));
            g2d.fillRect(x, y, barWidth - 10, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth - 10, barHeight);
            
            // Draw value label
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.drawString(String.format("%.0f", value), x + 5, y - 5);
            
            // Draw category label
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            if (i < xValues.size()) {
                g2d.drawString(xValues.get(i), x + 5, height - padding - labelPadding + 20);
            }
        }
    }
    
    // Draw line chart
    private void drawLineChart(Graphics2D g2d, int width, int height) {
        List<Double> yValues = dataSet.getColumnAsNumbers(yAxisColumn);
        List<String> xValues = dataSet.getColumn(xAxisColumn);
        
        if (yValues.size() < 2) {
            g2d.drawString("Insufficient data for line chart", width / 2 - 100, height / 2);
            return;
        }
        
        // Find max value
        double maxValue = 0;
        for (double val : yValues) {
            if (val > maxValue) maxValue = val;
        }
        
        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String title = "Line Chart: " + yAxisColumn + " over time";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, padding - 10);
        
        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawLine(padding, padding, padding, height - padding - labelPadding);
        g2d.drawLine(padding, height - padding - labelPadding, width - padding, height - padding - labelPadding);
        
        int chartWidth = width - padding * 2;
        int chartHeight = height - padding * 2 - labelPadding;
        
        // Draw line
        g2d.setColor(new Color(220, 20, 60));
        g2d.setStroke(new BasicStroke(2.5f));
        
        for (int i = 0; i < yValues.size() - 1; i++) {
            int x1 = padding + (i * chartWidth) / (yValues.size() - 1);
            int y1 = height - padding - labelPadding - 
                    (int) ((yValues.get(i) / maxValue) * chartHeight);
            
            int x2 = padding + ((i + 1) * chartWidth) / (yValues.size() - 1);
            int y2 = height - padding - labelPadding - 
                    (int) ((yValues.get(i + 1) / maxValue) * chartHeight);
            
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        // Draw data points
        g2d.setColor(new Color(0, 100, 200));
        for (int i = 0; i < yValues.size(); i++) {
            int x = padding + (i * chartWidth) / (yValues.size() - 1);
            int y = height - padding - labelPadding - 
                   (int) ((yValues.get(i) / maxValue) * chartHeight);
            
            g2d.fillOval(x - 4, y - 4, 8, 8);
        }
    }
    
    // Draw pie chart
    private void drawPieChart(Graphics2D g2d, int width, int height) {
        List<Double> values = dataSet.getColumnAsNumbers(yAxisColumn);
        List<String> labels = dataSet.getColumn(xAxisColumn);
        
        if (values.isEmpty()) {
            g2d.drawString("No data to display", width / 2 - 50, height / 2);
            return;
        }
        
        // Calculate total
        double total = 0;
        for (double val : values) {
            total += val;
        }
        
        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String title = "Pie Chart: Distribution of " + yAxisColumn;
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, padding);
        
        // Pie chart dimensions
        int centerX = (width - 150) / 2;
        int centerY = (height + padding) / 2;
        int diameter = Math.min(width - 200, height - 150);
        
        // Colors for pie slices
        Color[] colors = {new Color(255, 0, 0), new Color(0, 0, 255), 
                         new Color(0, 128, 0), new Color(255, 165, 0),
                         new Color(128, 0, 128)};
        
        int startAngle = 0;
        for (int i = 0; i < values.size(); i++) {
            int arcAngle = (int) (360.0 * values.get(i) / total);
            
            g2d.setColor(colors[i % colors.length]);
            g2d.fillArc(centerX, centerY, diameter, diameter, startAngle, arcAngle);
            g2d.setColor(Color.BLACK);
            g2d.drawArc(centerX, centerY, diameter, diameter, startAngle, arcAngle);
            
            startAngle += arcAngle;
        }
    }
}
