package com.patriotenergygroup.peuploadservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.patriotenergygroup.peuploadservice.exception.DataUpdateException;
import com.patriotenergygroup.peuploadservice.exception.StorageFileNotFoundException;
import com.patriotenergygroup.peuploadservice.service.DataService;
import com.patriotenergygroup.peuploadservice.service.StorageService;

@RestController
@RequestMapping("/upload")
public class UploadController {
	
	private final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private StorageService storageService; 
	
	@Autowired
	private DataService dataService;

	
	@PostMapping("/file")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.debug("Loading the file");

        String uploadedFilename = storageService.store(file);
        logger.info(uploadedFilename);
        dataService.updateInformation(storageService.load(uploadedFilename));

        return new ResponseEntity<>("Successfully uploaded - " +
                uploadedFilename, new HttpHeaders(), HttpStatus.OK);
	}
	
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }	

    @SuppressWarnings("rawtypes")
	@ExceptionHandler(DataUpdateException.class)
    public ResponseEntity handleStorageFileNotFound(DataUpdateException exc) {
    	storageService.deleteFile(exc.getFileToDelete());
        return ResponseEntity.notFound().build();
    }	
}
