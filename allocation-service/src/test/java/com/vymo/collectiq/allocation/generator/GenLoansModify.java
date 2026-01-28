package com.vymo.collectiq.allocation.generator;

import java.io.FileWriter;
import java.util.Arrays;

import static com.vymo.collectiq.allocation.generator.GenUtils.fileExists;
import static com.vymo.collectiq.allocation.generator.GenUtils.incrementAindex;

public class GenLoansModify {

    private static final String FILE = "/Users/rajashankarkolluru/ingestion/file2.csv";

    public static void main(String[] args) throws Exception{
        if (fileExists(FILE)){
            return;
        }
        System.out.println("****************************");
        System.out.println("Generating the loans database with modifications");
        System.out.println("****************************");
        long t = System.nanoTime();
        int size = 10000000;
        int numColumns = 200;
        int attributeCardinality = 10;
        StringBuilder h = new StringBuilder("id");
        for (int i = 1; i <= numColumns;i++){
            h.append(",col").append(i);
        }
        FileWriter writer = new FileWriter(FILE); // Creates or overwrites the file
        writer.write(h.append("\n").toString());

        int[] aindex = new int[numColumns];
        Arrays.fill(aindex,1);
        for (int i = 1; i <= size; i++){
            StringBuilder data = new StringBuilder("loan" + i);
            for (int j = 1; j <= numColumns; j++) {
                data.append(",col_modification_for_second_time").append(j).append("_").append(aindex[j-1]);
            }
            aindex = incrementAindex(aindex, attributeCardinality);
            writer.write(data.append("\n").toString());
        }
        writer.close();

        System.out.println("Total time taken: " + ((System.nanoTime() - t)/1e9) + " seconds");

    }

    public static void createSchema(int numColumns){
        StringBuilder sb = new StringBuilder("create table loans (id varchar(128)\n");
        for (int i = 1; i <= numColumns; i++){
            sb.append(",col").append(i).append(" varchar(128)\n");
        }
        sb.append(")");
        System.out.println(sb.toString());
    }
}
