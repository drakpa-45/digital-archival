package com.alfresco.archival.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alfresco.archival.Entity.AgencyMaster;
import com.alfresco.archival.Entity.CategoryMaster;
import com.alfresco.archival.Entity.SubCategoryMaster;
import com.alfresco.archival.Repository.AgencyMasterRepository;
import com.alfresco.archival.Repository.CategoryMasterRepository;
import com.alfresco.archival.Repository.SubCategoryMasterRepository;

@Service
public class MasterService {
    @Autowired
    private AgencyMasterRepository agencyMasterRepository;
    
    @Autowired
    private CategoryMasterRepository categoryMasterRepository;
    
    @Autowired
    private SubCategoryMasterRepository subCategoryMasterRepository;

    // Method to create a new agency
    @Transactional
    public void createAgencyMaster(AgencyMaster agencyMaster) {
        // Save agencyMaster to the database
        agencyMasterRepository.save(agencyMaster);
    }
    
    // Method to create a new category
    @Transactional
    public void createCategoryMaster(CategoryMaster categoryMaster) {
        // Save categoryMaster to the database
    	categoryMasterRepository.save(categoryMaster);
    }

    @Transactional
	public void createSubCategoryMaster(SubCategoryMaster subCategoryMaster) {
		// TODO Auto-generated method stub
		subCategoryMasterRepository.save(subCategoryMaster);
	}
}
