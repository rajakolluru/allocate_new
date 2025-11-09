package com.vymo.collectiq.allocation.generator;

import java.io.FileWriter;
import static com.vymo.collectiq.allocation.generator.GenUtils.*;

public class GenCases {

    private static final String CASES_FILE = "target/cases.csv";
    // Generate cases for allocation.
    public static void main(String[] args) throws Exception{
        if (fileExists(CASES_FILE)){
            return;
        }
        System.out.println("****************************");
        System.out.println("Generating the cases database");
        System.out.println("****************************");
        int size = 5000000;
        FileWriter writer = new FileWriter(CASES_FILE); // Creates or overwrites the file
        String header = "%s,%s,%s,%s,%s,%s\n".formatted("id", "branch",
                "product", "entity", "pincode", "territory");
        writer.write(header);

        int attributeCardinality = 10;
        int[] aindex = {1,1,1,1,1};
        for (int i = 1; i <= size; i++){
            String branch = "branch" + aindex[0];
            String product = "product" + aindex[1];
            String entity = "entity" + aindex[2];
            String pincode = "pincode" + aindex[3];
            String territory = "territory" + aindex[4];
            String data = "%s,%s,%s,%s,%s,%s\n".formatted("id" + i, branch,
                    product, entity, pincode, territory);
            aindex = incrementAindex(aindex,attributeCardinality);
            writer.write(data);
        }

        writer.close();
    }
}
