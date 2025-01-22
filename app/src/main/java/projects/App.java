/*
 * This source file was generated by the Gradle 'init' task
 */
package projects;

import java.io.File;
import java.io.File.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import projects.worker.FileSeachTask;

public class App {

    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("Follow this sentax for runnging the application:  java App.java <directory> <keyword>");
            return;
        }

        String directoryPath = args[0];
        String keyword = args[1];

        Path directory = Paths.get(directoryPath);
        if (!Files.isDirectory(directory)) {
            System.out.println("The provided path is not a directory");
            return;
        }

        //Thread safe queue to store results 
        ConcurrentLinkedDeque<String> results = new ConcurrentLinkedDeque<>();

        //thread pool to manage worker threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Files.walk(directory).filter(Files::isRegularFile).forEach(file -> {
            executorService.submit(new FileSeachTask(file.toFile(), keyword, results));
        });

        executorService.shutdown();
        while (!executorService.isTerminated()){
            //whait for all task to complete
        }

        System.out.println("Search complete. Files containing the keyword:");
        for (String result : results){
            System.out.println(result);
        }

    }
}
