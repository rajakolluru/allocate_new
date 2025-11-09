package com.vymo.collectiq.allocation.generator;

import java.io.File;
import java.util.Arrays;

public class GenUtils {
    public static void main(String[] args){
        int attributeCardinality = 2;
        int[] aindex = {1,1,1};
        for (int i = 0; i < 20; i++) {
            System.out.println("%s %s %s %s".formatted("index"+ 1,
                    "One" + aindex[0], "Two" + aindex[1],
                    "Three" + aindex[2]));
            aindex = incrementAindex(aindex, attributeCardinality);
        }
    }

    public static boolean fileExists(String s){
        File f = new File(s);
        return f.exists() && !f.isDirectory();
    }

    public static int[] incrementAindex(int[] aindex,int attributeCardinality) {
        int numAttributes = aindex.length;
        return incrementAttibuteNum(aindex, numAttributes-1,attributeCardinality);
    }

    private static int[] incrementAttibuteNum(int[] aindex, int attributeToChange,int attributeCardinality) {
        if (attributeToChange == -1){
            Arrays.fill(aindex, 1);
            return aindex;
        }
        if (aindex[attributeToChange] < attributeCardinality){
            aindex[attributeToChange]++;
            for (int i = attributeToChange+1; i < aindex.length; i++)
                aindex[i] = 1;
            return aindex;
        }
        return incrementAttibuteNum(aindex,attributeToChange-1,attributeCardinality);
    }
}
