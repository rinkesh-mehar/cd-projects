package com.krishi.fls.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCopyDemo {

    public static void main(String args[]) {

        try {
            // let's create a ZIP file to write data
            FileOutputStream fos = new FileOutputStream("/home/janardhana/zipfolder/sample.zip");
            ZipOutputStream zipOS = new ZipOutputStream(fos);

            String file1 = "/home/janardhana/example/1.txt";
            String file2 = "/home/janardhana/example/2.txt";
        

            writeToZipFile(file1, zipOS);
            writeToZipFile(file2, zipOS);
        

            zipOS.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add a file into Zip archive in Java.
     * 
     * @param fileName
     * @param zos
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeToZipFile(String path, ZipOutputStream zipStream)
            throws FileNotFoundException, IOException {

        System.out.println("Writing file : '" + path + "' to zip file");

        File aFile = new File(path);
        FileInputStream fis = new FileInputStream(aFile);
        ZipEntry zipEntry = new ZipEntry(path);
        zipStream.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipStream.write(bytes, 0, length);
        }

        zipStream.closeEntry();
        fis.close();
    }
}