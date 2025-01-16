package com.alfresco.archival.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfresco.archival.Entity.CategoryMaster;

public interface CategoryMasterRepository extends JpaRepository<CategoryMaster, UUID>{

}
