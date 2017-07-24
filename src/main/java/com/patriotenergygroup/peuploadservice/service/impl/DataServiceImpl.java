package com.patriotenergygroup.peuploadservice.service.impl;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.patriotenergygroup.peuploadservice.service.DataService;

@Service("dataService")
public class DataServiceImpl implements DataService {

	private final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
	
	@Override
	public boolean updateInformation(Path file) {
		// TODO Auto-generated method stub
		// read and validate file
		// parse file
		// add rows to the db for the new rows

		logger.info(String.format("%s", file.getFileName()));
		
		
		return false;
	}

}
