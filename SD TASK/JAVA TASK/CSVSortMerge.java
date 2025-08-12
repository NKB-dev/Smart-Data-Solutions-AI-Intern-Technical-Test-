//Approach 2 – Sort + Merge (Two Pointer Method)
/*
Here I decided to sort both CSV files by their ID column first and then walk through them
together using two pointers. This way, I can match rows without checking every single
pair like in the brute force method. Once the IDs match, I join the lines and save them
to the output. It’s a nice middle ground between simple and efficient, but it does
require loading and sorting the whole files before matching.
*/
//Time Complexity  -    O(n log n + m log m)
// Space Complexity - 	O(n + m)

import java.io.*;
import java.util.*;

public class CSVSortMerge {

    public static void combineFiles(String csv1, String csv2, String output) {
        List<String[]> list1 = new ArrayList<>();
        List<String[]> list2 = new ArrayList<>();

        // Load CSV1
        try (BufferedReader br = new BufferedReader(new FileReader(csv1))) {
            String line;
            while ((line = br.readLine()) != null) {
                list1.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading " + csv1);
        }

        // Load CSV2
        try (BufferedReader br = new BufferedReader(new FileReader(csv2))) {
            String line;
            while ((line = br.readLine()) != null) {
                list2.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading " + csv2);
        }

        // Sort by ID (first column)
        Comparator<String[]> byId = Comparator.comparing(a -> a[0]);
        list1.sort(byId);
        list2.sort(byId);

        // Merge using two pointers
        int i = 0, j = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {
            while (i < list1.size() && j < list2.size()) {
                int cmp = list1.get(i)[0].compareTo(list2.get(j)[0]);
                if (cmp == 0) {
                    String merged = String.join(",", list1.get(i)) + "," +
                            String.join(",", Arrays.copyOfRange(list2.get(j), 1, list2.get(j).length));
                    bw.write(merged);
                    bw.newLine();
                    i++;
                    j++;
                } else if (cmp < 0) {
                    i++;
                } else {
                    j++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing output");
        }
    }

    public static void main(String[] args) {
        combineFiles("csv1.csv", "csv2.csv", "output_sortmerge.csv");
        System.out.println("Sort + Merge method done.");
    }
}

