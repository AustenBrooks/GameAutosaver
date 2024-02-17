import java.io.*;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Autosaver {
    private static String inputFolder = null;
    private static String inputFileName = "savefile.sav";
    private static String outputFolder = "C:/GameSaves/";
    private static int saveCount = 0;
    private static int maxSaveCount = 10;
    private static int autoSaveInterval = 15;


    public static void main(String[] args) throws Exception {
        // load input from jar arguments
        inputFolder = args[0];
        inputFileName = args[1];
        if (args.length > 2)
            outputFolder = args[2];
        if (args.length > 3)
            maxSaveCount = Integer.parseInt(args[3]);
        if (args.length > 4)
            autoSaveInterval = Integer.parseInt(args[4]);

        // use forward slashes and end folders with slash
        outputFolder = outputFolder.replace("\\", "/");
        inputFolder = inputFolder.replace("\\", "/");
        if (!outputFolder.endsWith("/"))
            outputFolder += "/";
        if (!inputFolder.endsWith("/"))
            inputFolder += "/";

        // print parameters
        System.out.println();
        System.out.println("Copying from: " + inputFolder);
        System.out.println("Copying to: " + outputFolder);
        System.out.println("Autosaving every " + autoSaveInterval + " minutes");
        System.out.println("Saving up to " + maxSaveCount + " autosaves");
        System.out.println();

        // initialize saveCount by searching for existing autosave files (not all that useful since it doesn't look at modified date)
        initializeSaveCount();

        // make sure outputFolder exists
        createOutputFolder(outputFolder);

        // start saving
        Timer timer = new Timer();
        timer.schedule(new AutosaveTask(), 0, autoSaveInterval * 60 * 1000); // autoSaveInterval minutes in milliseconds
    }

    private static void initializeSaveCount() {
        File folder = new File(outputFolder);
        File[] files = folder.listFiles((dir, name) -> name.matches("autosave\\d"));
        if (files != null) {
            long maxLastModifiedTime = Long.MIN_VALUE;
            String lastModifiedFileName = null;
            for (File file : files) {
                if (file.isFile() && file.lastModified() > maxLastModifiedTime) {
                    maxLastModifiedTime = file.lastModified();
                    lastModifiedFileName = file.getName();
                }
            }
            if(lastModifiedFileName != null)
                saveCount = Integer.parseInt(lastModifiedFileName.replaceAll("[^\\d]", "")) + 1;
        }
    }

    private static void createOutputFolder(String folderPath) throws Exception {
        File folder = new File(folderPath);

        if (folder.exists()) {
            System.out.println(folderPath + " already exists.");
        }
        else {
            if (folder.mkdir())
                System.out.println(folderPath + " created successfully.");
            else
                throw new IOException("Error creating " + folderPath);
        }
    }
    private static class AutosaveTask extends TimerTask {
        @Override
        public void run() {
            if (saveCount >= maxSaveCount){
                saveCount = 0;
            }

            File input = new File(inputFolder + inputFileName);
            String autosaveFileName = outputFolder + "autosave" + saveCount;
            File output = new File(autosaveFileName);

            try (FileInputStream fis = new FileInputStream(input);
                 FileOutputStream fos = new FileOutputStream(output)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println(autosaveFileName + " created successfully at " + LocalDateTime.now());
                saveCount++;


            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
