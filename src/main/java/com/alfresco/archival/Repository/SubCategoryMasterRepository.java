package com.alfresco.archival.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfresco.archival.Entity.SubCategoryMaster;

public interface SubCategoryMasterRepository extends JpaRepository<SubCategoryMaster, UUID>{

}
