// ============================================================
// File: StatisticsPanel.java
// Purpose: Display statistical analysis results
// ============================================================

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private DataSet dataSet;
    private JTextArea statisticsText;
    private JComboBox<String> columnSelector;
    
    // Constructor
    public StatisticsPanel(DataSet dataSet) {
        this.dataSet = dataSet;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Create control panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // Create statistics display area
        statisticsText = new JTextArea();
        statisticsText.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statisticsText.setEditable(false);
        statisticsText.setBackground(new Color(240, 240, 240));
        statisticsText.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(statisticsText);
        add(scrollPane, BorderLayout.CENTER);
        
        // Display initial statistics
        displayStatistics();
    }
    
    // Create control panel with column selector
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(220, 220, 220));
        
        panel.add(new JLabel("Select Column:"));
        
        // Populate column selector
        columnSelector = new JComboBox<>();
        for (String columnName : dataSet.getColumnNames()) {
            columnSelector.addItem(columnName);
        }
        
        // Add listener to update statistics when column changes
        columnSelector.addActionListener(e -> displayStatistics());
        
        panel.add(columnSelector);
        return panel;
    }
    
    // Display statistics for selected column
    private void displayStatistics() {
        String selectedColumn = (String) columnSelector.getSelectedItem();
        
        if (selectedColumn == null) {
            statisticsText.setText("No columns available");
            return;
        }
        
        try {
            // Get numeric data from selected column
            java.util.List<Double> numericData = dataSet.getColumnAsNumbers(selectedColumn);
            
            if (numericData.isEmpty()) {
                statisticsText.setText("No numeric data in column: " + selectedColumn);
                return;
            }
            
            // Calculate statistics
            StatisticsCalculator calc = new StatisticsCalculator(numericData, selectedColumn);
            
            // Build report
            StringBuilder report = new StringBuilder();
            report.append("╔════════════════════════════════════════════╗\n");
            report.append("║     Statistical Analysis Report             ║\n");
            report.append("╚════════════════════════════════════════════╝\n\n");
            
            report.append("Column: ").append(selectedColumn).append("\n");
            report.append("Data Points: ").append(calc.getCount()).append("\n\n");
            
            report.append("┌─ Measures of Central Tendency ─────────────┐\n");
            report.append(String.format("│ Mean (Average):        %10.2f          │\n", calc.getMean()));
            report.append(String.format("│ Median (Middle):       %10.2f          │\n", calc.getMedian()));
            report.append(String.format("│ Mode (Most Frequent):  %10.2f          │\n", calc.getMode()));
            report.append("└────────────────────────────────────────────┘\n\n");
            
            report.append("┌─ Measures of Dispersion ──────────────────┐\n");
            report.append(String.format("│ Standard Deviation:    %10.2f          │\n", calc.getStandardDeviation()));
            report.append(String.format("│ Range:                 %10.2f          │\n", calc.getRange()));
            report.append(String.format("│ Minimum:               %10.2f          │\n", calc.getMin()));
            report.append(String.format("│ Maximum:               %10.2f          │\n", calc.getMax()));
            report.append("└────────────────────────────────────────────┘\n\n");
            
            report.append("┌─ Additional Statistics ────────────────────┐\n");
            report.append(String.format("│ Sum:                   %10.2f          │\n", calc.getSum()));
            double[] quartiles = calc.getQuartiles();
            report.append(String.format("│ Q1 (25th percentile):  %10.2f          │\n", quartiles[0]));
            report.append(String.format("│ Q3 (75th percentile):  %10.2f          │\n", quartiles[2]));
            report.append("└────────────────────────────────────────────┘\n");
            
            statisticsText.setText(report.toString());
            
        } catch (Exception e) {
            statisticsText.setText("Error calculating statistics: " + e.getMessage());
        }
    }
}
