package com.drk.tools.util;

import com.drk.tools.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Utility class to read the images from file system.
 *
 * @author PranaySK
 * @since 1.0
 */

@Component
public class ImageReader {

    private static final Logger logger = LoggerFactory.getLogger(ImageReader.class);

    @Autowired
    private AppConfig properties;

    public Resource readBmImage(final String imageName) {

        logger.debug("Read BmImages at -> {}", properties.getExtractAllPath());

        final File dir = new File(properties.getExtractAllPath());
        Resource imageFile = null;
        int imageCheckFlag = 0;

        try {
            if (dir.isDirectory()) {
                for (final File imgFile : dir.listFiles(IMAGE_FILTER)) {
                    if (imgFile.getName().split("\\.")[0].equalsIgnoreCase(imageName)) {
                        imageFile = new FileSystemResource(imgFile);
                        logger.debug("Matched imageName -> {}", imageFile.getFilename());
                        imageCheckFlag = 1;
                        break;
                    }
                }
                if (imageCheckFlag == 0) {
                    logger.debug("UnMatched imageName => {}", imageName);
                }
            } else {
                logger.error("Directory path not exist -> {}", dir);
            }
        } catch (Exception ex) {
            logger.error("Error while reading image -> {}", ex.getMessage());
        }

        return imageFile;
    }

    /**
     * array of supported extensions
     */
    private static final String[] EXTENSIONS = new String[]{"jpeg", "jpg"};

    /**
     * filter to identify images based on their extensions
     */
    private static final FilenameFilter IMAGE_FILTER = ((dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    });

}
