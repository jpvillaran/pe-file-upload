package com.patriotenergygroup.peuploadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.patriotenergygroup.peuploadservice.configuration.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class UploadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadServiceApplication.class, args);
	}
}
