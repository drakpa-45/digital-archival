package com.alfresco.archival.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfresco.archival.Entity.DrcFolder;

public interface FolderRepository extends JpaRepository<DrcFolder, UUID> {
	 Optional<DrcFolder> findByNameAndParentFolderIdIsNull(String name);
	 Optional<DrcFolder> findByNameAndParentFolderId(String name, UUID parentFolderId);
}

