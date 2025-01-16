package com.alfresco.archival.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfresco.archival.Entity.AgencyMaster;
import com.alfresco.archival.Entity.CategoryMaster;
import com.alfresco.archival.Entity.SubCategoryMaster;
import com.alfresco.archival.Service.MasterService;

@RestController
@RequestMapping("/masterManagement")
public class MasterManagementController {
    @Autowired
    private MasterService masterService; // Assume a service to handle DB operations

    // Method to create/add agency
    @PostMapping("/addAgency")
    public ResponseEntity<String> addAgency(@RequestBody AgencyMaster agencyMaster) {
        try {
        	masterService.createAgencyMaster(agencyMaster);
            return new ResponseEntity<>("Agency added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add agency: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Method to create/add category
    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryMaster categoryMaster) {
        try {
        	masterService.createCategoryMaster(categoryMaster);
            return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add Category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Method to create/add subcategory
    @PostMapping("/addSubCategory")
    public ResponseEntity<String> addSubCategory(@RequestBody SubCategoryMaster subCategoryMaster) {
        try {
        	masterService.createSubCategoryMaster(subCategoryMaster);
            return new ResponseEntity<>("subcategory added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add subcategory: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
