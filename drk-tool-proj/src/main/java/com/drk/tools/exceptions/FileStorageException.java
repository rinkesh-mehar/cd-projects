/**
 * 
 */
package com.drk.tools.exceptions;

/**
 * @author Rinkesh Mehar - Cropdata
 *
 * 26-DEC-2020
 */
public class FileStorageException extends RuntimeException{

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
