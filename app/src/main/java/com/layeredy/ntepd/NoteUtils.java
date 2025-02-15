package com.layeredy.ntepd;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class NoteUtils {

    public static void readAllNotes(Context context) {
        File directory = new File(MainActivity.getInstance().getFilesDir(), "notes");

        // Ensure the directory exists
        if (!directory.exists()) {
            if (!directory.mkdir()) {  // Attempt to create the directory if missing
                System.out.println("Failed to create notes directory.");
                return;
            }
        }

        // Check again if it's a valid directory
        if (!directory.isDirectory()) {
            System.out.println("Notes path is not a directory.");
            return;
        }

        // Get the list of files
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No saved notes found.");
            return;
        }

        // Loop through and read notes
        for (File file : files) {
            String content = readNote(file.getName().replaceAll(".txt", ""), context);
            System.out.println("Note: " + file.getName().replaceAll(".txt", "") + "Content: " + content);
        }
    }

    public static void saveNote(String filename, String data, Context context) {
        File directory = new File(MainActivity.getInstance().getFilesDir(), "notes");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directory, filename + ".txt");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes());
            Toast.makeText(context, "File saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to save file", Toast.LENGTH_SHORT).show();
        }
    }


    public static String readNote(String filename, Context context) {
        File directory = new File(MainActivity.getInstance().getFilesDir(), "notes");
        File file = new File(directory, filename + ".txt");

        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to read file", Toast.LENGTH_SHORT).show();
        }
        return stringBuilder.toString();
    }

}
