
//Approach 1 – HashMap Lookup
/*
This program basically reads two csv files and checks if the first column (ID) matches in both files.
If it finds a match, it will join that line from csv1 with the rest of the columns from csv2 (excluding the id again).
I used a HashMap to store csv2 data so that searching for matches is super quick.
Then I just loop through csv1 and whenever there’s a match, I write the combined line into a new output file.
The output only contains rows that had matches in both files, so no unmatched junk is written.
Pretty straightforward but does the job neatly.
*/

//Time Complexity	- O(n + m)
// Space Complexity-  O(m)

import java.io.*;
import java.util.*;

public class CSVCombiner {

    // This method will "stick" matching lines from csv1 and csv2 together
    static void combineFiles(String file1, String file2, String outFile) {
        // map to store data from csv2 (excluding the id column)
        HashMap<String, String> map2 = new HashMap<>();

        // 1. Read csv2 and save rows in the map
        try (BufferedReader br = new BufferedReader(new FileReader(file2))) {
            String lineFromCsv2;
            while ((lineFromCsv2 = br.readLine()) != null) {
                String[] bits = lineFromCsv2.split(",");
                if (bits.length > 1) {
                    // join the rest of the columns back into a string
                    String restOfLine = String.join(",", Arrays.copyOfRange(bits, 1, bits.length));
                    map2.put(bits[0], restOfLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file 2: " + e.getMessage());
            return;
        }

        // 2. Read csv1 and if there's a match in csv2, write combined row to output
        try (
                BufferedReader br = new BufferedReader(new FileReader(file1));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))
        ) {
            String lineFromCsv1;
            while ((lineFromCsv1 = br.readLine()) != null) {
                String[] bits = lineFromCsv1.split(",");
                String id = bits[0];

                if (map2.containsKey(id)) {
                    // matched → merge csv1 line + csv2 line (minus id)
                    String merged = lineFromCsv1 + "," + map2.get(id);
                    bw.write(merged);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file 1 or writing output: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // file names (make sure they're in the project root)
        String csv1 = "csv1.csv";
        String csv2 = "csv2.csv";
        String output = "output.csv";

        // call our cool method
        combineFiles(csv1, csv2, output);

        System.out.println("Done! Check the file: " + output);
    }
}
