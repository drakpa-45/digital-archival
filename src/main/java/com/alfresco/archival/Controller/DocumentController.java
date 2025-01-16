package com.alfresco.archival.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alfresco.archival.Entity.DocumentEntityDRC;
import com.alfresco.archival.Service.DocumentService;
import com.alfresco.archival.Service.FileStorageService;

@RestController
@RequestMapping("/documents")
public class DocumentController {

	@Autowired
	DocumentService documentService;
	
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public DocumentEntityDRC uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType,
            @RequestParam("type") String type) {
    	DocumentEntityDRC dto = new DocumentEntityDRC();
        try {
        	dto=fileStorageService.storeFile(file, documentType, type);
        	System.out.print("============== document added successfully ===========");
            return dto;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
}

