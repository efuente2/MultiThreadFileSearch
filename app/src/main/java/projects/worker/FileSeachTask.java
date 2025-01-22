package projects.worker;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FileSeachTask implements Runnable {
    private final File file;
    private final String keyword;
    private final ConcurrentLinkedDeque<String> results;

    public FileSeachTask(File file, String keyword, ConcurrentLinkedDeque<String> results) {
        this.file = file;
        this.keyword = keyword;
        this.results = results;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) !=null) {
            if (line.contains(keyword)){
                results.add(file.getAbsolutePath());
                break;
            }
        }
    }   catch (IOException e) { 
        System.err.println("Error reading file: " + file.getAbsolutePath());
    }

    }
}