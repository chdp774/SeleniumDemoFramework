package com.demoproject.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class ExecutionHistoryManager {
    private static final String FILE_PATH = "src/test/resources/ExtentReport/execution_history.json";
    private static final int HISTORY_LIMIT = 5;

    public static void saveExecutionHistory(int passed, int failed, int skipped) {
        JSONArray historyArray = new JSONArray();

        // Load existing data
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
                historyArray = new JSONArray(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add new execution data
        JSONObject newExecution = new JSONObject();
        newExecution.put("date", new Date().toString());
        newExecution.put("passed", passed);
        newExecution.put("failed", failed);
        newExecution.put("skipped", skipped);

        historyArray.put(newExecution);

        // Keep only the last 5 records
        while (historyArray.length() > HISTORY_LIMIT) {
            historyArray.remove(0);
        }

        // Save updated history
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(historyArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

