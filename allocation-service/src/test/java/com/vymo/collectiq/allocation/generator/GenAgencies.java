package com.vymo.collectiq.allocation.generator;

import java.io.FileWriter;

import static com.vymo.collectiq.allocation.generator.GenUtils.fileExists;
import static com.vymo.collectiq.allocation.generator.GenUtils.incrementAindex;

public class GenAgencies {

    private static final String AGENCIES_FILE = "target/agencies.csv";
    // Generate cases for allocation.
    public static void main(String[] args) throws Exception{
        if (fileExists(AGENCIES_FILE)){
            return;
        }
        System.out.println("****************************");
        System.out.println("Generating the Agencies database");
        System.out.println("****************************");
        int size = 32;
        FileWriter writer = new FileWriter(AGENCIES_FILE); // Creates or overwrites the file
        String header = "%s,%s,%s,%s,%s,%s\n".formatted("id", "branch",
                "product", "entity", "pincode", "territory");
        writer.write(header);

        int attributeCardinality = 2;
        int[] aindex = {1,1,1,1,1};
        for (int i = 1; i <= size; i++){
            String branch = "branch" + aindex[0];
            String product = "product" + aindex[1];
            String entity = "entity" + aindex[2];
            String pincode = "pincode" + aindex[3];
            String territory = "territory" + aindex[4];
            String data = "%s,%s,%s,%s,%s,%s\n".formatted("agency" + i, branch,
                    product, entity, pincode, territory);
            aindex = incrementAindex(aindex,attributeCardinality);
            writer.write(data);
        }

        writer.close();
    }
}
