# Advanced CSV Data Visualization & Statistical Analysis Project

## Project Overview

This is a complete Java application for loading CSV files, performing statistical analysis, and creating multiple visualizations including:

- **Data Tables**: Interactive table view of all data
- **Statistical Analysis**: Mean, median, mode, standard deviation, quartiles, etc.
- **Multiple Charts**: Bar charts, line charts, pie charts
- **Flexible Data Handling**: Works with any CSV file format

## Project Architecture

### Class Relationships

```
CSVVisualizationApp (Main Application - JFrame)
    ├── File I/O
    │   └── CSVReader (reads CSV files)
    │
    └── Data Processing
        ├── DataSet (generic data container)
        │   └── Column extraction
        │   └── Numeric conversion
        │
        ├── StatisticsCalculator
        │   ├── Mean, Median, Mode
        │   ├── Standard Deviation
        │   └── Quartiles
        │
        └── Visualization Panels
            ├── DataTablePanel (JTable)
            ├── StatisticsPanel (Text display)
            └── ChartVisualizationPanel
                ├── Bar Chart
                ├── Line Chart
                └── Pie Chart
```

## Class-by-Class Explanation

### 1. CSVReader.java

**Purpose**: Read and parse CSV files from disk

**Key Methods**:

```
readCSV()
  - Opens BufferedReader for efficient file reading
  - Reads line by line using readLine()
  - Parses each line handling quoted fields
  - Returns List<String[]> where each array is a row
  
parseLine(String line)
  - Iterates through characters
  - Handles quotes (toggle inQuotes state)
  - Recognizes delimiter only outside quotes
  - Returns array of field values
  
readCSVSkipHeader()
  - Calls readCSV()
  - Removes first row (assumed to be header)
  - Returns data without header
```

**Exception Handling**:
```
try-catch for IOException
  - FileNotFoundException: File not found
  - IOException: General read errors
  - Rethrows with descriptive message
```

### 2. DataSet.java

**Purpose**: Generic container for tabular data (works with any CSV)

**Key Data Structures**:
```
- List<String> columnNames: Header names
- List<List<String>> rows: All data rows
- Map<String, Integer> columnIndexMap: Fast column lookup
```

**Key Methods**:

```
setHeaders(String[] headers)
  - Populates columnNames from first row
  - Builds columnIndexMap for O(1) lookup
  
addRow(String[] rowData)
  - Converts array to List<String>
  - Adds to rows collection
  
getValue(int row, String columnName)
  - Uses columnIndexMap to find column index
  - Returns value at intersection
  
getColumnAsNumbers(String columnName)
  - Extracts column as strings
  - Attempts Double.parseDouble() on each
  - Skips non-numeric values with try-catch
  - Returns List<Double> for statistics
  
filterByColumn(String columnName, String value)
  - Creates new DataSet
  - Copies header
  - Adds only matching rows
  - Returns filtered dataset
  
sortByColumn(String columnName, boolean ascending)
  - Uses List.sort() with custom comparator
  - Parses values as doubles
  - Compares numerically
```

### 3. StatisticsCalculator.java

**Purpose**: Perform statistical analysis

**Statistical Formulas Implemented**:

```
Mean = Sum of all values / Count of values

Median:
  - Sort data
  - If even count: average of two middle values
  - If odd count: middle value

Mode:
  - Count frequency of each value (HashMap)
  - Return most frequent value

Standard Deviation:
  - Calculate mean
  - For each value: (value - mean)²
  - Sum all squared differences
  - Divide by count: variance
  - Square root of variance: std dev

Quartiles:
  - Q1: 25th percentile (sort[n/4])
  - Q2: 50th percentile (median)
  - Q3: 75th percentile (sort[3n/4])
```

**Key Methods**:

```
getMean()
  - Loop through all values
  - Accumulate sum
  - Divide by size
  - Return average

getMedian()
  - Create sorted copy of data
  - Find middle position(s)
  - Handle even/odd counts
  
getStandardDeviation()
  - Calculate mean first
  - Calculate squared differences from mean
  - Sum all differences
  - Divide by count for variance
  - Return Math.sqrt(variance)
  
getStatisticsReport()
  - Formats all statistics as string
  - Returns multi-line report
```

### 4. DataTablePanel.java

**Purpose**: Display data in tabular format (GUI component)

**Technology**: Extends JPanel with JTable

```
JTable Setup:
  - DefaultTableModel: provides table data
  - Add column names from DataSet
  - Add all rows from DataSet
  - Set styling (colors, fonts, row height)
  - Place in JScrollPane for scrolling
  
Column Headers:
  - Background: Dark blue (70, 130, 180)
  - Text: White
  - Font: Arial Bold 12
  
Data Cells:
  - Background: White
  - Grid: Light gray (200, 200, 200)
  - Row height: 25 pixels
```

### 5. StatisticsPanel.java

**Purpose**: Display statistical analysis results

**Components**:
```
Top: Control Panel (FlowLayout)
  - Label "Select Column:"
  - JComboBox with all column names
  - ActionListener calls displayStatistics()

Center: JTextArea (read-only)
  - Font: Monospaced 12pt
  - Shows formatted statistics report
  - Auto-updates when column changes
```

**Display Format**:
```
┌────────────────────────────┐
│ Statistical Analysis       │
├────────────────────────────┤
│ Column: [column_name]      │
│ Data Points: [count]       │
│ Mean: [value]              │
│ Median: [value]            │
│ Std Dev: [value]           │
│ ... (more stats)           │
└────────────────────────────┘
```

### 6. ChartVisualizationPanel.java

**Purpose**: Display multiple chart types

**Chart Types**:

**Bar Chart**:
```
- X-axis: Categories (non-numeric column)
- Y-axis: Numeric values
- Implementation:
  1. Find max value for scaling
  2. Calculate bar height: (value/max) * chartHeight
  3. Draw filled rectangles with borders
  4. Label each bar with value
```

**Line Chart**:
```
- X-axis: Sequential points (by order)
- Y-axis: Numeric values
- Implementation:
  1. Connect points with line segments
  2. Scale coordinates like bar chart
  3. Draw circles at data points
  4. Display values as labels
```

**Pie Chart**:
```
- Represents proportions
- Implementation:
  1. Calculate total of all values
  2. For each value: (value/total) * 360°
  3. Draw arc segments (fillArc)
  4. Different colors for each slice
  5. Display percentages
```

### 7. CSVVisualizationApp.java

**Purpose**: Main application window

**Components**:

```
Menu Bar:
  File Menu:
    - Open CSV File (file chooser dialog)
    - Exit (System.exit(0))

Tabbed Pane (3 tabs):
  1. Data Table
     - Shows DataTablePanel
     - All imported data visible
  
  2. Statistics
     - Shows StatisticsPanel
     - Column selector dropdown
     - Statistical results
  
  3. Charts
     - Shows ChartVisualizationPanel
     - Chart type selector
     - Multiple visualizations

Status Bar:
  - Shows current file path
  - Row and column counts
  - Error messages
```

**Workflow**:

```
1. User clicks "File > Open CSV File"
2. JFileChooser opens (filters for .csv)
3. User selects file
4. loadCSVData() called:
   - CSVReader reads file
   - DataSet populated
   - All panels created
   - Tabs added to interface
5. User switches between tabs
6. User selects chart type or column
   - Visualization updates
   - Statistics recalculate
```

## File Input/Output Details

### CSV Reading Process

```java
try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    String line;
    while ((line = br.readLine()) != null) {
        // Process each line
        String[] row = parseLine(line);
        data.add(row);
    }
}
```

**Benefits of BufferedReader**:
- 8192 byte internal buffer
- Reduced disk I/O operations
- Much faster than direct FileReader
- readLine() handles \n, \r, \r\n automatically

### Quote Handling in CSV

```
Handles quoted fields:
  "Smith, John" -> reads as single field (not split by comma)
  "" (escaped quote) -> reads as single quote
  
Example:
  Input: "Name","Age","Notes"
  Output: ["Name", "Age", "Notes"]
```

## Compilation and Execution

### Compile:
```bash
javac *.java
```

### Run:
```bash
java CSVVisualizationApp
```

### Create Sample CSV (student_data.csv):
```
Name,Age,Score,Grade,Subject
Alice,20,85,B,Mathematics
Bob,22,92,A,Mathematics
Charlie,21,78,C,Physics
Diana,20,88,B,Chemistry
Eve,23,95,A,Mathematics
Frank,22,72,C,Physics
Grace,21,89,B,Chemistry
```

## Key Java Concepts Demonstrated

### 1. File I/O
- BufferedReader for efficient reading
- FileReader for character streams
- Exception handling with try-catch-finally

### 2. Collections Framework
- ArrayList for dynamic arrays
- HashMap for O(1) lookups
- List iteration and manipulation

### 3. Object-Oriented Programming
- Encapsulation (private fields, public methods)
- Inheritance (extends JPanel)
- Composition (DataSet contains Lists)

### 4. GUI Programming
- JFrame, JPanel, JTabbedPane
- JTable with custom models
- JComboBox for selections
- Layout managers (BorderLayout, FlowLayout)

### 5. Graphics Rendering
- Graphics2D for advanced drawing
- Coordinate calculations
- Shape rendering (fillRect, drawArc, etc.)
- Font and color management

### 6. String Processing
- split(), trim(), substring()
- StringBuilder for efficient concatenation
- String.format() for formatting

### 7. Exception Handling
- try-catch-finally blocks
- Specific exception types
- Error messages and recovery

### 8. Data Structures
- 2D data representation
- Column-based access patterns
- Numeric vs. string data differentiation

## Extension Ideas

### 1. More Statistics
- Variance, skewness, kurtosis
- Correlation between columns
- Regression analysis

### 2. More Visualizations
- Scatter plots
- Histograms
- Box plots
- Heat maps

### 3. Data Operations
- Sort by any column
- Filter by conditions
- Data export to CSV
- Find duplicates

### 4. Advanced Features
- Database integration
- Real-time data updates
- Data validation rules
- Missing value imputation

### 5. Performance
- Lazy loading for large files
- Caching of calculations
- Parallel processing

## Common Modifications

### Change Sample CSV Format
Edit createSampleCSV() or simply provide different CSV

### Add New Chart Type
1. Create new drawing method in ChartVisualizationPanel
2. Add case to switch statement in paintComponent()
3. Add selection to chart type combo box

### Modify Color Scheme
Change Color constants:
```java
new Color(R, G, B)
// Red: 255, 0, 0
// Green: 0, 255, 0
// Blue: 0, 0, 255
```

### Increase Statistical Calculations
Add methods to StatisticsCalculator:
```java
public double getVariance() { ... }
public double getSkewness() { ... }
```

## Performance Considerations

### For Large CSV Files
- Use BufferedReader (already optimized)
- Lazy load data if needed
- Consider streaming for >1GB files

### For Many Columns
- Only calculate statistics for numeric columns
- Cache calculations
- Use HashMap for column lookups

### For Responsive UI
- Load data on separate thread
- Update UI on Event Dispatch Thread
- Show progress indicator

