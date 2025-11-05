// ============================================================
// File: CSVReader.java
// Purpose: Read and parse CSV files from disk
// ============================================================

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private String filePath;
    private char delimiter;
    
    // Constructor with default comma delimiter
    public CSVReader(String filePath) {
        this(filePath, ',');
    }
    
    // Constructor with custom delimiter
    public CSVReader(String filePath, char delimiter) {
        this.filePath = filePath;
        this.delimiter = delimiter;
    }
    
    // Read CSV file and return as List of String arrays
    public List<String[]> readCSV() throws IOException {
        List<String[]> data = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse each line into array of values
                String[] row = parseLine(line);
                data.add(row);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            throw new IOException("Cannot find file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            throw e;
        }
        
        return data;
    }
    
    // Parse a single CSV line handling quoted fields
    private String[] parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        
        // Iterate through each character in the line
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            // Check for quote character
            if (c == '"') {
                // Toggle quote state
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Double quote means escaped quote
                    field.append('"');
                    i++;  // Skip next quote
                } else {
                    inQuotes = !inQuotes;
                }
            } 
            // Check for delimiter
            else if (c == delimiter && !inQuotes) {
                // End of field
                fields.add(field.toString().trim());
                field = new StringBuilder();
            } 
            else {
                field.append(c);
            }
        }
        
        // Add last field
        fields.add(field.toString().trim());
        
        return fields.toArray(new String[0]);
    }
    
    // Read CSV and skip first line (assuming header)
    public List<String[]> readCSVSkipHeader() throws IOException {
        List<String[]> data = readCSV();
        if (!data.isEmpty()) {
            data.remove(0);  // Remove header row
        }
        return data;
    }
    
    // Get header row only
    public String[] getHeader() throws IOException {
        List<String[]> data = readCSV();
        if (!data.isEmpty()) {
            return data.get(0);
        }
        return new String[0];
    }
}
