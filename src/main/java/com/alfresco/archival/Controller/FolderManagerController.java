package com.alfresco.archival.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfresco.archival.DTO.FolderRequestDTO;
import com.alfresco.archival.Service.FileStorageService;

@RestController
@RequestMapping("/folderManager")
public class FolderManagerController {
	
	@Autowired
    private FileStorageService fileStorageService;

   

    // Create Folder
    @PostMapping("/createFolder/{folderName}")
    public ResponseEntity<String> createFolder(@PathVariable("folderName") String folderName) {
        try {
            fileStorageService.createFolder(folderName);
            return ResponseEntity.ok("Folder created successfully: " + folderName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error creating folder: " + e.getMessage());
        }
    }
    
    // Create Subfolder
    @PostMapping("/createSubFolder/{parentFolder}/{subFolderName}")
    public ResponseEntity<String> createSubFolder(@PathVariable("parentFolder") String parentFolder, @PathVariable("subFolderName") String subFolderName) {
        try {
            fileStorageService.createSubFolder(parentFolder, subFolderName);
            return ResponseEntity.ok("Subfolder created successfully under " + parentFolder + ": " + subFolderName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error creating subfolder: " + e.getMessage());
        }
    }
    
    @PostMapping("/createNestedFolders")
    public ResponseEntity<String> createNestedFolders(@RequestBody FolderRequestDTO folderRequest) {
        try {
              	fileStorageService.createFolderStructure(folderRequest.getParentFolder(), folderRequest.getSubfolders(),folderRequest.getIsInternal());
            return ResponseEntity.ok("Folders created successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error creating folders: " + e.getMessage());
        }
    }
   
}
