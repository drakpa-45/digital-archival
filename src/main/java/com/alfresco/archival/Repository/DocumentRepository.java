package com.alfresco.archival.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfresco.archival.Entity.DocumentEntityDRC;

public interface DocumentRepository extends JpaRepository<DocumentEntityDRC,Long>{

}
