# Advanced CSV Data Visualization Project - Complete Usage Guide

## Quick Start

### Step 1: Compile All Files
```bash
javac *.java
```

### Step 2: Run Application
```bash
java CSVVisualizationApp
```

### Step 3: Load CSV File
1. Click **File → Open CSV File**
2. Select your CSV file (e.g., `sample_data.csv`)
3. Application automatically loads and processes data

### Step 4: Explore Data
- **Data Table Tab**: View all imported data
- **Statistics Tab**: Select column and view statistical analysis
- **Charts Tab**: Switch between bar, line, and pie charts

---

## File Structure

```
Project Files:
├── CSVReader.java                    # CSV file parser
├── DataSet.java                      # Generic data container
├── StatisticsCalculator.java         # Statistical calculations
├── DataTablePanel.java               # Table visualization
├── StatisticsPanel.java              # Statistics display
├── ChartVisualizationPanel.java      # Chart rendering
├── CSVVisualizationApp.java          # Main application
└── sample_data.csv                   # Sample dataset
```

---

## Project Components

### 1. CSVReader.java
**Responsibility**: File I/O and CSV parsing

**How it works**:
```
File → BufferedReader → Line-by-line parsing → String[] rows
```

**Features**:
- Handles quoted fields with commas
- Custom delimiter support
- Exception handling for missing files
- Efficient buffered reading

**Usage**:
```java
CSVReader reader = new CSVReader("employees.csv");
List<String[]> data = reader.readCSV();           // All rows
String[] headers = reader.getHeader();             // Headers only
List<String[]> noHeader = reader.readCSVSkipHeader(); // Skip first row
```

---

### 2. DataSet.java
**Responsibility**: Generic tabular data container

**Key Features**:
- Column name mapping (HashMap for O(1) lookup)
- Row storage (List of Lists)
- Type conversion (String → Double)
- Data filtering and sorting

**Data Structures**:
```java
List<String> columnNames          // ["Name", "Age", "Score", ...]
List<List<String>> rows           // [["Alice", "28", "92"], ...]
Map<String, Integer> columnIndexMap // {"Name": 0, "Age": 1, ...}
```

**Common Operations**:
```java
dataset.setHeaders(headers);                    // Initialize
dataset.addRow(rowData);                        // Add row
int rows = dataset.getRowCount();               // Get count
List<Double> nums = dataset.getColumnAsNumbers("Age");  // Get numeric column
DataSet filtered = dataset.filterByColumn("Department", "Engineering");
dataset.sortByColumn("Salary", false);          // Sort descending
```

---

### 3. StatisticsCalculator.java
**Responsibility**: Statistical analysis

**Calculations Available**:
| Metric | Formula | Use Case |
|--------|---------|----------|
| Mean | Sum / Count | Average value |
| Median | Middle value (sorted) | Central tendency (outlier-resistant) |
| Mode | Most frequent | Most common value |
| Std Dev | √(Σ(x-mean)²/n) | Data spread |
| Min/Max | Smallest/Largest | Range bounds |
| Range | Max - Min | Value spread |
| Quartiles | 25th, 50th, 75th percentiles | Distribution shape |

**Example Usage**:
```java
List<Double> scores = dataset.getColumnAsNumbers("Score");
StatisticsCalculator calc = new StatisticsCalculator(scores, "Score");

double avg = calc.getMean();                    // 89.45
double med = calc.getMedian();                 // 90.0
double stdDev = calc.getStandardDeviation();   // 3.15
double[] quartiles = calc.getQuartiles();      // [Q1, Q2, Q3]

System.out.println(calc.getStatisticsReport()); // Formatted report
```

---

### 4. DataTablePanel.java
**Responsibility**: Display data as interactive table

**Features**:
- JTable with DefaultTableModel
- Scrollable (vertical and horizontal)
- Styled headers (blue background, white text)
- Grid lines for readability

**Technology Stack**:
```
DataSet → DefaultTableModel → JTable → JScrollPane → JPanel
```

**Visual Design**:
```
┌─────────────────────────────────────┐
│ Name    │ Age │ Score │ Department  │  ← Headers (blue, bold)
├─────────────────────────────────────┤
│ Alice   │ 28  │ 92    │ Engineering │
│ Bob     │ 34  │ 88    │ Marketing   │  ← Data rows (white)
│ Charlie │ 26  │ 95    │ Engineering │
│ ...     │ ... │ ...   │ ...         │
└─────────────────────────────────────┘
```

---

### 5. StatisticsPanel.java
**Responsibility**: Display statistical analysis

**Features**:
- Column selector dropdown
- Automatic statistics calculation
- Formatted text display
- Real-time updates

**Workflow**:
```
1. User opens Statistics tab
2. Display statistics for first numeric column
3. User selects different column
4. Statistics automatically recalculate
5. Display updated report
```

**Report Format**:
```
╔════════════════════════════════════╗
║   Statistical Analysis Report      ║
╚════════════════════════════════════╝

Column: Score
Data Points: 20

┌─ Measures of Central Tendency ─┐
│ Mean (Average):          89.45  │
│ Median (Middle):         90.00  │
│ Mode (Most Frequent):    91.00  │
└────────────────────────────────┘

┌─ Measures of Dispersion ──────┐
│ Standard Deviation:       3.15  │
│ Range:                   11.00  │
│ Minimum:                 84.00  │
│ Maximum:                 95.00  │
└────────────────────────────────┘
```

---

### 6. ChartVisualizationPanel.java
**Responsibility**: Render multiple chart types

**Bar Chart**:
- X-axis: Categories (text column)
- Y-axis: Numeric values
- Each category: vertical rectangle
- Height proportional to value
- Labels: value on bar, category below

```
Example:
     ┌─────┐
  95 │     │
  90 │ ┌─┐ │
  85 │ │ │ │
  80 │ │ │ │
     └─┴─┴─┘
       A B C
```

**Line Chart**:
- X-axis: Data point index or category
- Y-axis: Numeric values
- Points connected by lines
- Circles mark data points
- Labels: value above point

```
Example:
  95┤       ●
  90├   ●─●─┤
  85├───┤   │
     └────────
```

**Pie Chart**:
- Circular representation
- Each slice: portion of total
- Size proportional to value
- Labeled with percentages
- Different colors per slice

```
Example:
        ┌─╢─┐
       ╱ 25% ╲
      │ 50%   │
       ╲ 25% ╱
        └─┼─┘
```

**Coordinate Transformation**:
```
Screen coordinates:
  (0,0) at top-left
  X increases right
  Y increases down

Formula for bar position:
  screenY = screenHeight - dataHeight - padding
```

---

### 7. CSVVisualizationApp.java
**Responsibility**: Main application window

**GUI Layout**:
```
┌─ File | ─────────────────────────────────┐
├───────────────────────────────────────────┤
│ [Data Table] [Statistics] [Charts]        │  ← Tabs
├───────────────────────────────────────────┤
│                                           │
│  Content Area (varies by tab)             │
│                                           │
│                                           │
├───────────────────────────────────────────┤
│ Ready to load CSV file...                 │  ← Status bar
└───────────────────────────────────────────┘
```

**Event Flow**:
```
File > Open CSV File
    ↓
JFileChooser opened
    ↓
User selects file
    ↓
loadCSVData() called
    ↓
CSVReader.readCSV()
    ↓
DataSet created
    ↓
Panels initialized
    ↓
Tabs populated
    ↓
UI refreshed
```

---

## Data Processing Pipeline

### Step-by-Step Data Flow

```
1. File Selection
   └─ User selects CSV file via JFileChooser

2. File Reading
   └─ CSVReader
      ├─ Create BufferedReader
      ├─ Read line by line
      ├─ Parse quoted fields
      └─ Return List<String[]>

3. Data Organization
   └─ DataSet
      ├─ First row → Headers (columnIndexMap)
      ├─ Remaining rows → rows storage
      └─ Ready for access and manipulation

4. Visualization Preparation
   ├─ DataTablePanel
   │  └─ Create JTable from all data
   ├─ StatisticsPanel
   │  └─ Extract numeric columns
   └─ ChartVisualizationPanel
      └─ Prepare x,y data

5. Display
   └─ Tabs added to JTabbedPane
      ├─ Data Table
      ├─ Statistics
      └─ Charts
```

---

## Key Algorithms

### CSV Parsing with Quote Handling

```java
for (int i = 0; i < line.length(); i++) {
    char c = line.charAt(i);
    
    if (c == '"') {
        // Toggle quote state (even/odd occurrences)
        inQuotes = !inQuotes;
    } 
    else if (c == delimiter && !inQuotes) {
        // End of field (delimiter outside quotes)
        fields.add(field.toString());
        field = new StringBuilder();
    } 
    else {
        field.append(c);
    }
}
```

**Example**:
```
Input: "Smith, John",28,Engineer,"San Francisco, CA"
       ^           ^                                 ^
       start quote  end quote at position 10        end quote at position 52
       
Step-by-step:
1. " → inQuotes = true
2. S-m-i-t-h-,- -J-o-h-n → append (comma inside quotes)
3. " → inQuotes = false
4. , → delimiter outside quotes → add field
5. Continue...

Output: ["Smith, John", "28", "Engineer", "San Francisco, CA"]
```

### Mean Calculation

```java
double mean = 0.0;
for (double value : data) {
    mean += value;
}
mean = mean / data.size();
```

**Example**:
```
Data: [85, 90, 92, 88, 95]
Sum: 85 + 90 + 92 + 88 + 95 = 450
Mean: 450 / 5 = 90.0
```

### Standard Deviation Calculation

```java
double mean = getMean();
double sumSquaredDiff = 0.0;

for (double value : data) {
    double diff = value - mean;
    sumSquaredDiff += diff * diff;
}

double variance = sumSquaredDiff / data.size();
return Math.sqrt(variance);
```

**Example**:
```
Data: [85, 90, 92, 88, 95], Mean = 90

Differences from mean: [-5, 0, 2, -2, 5]
Squared differences:   [25, 0, 4, 4, 25]
Sum:                   58
Variance:              58 / 5 = 11.6
Std Dev:               √11.6 ≈ 3.41
```

### Pie Chart Angle Calculation

```java
for (int i = 0; i < values.size(); i++) {
    double value = values.get(i);
    int arcAngle = (int)(360.0 * value / total);
    
    g2d.fillArc(centerX, centerY, diameter, diameter, 
                startAngle, arcAngle);
    
    startAngle += arcAngle;
}
```

**Example**:
```
Values: [100, 200, 150], Total = 450

Slice 1: (100/450) * 360 = 80°
Slice 2: (200/450) * 360 = 160°
Slice 3: (150/450) * 360 = 120°
```

---

## Error Handling

### File Not Found
```java
try {
    CSVReader reader = new CSVReader("nonexistent.csv");
} catch (FileNotFoundException e) {
    JOptionPane.showMessageDialog(this, 
        "File not found: " + e.getMessage());
}
```

### Invalid Data Format
```java
try {
    double value = Double.parseDouble("not_a_number");
} catch (NumberFormatException e) {
    // Skip non-numeric values in statistical calculations
}
```

### Empty or Malformed CSV
```java
if (dataSet.getRowCount() == 0) {
    statusLabel.setText("Error: CSV file has no data rows");
}
```

---

## Performance Tips

### For Large CSV Files (>10MB)
1. Use BufferedReader (already implemented)
2. Read only required columns
3. Consider streaming approach

### For Many Columns (>100)
1. Only calculate statistics for numeric columns
2. Cache calculation results
3. Use HashMap for column lookups (O(1))

### For Frequent Updates
1. Lazy load data
2. Update UI on Event Dispatch Thread
3. Show progress indicator

---

## Common CSV Formats Supported

### Standard Comma-Separated
```csv
Name,Age,Salary
Alice,28,75000
Bob,34,80000
```

### With Quoted Fields
```csv
"Last Name","First Name","Notes"
"Smith","John","Contains, comma"
"Johnson","Jane","Has ""quotes"""
```

### With Quotes and Commas in Values
```csv
Company,Address,Contact
"Apple Inc.","123 Main St, Cupertino, CA","John Doe"
"Microsoft","456 Park Ave, Seattle, WA","Jane Smith"
```

---

## Extension Examples

### Add Variance Calculation
```java
public double getVariance() {
    double mean = getMean();
    double sumSquaredDiff = 0.0;
    for (double value : data) {
        sumSquaredDiff += Math.pow(value - mean, 2);
    }
    return sumSquaredDiff / (data.size() - 1);  // Sample variance
}
```

### Add Sorting by Column
```java
public void sortByColumn(String columnName, boolean ascending) {
    int index = getColumnIndex(columnName);
    final boolean asc = ascending;
    
    rows.sort((row1, row2) -> {
        try {
            double val1 = Double.parseDouble(row1.get(index));
            double val2 = Double.parseDouble(row2.get(index));
            return asc ? Double.compare(val1, val2) 
                      : Double.compare(val2, val1);
        } catch (NumberFormatException e) {
            return 0;
        }
    });
}
```

### Add Histogram Chart
```java
private void drawHistogram(Graphics2D g2d, int width, int height) {
    // Divide data into bins
    // Count frequency in each bin
    // Draw bars for frequencies
    // Add axis labels
}
```

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| "No data to display" | CSV file might be empty; check file format |
| Statistics showing 0 | Column contains non-numeric data; try different column |
| Chart not updating | Make sure to select correct chart type dropdown |
| File not found | Check file path; ensure file is in current directory |
| Application crashes | Check CSV format; ensure proper encoding (UTF-8) |

---

## Summary

This project demonstrates:
✓ File I/O (BufferedReader, exception handling)
✓ Collections (ArrayList, HashMap, List operations)
✓ Object-oriented design (encapsulation, inheritance)
✓ GUI programming (Swing, event listeners, layout managers)
✓ Graphics rendering (Charts with coordinate transformation)
✓ Statistical analysis (mean, median, standard deviation)
✓ Data processing (filtering, sorting, type conversion)
✓ Algorithm implementation (parsing, calculations)

