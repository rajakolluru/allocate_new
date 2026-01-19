package com.vymo.collectiq.allocation.service.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vymo.collectiq.allocation.model.Allocatee;
import com.vymo.collectiq.allocation.model.Rule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoaderUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Allocatee> loadUsers(String csvFilePath) throws IOException {
        List<Allocatee> allocateeList = new ArrayList<>();

        // Load CSV from resources (classpath)
        try (InputStream inputStream = new FileInputStream(csvFilePath)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                boolean isFirstLine = true;
                String[] headers = null;
                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        headers = line.split(",");
                        // skip header
                        isFirstLine = false;
                        continue;
                    }

                    String[] values = line.split(",");
                    if (values.length >= 6) {

                        Allocatee allocatee = new Allocatee();
                        allocatee.put(headers[0].trim(),values[0].trim());
                        allocatee.put(headers[1].trim(),values[1].trim());
                        allocatee.put(headers[2].trim(),values[2].trim());
                        allocatee.put(headers[3].trim(),values[3].trim());
                        allocatee.put(headers[4].trim(),values[4].trim());
                        allocatee.put(headers[5].trim(),values[5].trim());
                        allocateeList.add(allocatee);
                    }
                }
            }
        }
        return allocateeList;
    }

}
