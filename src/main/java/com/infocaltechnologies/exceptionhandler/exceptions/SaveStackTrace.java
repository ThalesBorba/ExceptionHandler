package com.infocaltechnologies.exceptionhandler.exceptions;

import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class SaveStackTrace {

    public static final String ERROR_LOG = "error.log";
    public static final String OCORREU_UM_ERRO_AO_SALVAR_O_LOG = "Ocorreu um erro ao salvar o log";
    private static final long MAX_FILE_SIZE = 1048576; // 1 MB

    static void saveStackTraceToFile(Exception ex) {
        try {
            String timestamp = getFormattedTimestamp();
            String stackTrace = getStackTraceAsString(ex);
            String existingContent = readExistingContent();
            long existingFileSize = getFileSize();

            if (existingFileSize + stackTrace.length() > MAX_FILE_SIZE) {
                String renamedFilePath = generateTimestampedFilePath();
                Files.move(Paths.get(ERROR_LOG), Paths.get(renamedFilePath));
                existingContent = ""; // Reset existing content
            }

            try (PrintWriter printWriter = new PrintWriter(new FileWriter(ERROR_LOG, false))) {
                printWriter.println("Timestamp: " + timestamp);
                printWriter.println(stackTrace);
                printWriter.println();
                printWriter.print(existingContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PrintException(OCORREU_UM_ERRO_AO_SALVAR_O_LOG);
        }
    }

    private static String readExistingContent() {
        try {
            byte[] contentBytes;
            File file = new File(ERROR_LOG);
            Path path = Paths.get(ERROR_LOG);
            if (!file.exists()) {
                Files.createFile(path);
                contentBytes = new byte[0];
            } else {
                contentBytes = Files.readAllBytes(path);
            }
            return new String(contentBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PrintException(OCORREU_UM_ERRO_AO_SALVAR_O_LOG);
        }
    }

    private static long getFileSize() {
        try {
            return Files.size(Paths.get(SaveStackTrace.ERROR_LOG));
        } catch (IOException e) {
            e.printStackTrace();
            throw new PrintException(OCORREU_UM_ERRO_AO_SALVAR_O_LOG);
        }
    }

    private static String generateTimestampedFilePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return "error_" + timestamp + ".log";
    }

    private static String getFormattedTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(new Date());
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    private class PrintException extends RuntimeException {

        public PrintException(String message) {
            super(message);
        }
    }
}
