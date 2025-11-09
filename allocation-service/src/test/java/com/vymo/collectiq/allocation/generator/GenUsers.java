package com.vymo.collectiq.allocation.generator;

import java.io.FileWriter;

import static com.vymo.collectiq.allocation.generator.GenUtils.fileExists;
import static com.vymo.collectiq.allocation.generator.GenUtils.incrementAindex;

public class GenUsers {
    private static final String USERS_FILE = "target/users1.csv";
    public static void main(String[] args) throws Exception{
        if (fileExists(USERS_FILE)){
            return;
        }
        System.out.println("****************************");
        System.out.println("Generating the users database");
        System.out.println("****************************");
        int size = 500000;
        int[] aindex = {1,1,1,1,1};
        FileWriter writer = new FileWriter(USERS_FILE);// Creates or overwrites the file
        int attributeCardinality = 10;
        String header = "%s,%s,%s,%s,%s,%s\n".formatted("id", "branch",
                "product", "entity", "pincode", "territory");
        writer.write(header);
        for (int i = 1; i <= size; i++){
            String branch = "branch" + aindex[0];
            String product = "product" + aindex[1];
            String entity = "entity" + aindex[2];
            String attribute4 = "pincode" + aindex[3];
            String attribute5 = "territory" + aindex[4];
            String data = "%s,%s,%s,%s,%s,%s\n".formatted("id" + i, branch,
                   product, entity, attribute4, attribute5);
            writer.write(data);
            aindex = incrementAindex(aindex,attributeCardinality);
        }
        writer.close();
    }
}
