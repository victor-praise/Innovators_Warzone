package main.java.utils.logger;

import java.io.File;
import java.io.FileWriter;

public class LogWriter implements Observer{

    /**
     * File name for logger
     */
    private String l_Filename = "log";

    /**
     * constructor that ensures logs are cleared
     */
    public LogWriter() {
        clearLogs();
    }

    /**
     * A function to write the actions of the game to a logfile named demolog.
     *
     * @param p_message The message to be written to the log file.
     */
    public void writeLogFile(String p_message) {

        try {
            File directory = new File("Logs");
            if (!directory.exists() || !directory.isDirectory()) {
                directory.mkdirs();
            }
            File file = new File("Logs", l_Filename);
            FileWriter writer = new FileWriter(file, true); // Use 'true' to append to the file
            writer.write(p_message + "\n");
            writer.close();

        } catch (Exception p_Exception) {
            System.out.println(p_Exception.getMessage());
        }
    }

    /**
     * receives update from Subject and then writes to LogFile.
     *
     * @param p_message the message to be updated
     */
    @Override
    public void update(String p_message) {
        writeLogFile(p_message);
    }

    /**
     * Clears the log file before a new game starts.
     */
    @Override
    public void clearLogs() {
        try {
            File directory = new File("Logs");
            if (!directory.exists() || !directory.isDirectory()) {
                directory.mkdirs();
            }
            File l_File = new File("Logs/" + l_Filename + ".log");
            if (l_File.exists()) {
                l_File.delete();
            }
        } catch (Exception ex) {

        }
    }
}
