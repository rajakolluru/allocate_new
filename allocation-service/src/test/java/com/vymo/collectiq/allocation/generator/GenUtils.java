package com.vymo.collectiq.allocation.generator;

import java.io.File;
import java.util.Arrays;

/**
 * Allows the generation oif multiple attributes in the following format.
 * if there are 3 attributes and cardinality is 2, we will have 2x2x2 = 8 combinations like:
 * id1, a1-1, a2-1, a3-1
 * id2, a1-1, a2-1, a3-2
 * id3, a1-1, a2-2, a3-1
 * id4, a1-1, a2-2, a3-2
 * id5, a1-2, a2-1, a3-1
 * id6, a1-2, a2-1, a3-2
 * id5, a1-2, a2-2, a3-1
 * id6, a1-2, a2-2, a3-2
 */
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
