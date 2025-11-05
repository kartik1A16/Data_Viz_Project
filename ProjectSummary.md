# Data Visualization Project using Java- Summary

## What You Have

This project have features like:
1. **Loading CSV files** from disk
2. **Performing statistical analysis** on numeric columns
3. **Visualizing data** with multiple chart types
4. **Interactive exploration** through tabbed interface

## Project Files 

### Core Application Files (7 files)

1. **CSVReader.java** 
   - Reads CSV files with BufferedReader
   - Handles quoted fields and custom delimiters
   - Exception handling for file errors

2. **DataSet.java** 
   - Generic tabular data container
   - Column indexing for fast lookups
   - Type conversion (String to numeric)
   - Filtering and sorting capabilities

3. **StatisticsCalculator.java** 
   - Mean, median, mode calculations
   - Standard deviation and variance
   - Quartiles and percentiles
   - Formatted statistical reports

4. **DataTablePanel.java** 
   - JTable with DefaultTableModel
   - Scrollable grid display
   - Styled headers and cells

5. **StatisticsPanel.java** 
   - Column selector dropdown
   - Real-time statistics calculation
   - Formatted text output

6. **ChartVisualizationPanel.java** 
   - Bar chart rendering
   - Line chart rendering
   - Pie chart rendering
   - Dynamic chart switching

7. **CSVVisualizationApp.java** 
   - Main application window
   - File menu with CSV loader
   - Tabbed interface
   - Status bar with file information

### Sample Data
- **sample_data.csv** (20 rows, 5 columns)
  - Employee data with demographics and metrics
  - Ready-to-use test file

### Documentation
- **ProjectGuide.md** (comprehensive technical guide)
- **UsageGuide.md** (detailed usage instructions)

## Quick Compilation & Execution

```bash
# Compile all Java files
javac *.java

# Run the application
java CSVVisualizationApp

# Load sample_data.csv via File → Open CSV File menu
```

## Key Features

### Data Import
- Load any CSV file
- Automatic header detection
- Quote-aware field parsing
- Delimiter customization

### Statistical Analysis
- Automatic calculation for all numeric columns
- Column selector dropdown
- Instant statistics recalculation
- Metrics: Mean, Median, Mode, Std Dev, Quartiles, Min, Max, Range, Sum

### Visualization
- **Bar Chart**: Categories vs. Numeric values
- **Line Chart**: Trend over time/sequence
- **Pie Chart**: Proportional representation
- **Data Table**: Full data grid display

### User Interface
- Menu-driven file loading
- Tabbed interface (Data, Statistics, Charts)
- Real-time chart switching
- Status bar with file metadata

## Architecture Overview

```
┌─────────────────────────────────────┐
│     CSVVisualizationApp             │
│     (Main Window - JFrame)          │
├─────────────────────────────────────┤
│  File → Open CSV                    │
├─────────────────────────────────────┤
│                                     │
│  TabbedPane (3 tabs):               │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Data Table Tab              │   │
│  │ [DataTablePanel]            │   │
│  │ - JTable with scrolling     │   │
│  │ - All raw data visible      │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Statistics Tab              │   │
│  │ [StatisticsPanel]           │   │
│  │ - Column selector           │   │
│  │ - Statistical metrics       │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Charts Tab                  │   │
│  │ [ChartVisualizationPanel]   │   │
│  │ - Chart type selector       │   │
│  │ - Bar/Line/Pie charts       │   │
│  └─────────────────────────────┘   │
│                                     │
├─────────────────────────────────────┤
│ Status: Ready to load CSV file...  │
└─────────────────────────────────────┘
```

## Data Flow

```
CSV File
   ↓
CSVReader.readCSV()
   ↓ [BufferedReader]
Parse lines with quote handling
   ↓
List<String[]> data
   ↓
DataSet initialization
   ├─ setHeaders() → columnIndexMap
   └─ addRow() for each row
   ↓
Three visualizations created:
   ├─ DataTablePanel [JTable]
   ├─ StatisticsPanel [Statistics calc]
   └─ ChartVisualizationPanel [Graphics2D]
   ↓
Displayed in JTabbedPane
   ↓
User interaction:
   ├─ Select different columns
   ├─ Change chart type
   └─ View statistics
   ↓
Real-time updates
```

## Technology Stack

### Java Core
- Collections Framework (ArrayList, HashMap)
- Object-Oriented Programming
- Exception Handling (try-catch-finally)
- String Processing

### File I/O
- BufferedReader (efficient file reading)
- FileReader (character stream)
- IOException handling

### GUI (Swing)
- JFrame (main window)
- JTabbedPane (tab navigation)
- JPanel (custom components)
- JTable (data grid)
- JComboBox (dropdown selection)
- JFileChooser (file selection dialog)

### Graphics
- Graphics2D (advanced rendering)
- Shape drawing (rect, oval, arc, polygon)
- Color management
- Font rendering
- Coordinate transformation

### Data Processing
- CSV parsing with quote handling
- Type conversion (String ↔ Double)
- Statistical calculations
- Data filtering and sorting

## Key Algorithms Implemented

1. **CSV Parsing with Quotes**
   - Character-by-character iteration
   - Quote state tracking
   - Delimiter recognition outside quotes

2. **Statistical Calculations**
   - Mean: sum / count
   - Median: middle value (sorted)
   - Mode: frequency counting
   - Standard Deviation: √(Σ(x-mean)²/n)
   - Quartiles: positional percentiles

3. **Chart Coordinate Transformation**
   - Data value → pixel position
   - Proportional scaling based on max value
   - Y-axis inversion (screen coords)

4. **Data Filtering**
   - Create new DataSet
   - Copy headers
   - Iterate and match rows

## Memory Management

- BufferedReader: 8KB internal buffer
- HashMap: O(1) column lookups
- ArrayList: Dynamic resizing
- Collections: Memory-efficient storage
- Resource cleanup: try-with-resources for file I/O

## Error Handling

- FileNotFoundException: File not found
- IOException: General read errors
- NumberFormatException: Non-numeric data
- Empty dataset: User-friendly messages

## Performance Characteristics

| Operation | Complexity | Notes |
|-----------|-----------|-------|
| Read CSV | O(n) | Linear scan of file |
| Column lookup | O(1) | HashMap-based |
| Statistics | O(n) | Single pass required |
| Sorting | O(n log n) | Java Collections.sort() |
| Chart render | O(n) | One point per data value |

## Extensibility Points

### Add New Statistics
```java
public double getVariance() { ... }
public double getSkewness() { ... }
public double getKurtosis() { ... }
```

### Add New Chart Types
```java
private void drawScatterPlot(Graphics2D g2d, int w, int h) { ... }
private void drawHistogram(Graphics2D g2d, int w, int h) { ... }
private void drawBoxPlot(Graphics2D g2d, int w, int h) { ... }
```

### Add New Features
- Export to image (PNG/JPG)
- Export to PDF report
- Database integration
- Real-time streaming data
- Multi-file comparison

## Java Concepts Demonstrated

✓ File I/O (BufferedReader, FileReader)
✓ Collections (ArrayList, HashMap, List)
✓ Object-Oriented Programming (Classes, Inheritance)
✓ Encapsulation (Private fields, Public methods)
✓ Exception Handling (try-catch-finally)
✓ GUI Programming (Swing framework)
✓ Graphics Rendering (Graphics2D, shapes, colors)
✓ Algorithm Implementation (Parsing, Calculations)
✓ Event-Driven Programming (ActionListeners)
✓ Data Structures (2D arrays, Maps)

## Sample CSV Format

```csv
Name,Age,Score,Department,Salary
Alice Johnson,28,92,Engineering,75000
Bob Smith,34,88,Marketing,65000
Charlie Brown,26,95,Engineering,72000
```

## Supported CSV Features

✓ Standard comma-separated values
✓ Quoted fields with embedded commas
✓ Quoted fields with embedded quotes ("")
✓ Fields with line breaks
✓ Custom delimiters (tab, semicolon, etc.)
✓ Mixed numeric and text columns

## System Requirements

- Java 8 or higher
- JDK/JRE with Swing support
- Minimum 100MB available RAM
- CSV files under 500MB recommended

## Usage Example

```bash
# 1. Compile
$ javac *.java

# 2. Run
$ java CSVVisualizationApp

# 3. In application:
#    File → Open CSV File → select sample_data.csv
#    Click "Data Table" tab to see all data
#    Click "Statistics" tab to analyze specific column
#    Click "Charts" tab and switch between chart types

# 4. Experiment:
#    - Select different columns for analysis
#    - Switch between bar, line, pie charts
#    - View real-time calculations
```

## What Makes This Project Robust

1. **Proper Exception Handling**: FileNotFound, IOException, NumberFormat
2. **Efficient I/O**: BufferedReader with 8KB buffer
3. **Fast Lookups**: HashMap for column indexing
4. **Flexible Design**: Works with any CSV file
5. **User-Friendly**: Menu-driven interface, clear error messages
6. **Responsive**: Real-time chart and statistics updates
7. **Professional UI**: Styled components, organized tabs
8. **Scalable**: Handle thousands of rows

## Future Enhancement Ideas

### Analytics
- Correlation matrix
- Regression analysis
- Probability distributions
- Confidence intervals

### Visualization
- Heatmaps
- 3D charts
- Interactive drill-down
- Export to PDF/PNG

### Data
- Database integration
- API data loading
- Real-time streaming
- Data transformation

### Performance
- Lazy loading
- Caching
- Parallel processing
- Memory optimization

## Conclusion

This project provides a complete, professional-grade solution for:
- CSV file handling with robust parsing
- Statistical analysis with comprehensive metrics
- Multiple visualization options
- Interactive user interface

It demonstrates advanced Java concepts including file I/O, collections, OOP, GUI programming, graphics rendering, and algorithm implementation—all in a cohesive, production-ready application.

