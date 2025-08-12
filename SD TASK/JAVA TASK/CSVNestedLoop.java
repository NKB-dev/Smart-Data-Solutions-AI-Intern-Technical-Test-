//Approach 3 – Nested Loops
/*
In this version I didn’t use any fancy data structures, just pure looping.
First I read both CSV files into two lists and then compared every row from the first file
with every row from the second file to see if their IDs match. When a match is found,
I merge the data and write it to the output. It’s pretty straightforward and easy to
understand, but not the fastest thing in the world since it checks every possible pair.
Still works fine for small files.
*/

//Time Complexity   - O(n × m)
// Space Complexity - O(n + m)


import java.io.*;
import java.util.*;

public class CSVNestedLoop {

    public static void combineFiles(String csv1, String csv2, String output) {
        List<String[]> list1 = new ArrayList<>();
        List<String[]> list2 = new ArrayList<>();

        // Load CSV1 into list1
        try (BufferedReader br = new BufferedReader(new FileReader(csv1))) {
            String line;
            while ((line = br.readLine()) != null) {
                list1.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading " + csv1);
        }

        // Load CSV2 into list2
        try (BufferedReader br = new BufferedReader(new FileReader(csv2))) {
            String line;
            while ((line = br.readLine()) != null) {
                list2.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading " + csv2);
        }

        // Compare every row from csv1 with every row from csv2
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {
            for (String[] row1 : list1) {
                for (String[] row2 : list2) {
                    if (row1[0].equals(row2[0])) {
                        String merged = String.join(",", row1) + "," +
                                String.join(",", Arrays.copyOfRange(row2, 1, row2.length));
                        bw.write(merged);
                        bw.newLine();
                        break; // stop after finding first match
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing output");
        }
    }

    public static void main(String[] args) {
        combineFiles("csv1.csv", "csv2.csv", "output_nested.csv");
        System.out.println("Nested loop method done.");
    }
}
