// ============================================================
// File: CSVVisualizationApp.java
// Purpose: Main application - loads CSV and provides analytics
// ============================================================

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class CSVVisualizationApp extends JFrame {
    private JTabbedPane tabbedPane;
    private DataSet dataSet;
    private DataTablePanel tablePanel;
    private StatisticsPanel statisticsPanel;
    private ChartVisualizationPanel chartPanel;
    private JComboBox<String> columnSelector;
    private JLabel statusLabel;
    
    // Constructor
    public CSVVisualizationApp() {
        setTitle("Data Visualization & Analytics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Create menu bar
        createMenuBar();
        
        // Create main content area
        tabbedPane = new JTabbedPane();
        
        // Status bar
        statusLabel = new JLabel("Ready to load CSV file...");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    // Create menu bar
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem openItem = new JMenuItem("Open CSV File");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCSVFile();
            }
        });
        fileMenu.add(openItem);
        
        fileMenu.addSeparator();
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    // Open CSV file dialog
    private void openCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "CSV Files", "csv");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadCSVData(selectedFile.getAbsolutePath());
        }
    }
    
    // Load CSV data and initialize panels
    private void loadCSVData(String filePath) {
        try {
            statusLabel.setText("Loading file: " + filePath + "...");
            
            // Read CSV file
            CSVReader reader = new CSVReader(filePath);
            List<String[]> csvData = reader.readCSV();
            
            if (csvData.isEmpty()) {
                statusLabel.setText("Error: CSV file is empty");
                return;
            }
            
            // Initialize DataSet
            dataSet = new DataSet();
            
            // Set headers (first row)
            dataSet.setHeaders(csvData.get(0));
            
            // Add data rows
            for (int i = 1; i < csvData.size(); i++) {
                dataSet.addRow(csvData.get(i));
            }
            
            // Clear existing tabs
            tabbedPane.removeAll();
            
            // Create new panels
            tablePanel = new DataTablePanel(dataSet);
            statisticsPanel = new StatisticsPanel(dataSet);
            
            // Find numeric column for chart
            String numericColumn = dataSet.getColumnNames().get(0);
            for (String col : dataSet.getColumnNames()) {
                List<Double> numData = dataSet.getColumnAsNumbers(col);
                if (!numData.isEmpty()) {
                    numericColumn = col;
                    break;
                }
            }
            
            // Create chart panel
            String categoryColumn = dataSet.getColumnNames().get(0);
            chartPanel = new ChartVisualizationPanel(dataSet, categoryColumn, numericColumn);
            
            // Add tabs
            tabbedPane.addTab("Data Table", tablePanel);
            tabbedPane.addTab("Statistics", statisticsPanel);
            tabbedPane.addTab("Charts", createChartControlPanel());
            
            statusLabel.setText("Successfully loaded: " + filePath + 
                              " (Rows: " + dataSet.getRowCount() + 
                              ", Columns: " + dataSet.getColumnCount() + ")");
            
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error loading CSV: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Create chart control panel
    private JPanel createChartControlPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(new Color(220, 220, 220));
        
        // Chart type selector
        JLabel chartTypeLabel = new JLabel("Chart Type:");
        String[] chartTypes = {"Bar Chart", "Line Chart", "Pie Chart"};
        JComboBox<String> chartTypeSelector = new JComboBox<>(chartTypes);
        chartTypeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = chartTypeSelector.getSelectedIndex();
                chartPanel.setChartType(index);
            }
        });
        
        controlPanel.add(chartTypeLabel);
        controlPanel.add(chartTypeSelector);
        
        // Add panels
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CSVVisualizationApp app = new CSVVisualizationApp();
                app.setVisible(true);
            }
        });
    }
}
