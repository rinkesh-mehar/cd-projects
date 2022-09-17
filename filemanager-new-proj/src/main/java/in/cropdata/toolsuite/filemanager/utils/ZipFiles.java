package in.cropdata.toolsuite.filemanager.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFiles {

    List<String> filesListInDir = new ArrayList<String>();
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

//    public static void main(String[] args) {
//        File file = new File("/Users/pankaj/sitemap.xml");
//        String zipFileName = "/Users/pankaj/sitemap.zip";
//        
//        File dir = new File("/Users/pankaj/tmp");
//        String zipDirName = "/Users/pankaj/tmp.zip";
//        
//        zipSingleFile(file, zipFileName);
//        
//        ZipFiles zipFiles = new ZipFiles();
//        zipFiles.zipDirectory(dir, zipDirName);
//    }

    /**
     * This method zips the directory
     * 
     * @param dir
     * @param zipDirName
     */
    public void zipDirectory(File dir, String zipDirName) {
	try {
	    populateFilesList(dir);
	    // now zip files one by one
	    // create ZipOutputStream to write to the zip file
	    FileOutputStream fos = new FileOutputStream(zipDirName);
	    ZipOutputStream zos = new ZipOutputStream(fos);
	    for (String filePath : filesListInDir) {
		System.out.println("Zipping " + filePath);
		// for ZipEntry we need to keep only relative file path, so we used substring on
		// absolute path
		String fileName = filePath.replaceAll("/tmp/hdfs_temp/", "");
		System.out.println("FileName: " + fileName);
		if (!(fileName.startsWith(".") && fileName.endsWith(".crc"))) {
		    ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
		    zos.putNextEntry(ze);
		    // read the file and write to ZipOutputStream
		    FileInputStream fis = new FileInputStream(filePath);
		    byte[] buffer = new byte[1024];
		    int len;
		    while ((len = fis.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		    }
		    zos.closeEntry();
		    fis.close();
		} // if not hidden file

	    }
	    zos.close();
	    fos.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * This method populates all the files in a directory to a List
     * 
     * @param dir
     * @throws IOException
     */
    public void populateFilesList(File dir) throws IOException {
	File[] files = dir.listFiles();
	for (File file : files) {
	    if (file.isFile())
		filesListInDir.add(file.getAbsolutePath());
	    else
		populateFilesList(file);
	}
    }

    public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
	Path p = Files.createFile(Paths.get(zipFilePath));
	try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
	    Path pp = Paths.get(sourceDirPath);
	    Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path -> {
		ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
		try {
		    zs.putNextEntry(zipEntry);
		    zs.write(Files.readAllBytes(path));
		    zs.closeEntry();
		} catch (Exception e) {
		    System.err.println(e);
		}
	    });
	}
    }// pack

    /**
     * This method compresses the single file to zip format
     * 
     * @param file
     * @param zipFileName
     */
    public static void zipSingleFile(File file, String zipFileName) {
	try {
	    // create ZipOutputStream to write to the zip file
	    FileOutputStream fos = new FileOutputStream(zipFileName);
	    ZipOutputStream zos = new ZipOutputStream(fos);
	    // add a new Zip Entry to the ZipOutputStream
	    ZipEntry ze = new ZipEntry(file.getName());
	    zos.putNextEntry(ze);
	    // read the file and write to ZipOutputStream
	    FileInputStream fis = new FileInputStream(file);
	    byte[] buffer = new byte[1024];
	    int len;
	    while ((len = fis.read(buffer)) > 0) {
		zos.write(buffer, 0, len);
	    }

	    // Close the zip entry to write to zip file
	    zos.closeEntry();
	    // Close resources
	    zos.close();
	    fis.close();
	    fos.close();
	    System.out.println(file.getCanonicalPath() + " is zipped to " + zipFileName);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * 
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
	File destDir = new File(destDirectory);
	if (!destDir.exists()) {
	    destDir.mkdir();
	}
	ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	ZipEntry entry = zipIn.getNextEntry();
	// iterates over entries in the zip file
	while (entry != null) {
	    String filePath = destDirectory + File.separator + entry.getName();
	    if (!entry.isDirectory()) {
		// if the entry is a file, extracts it
		extractFile(zipIn, filePath);
	    } else {
		// if the entry is a directory, make the directory
		File dir = new File(filePath);
		dir.mkdir();
	    }
	    zipIn.closeEntry();
	    entry = zipIn.getNextEntry();
	}
	zipIn.close();
    }

    /**
     * Extracts a zip entry (file entry)
     * 
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
	byte[] bytesIn = new byte[BUFFER_SIZE];
	int read = 0;
	while ((read = zipIn.read(bytesIn)) != -1) {
	    bos.write(bytesIn, 0, read);
	}
	bos.close();
    }

//    public static void main(String[] args) {
//	String zipFilePath = "e:/Test/MyPics.zip";
//	String destDirectory = "f:/Pics";
//	ZipFiles unzipper = new ZipFiles();
//	try {
//	    unzipper.unzip(zipFilePath, destDirectory);
//	} catch (Exception ex) {
//	    ex.printStackTrace();
//	}
//    }
}