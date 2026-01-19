package com.vymo.collectiq.allocation.generator;

import java.io.FileWriter;

import static com.vymo.collectiq.allocation.generator.GenUtils.fileExists;
import static com.vymo.collectiq.allocation.generator.GenUtils.incrementAindex;

public class GenCases1 {

    private static final String CASES_FILE = "/Users/rajashankarkolluru/ingestion/file2.csv";
    // Generate cases for allocation.
    public static void main(String[] args) throws Exception{
        if (fileExists(CASES_FILE)){
            return;
        }
        System.out.println("****************************");
        System.out.println("Generating the cases database");
        System.out.println("****************************");
        int size = 10000000;
        FileWriter writer = new FileWriter(CASES_FILE); // Creates or overwrites the file
        String header = "%s,%s,%s,%s,%s,%s\n".formatted("id", "branch",
                "product", "entity", "pincode", "territory");
        // writer.write(header);


        for (int i = 1; i <= size; i++){
            StringBuilder s = new StringBuilder();
            for (int j = 1; j <= 300; j++){
                if (j != 1)s.append(",");
                s.append("value").append(j);
            }
            s.append("\n");
            writer.write(s.toString());
        }

        writer.close();
    }
}
