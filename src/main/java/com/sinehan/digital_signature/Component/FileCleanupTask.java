package com.sinehan.digital_signature.Component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Component
public class FileCleanupTask {

    private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/files/";

    @Scheduled(fixedDelay = 3600000) // Scheduled to run every hour (in milliseconds)
    public void cleanupOldFiles() {
        long currentTimeMillis = System.currentTimeMillis();

        File directory = new File(uploadDir);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    long lastModified = file.lastModified();
                    long elapsedTime = currentTimeMillis - lastModified;
                    long oneHourInMillis = TimeUnit.HOURS.toMillis(1);

                    if (elapsedTime > oneHourInMillis) {
                        boolean deleted = file.delete();

                        if (deleted) {
                            System.out.println("Deleted file: " + file.getName());
                        } else {
                            System.err.println("Failed to delete file: " + file.getName());
                        }
                    }
                }
            }
        }
    }
}

