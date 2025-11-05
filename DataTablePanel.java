// ============================================================
// File: DataTablePanel.java
// Purpose: Display data in tabular format
// ============================================================

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataTablePanel extends JPanel {
    private DataSet dataSet;
    private JTable table;
    private JScrollPane scrollPane;
    
    // Constructor
    public DataTablePanel(DataSet dataSet) {
        this.dataSet = dataSet;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Create table from dataset
        createTable();
        
        // Add table to panel
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Create and populate JTable
    private void createTable() {
        // Prepare table model
        DefaultTableModel model = new DefaultTableModel();
        
        // Add column headers
        for (String columnName : dataSet.getColumnNames()) {
            model.addColumn(columnName);
        }
        
        // Add rows
        String[][] allData = dataSet.getAllData();
        for (String[] row : allData) {
            model.addRow(row);
        }
        
        // Create table
        table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(200, 200, 200));
        table.setRowHeight(25);
        
        // Header styling
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add scroll pane
        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
