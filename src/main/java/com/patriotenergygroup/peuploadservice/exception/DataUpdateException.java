package com.patriotenergygroup.peuploadservice.exception;

import java.nio.file.Path;

public class DataUpdateException extends RuntimeException {

	private Path fileToDelete;
	
    public DataUpdateException(String message, Path fileToDelete) {
        super(message);
        this.fileToDelete = fileToDelete;
    }

    public DataUpdateException(String message, Path fileToDelete, Throwable cause) {
        super(message, cause);
        this.fileToDelete = fileToDelete;
    }
    
    public Path getFileToDelete() {
    	return this.fileToDelete;
    }
}