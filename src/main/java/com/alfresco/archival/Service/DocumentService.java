package com.alfresco.archival.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alfresco.archival.Entity.DocumentEntityDRC;
import com.alfresco.archival.Repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public DocumentEntityDRC saveDocument(DocumentEntityDRC document) {
    	
        return documentRepository.save(document);
    }

}
