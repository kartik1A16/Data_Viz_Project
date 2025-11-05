// ============================================================
// File: DataSet.java
// Purpose: Generic container for tabular data with columns and rows
// ============================================================

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSet {
    private List<String> columnNames;
    private List<List<String>> rows;
    private Map<String, Integer> columnIndexMap;
    
    // Constructor
    public DataSet() {
        this.columnNames = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.columnIndexMap = new HashMap<>();
    }
    
    // Set column headers
    public void setHeaders(String[] headers) {
        columnNames.clear();
        columnIndexMap.clear();
        
        for (int i = 0; i < headers.length; i++) {
            columnNames.add(headers[i]);
            columnIndexMap.put(headers[i], i);
        }
    }
    
    // Add a row of data
    public void addRow(String[] rowData) {
        List<String> row = new ArrayList<>();
        for (String value : rowData) {
            row.add(value);
        }
        rows.add(row);
    }
    
    // Get number of rows
    public int getRowCount() {
        return rows.size();
    }
    
    // Get number of columns
    public int getColumnCount() {
        return columnNames.size();
    }
    
    // Get all column names
    public List<String> getColumnNames() {
        return new ArrayList<>(columnNames);
    }
    
    // Get value at specific row and column
    public String getValue(int row, int column) {
        if (row >= 0 && row < rows.size() && column >= 0 && column < columnNames.size()) {
            return rows.get(row).get(column);
        }
        return "";
    }
    
    // Get value by column name
    public String getValue(int row, String columnName) {
        if (columnIndexMap.containsKey(columnName)) {
            int columnIndex = columnIndexMap.get(columnName);
            return getValue(row, columnIndex);
        }
        return "";
    }
    
    // Get entire row
    public String[] getRow(int row) {
        if (row >= 0 && row < rows.size()) {
            List<String> rowList = rows.get(row);
            return rowList.toArray(new String[0]);
        }
        return new String[0];
    }
    
    // Get all values in a column
    public List<String> getColumn(String columnName) {
        List<String> columnData = new ArrayList<>();
        if (columnIndexMap.containsKey(columnName)) {
            int columnIndex = columnIndexMap.get(columnName);
            for (List<String> row : rows) {
                if (columnIndex < row.size()) {
                    columnData.add(row.get(columnIndex));
                }
            }
        }
        return columnData;
    }
    
    // Get column as numeric values (convert strings to doubles)
    public List<Double> getColumnAsNumbers(String columnName) {
        List<Double> numericData = new ArrayList<>();
        List<String> stringData = getColumn(columnName);
        
        for (String value : stringData) {
            try {
                double numValue = Double.parseDouble(value);
                numericData.add(numValue);
            } catch (NumberFormatException e) {
                // Skip non-numeric values
            }
        }
        return numericData;
    }
    
    // Get all rows as 2D array
    public String[][] getAllData() {
        String[][] data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            data[i] = row.toArray(new String[0]);
        }
        return data;
    }
    
    // Find column index by name
    public int getColumnIndex(String columnName) {
        return columnIndexMap.getOrDefault(columnName, -1);
    }
    
    // Filter rows by condition (value in specific column equals target)
    public DataSet filterByColumn(String columnName, String targetValue) {
        DataSet filtered = new DataSet();
        filtered.setHeaders(columnNames.toArray(new String[0]));
        
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex != -1) {
            for (List<String> row : rows) {
                if (columnIndex < row.size() && row.get(columnIndex).equals(targetValue)) {
                    filtered.addRow(row.toArray(new String[0]));
                }
            }
        }
        return filtered;
    }
    
    // Sort rows by numeric column
    public void sortByColumn(String columnName, boolean ascending) {
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex == -1) return;
        
        final int colIndex = columnIndex;
        final boolean asc = ascending;
        
        rows.sort((row1, row2) -> {
            try {
                double val1 = Double.parseDouble(row1.get(colIndex));
                double val2 = Double.parseDouble(row2.get(colIndex));
                
                int result = Double.compare(val1, val2);
                return asc ? result : -result;
            } catch (NumberFormatException e) {
                return 0;
            }
        });
    }
    
    // Get data summary
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Dataset Summary:\n");
        summary.append("Rows: ").append(getRowCount()).append("\n");
        summary.append("Columns: ").append(getColumnCount()).append("\n");
        summary.append("Headers: ").append(columnNames).append("\n");
        return summary.toString();
    }
}
