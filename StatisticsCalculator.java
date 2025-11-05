// ============================================================
// File: StatisticsCalculator.java
// Purpose: Perform statistical analysis on numeric data
// ============================================================

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsCalculator {
    private List<Double> data;
    private String columnName;
    
    // Constructor
    public StatisticsCalculator(List<Double> data, String columnName) {
        this.data = new ArrayList<>(data);
        this.columnName = columnName;
    }
    
    // Calculate mean (average)
    public double getMean() {
        if (data.isEmpty()) return 0.0;
        
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }
    
    // Calculate median (middle value)
    public double getMedian() {
        if (data.isEmpty()) return 0.0;
        
        List<Double> sorted = new ArrayList<>(data);
        Collections.sort(sorted);
        
        int size = sorted.size();
        if (size % 2 == 0) {
            // Even number of elements: average of two middle values
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        } else {
            // Odd number of elements: middle value
            return sorted.get(size / 2);
        }
    }
    
    // Calculate mode (most frequent value)
    public double getMode() {
        if (data.isEmpty()) return 0.0;
        
        // Count frequency of each value
        java.util.Map<Double, Integer> frequency = new java.util.HashMap<>();
        for (double value : data) {
            frequency.put(value, frequency.getOrDefault(value, 0) + 1);
        }
        
        // Find most frequent value
        double mode = data.get(0);
        int maxFreq = 0;
        for (java.util.Map.Entry<Double, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }
    
    // Calculate standard deviation
    public double getStandardDeviation() {
        if (data.isEmpty()) return 0.0;
        
        double mean = getMean();
        double sumSquaredDifferences = 0.0;
        
        // Calculate sum of squared differences from mean
        for (double value : data) {
            double difference = value - mean;
            sumSquaredDifferences += difference * difference;
        }
        
        // Calculate variance (average of squared differences)
        double variance = sumSquaredDifferences / data.size();
        
        // Standard deviation is square root of variance
        return Math.sqrt(variance);
    }
    
    // Calculate minimum value
    public double getMin() {
        if (data.isEmpty()) return 0.0;
        
        double min = data.get(0);
        for (double value : data) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
    
    // Calculate maximum value
    public double getMax() {
        if (data.isEmpty()) return 0.0;
        
        double max = data.get(0);
        for (double value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    
    // Calculate sum
    public double getSum() {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum;
    }
    
    // Calculate range (max - min)
    public double getRange() {
        return getMax() - getMin();
    }
    
    // Get count of data points
    public int getCount() {
        return data.size();
    }
    
    // Get quartiles
    public double[] getQuartiles() {
        if (data.size() < 4) {
            return new double[]{getMin(), getMedian(), getMax()};
        }
        
        List<Double> sorted = new ArrayList<>(data);
        Collections.sort(sorted);
        
        int size = sorted.size();
        
        // Q1: 25th percentile
        double q1 = sorted.get(size / 4);
        
        // Q2: 50th percentile (median)
        double q2 = getMedian();
        
        // Q3: 75th percentile
        double q3 = sorted.get((3 * size) / 4);
        
        return new double[]{q1, q2, q3};
    }
    
    // Get all statistics as formatted string
    public String getStatisticsReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== Statistics for ").append(columnName).append(" ===\n");
        report.append(String.format("Count: %d\n", getCount()));
        report.append(String.format("Mean: %.2f\n", getMean()));
        report.append(String.format("Median: %.2f\n", getMedian()));
        report.append(String.format("Mode: %.2f\n", getMode()));
        report.append(String.format("Std Dev: %.2f\n", getStandardDeviation()));
        report.append(String.format("Min: %.2f\n", getMin()));
        report.append(String.format("Max: %.2f\n", getMax()));
        report.append(String.format("Range: %.2f\n", getRange()));
        report.append(String.format("Sum: %.2f\n", getSum()));
        
        return report.toString();
    }
}
