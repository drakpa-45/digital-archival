package com.alfresco.archival.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_alfresco_drc_document")
@Data
@Getter
@Setter
public class DocumentEntityDRC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(name="alfresco_ref_no", nullable = false)
    private String alfrescorRefNo;

    @Column
    private String document_type;

    @Column
    private String document_name;

    @Column
    private String upload_url; // Path to the stored file

    @Column
    private String uuid;

    @Column
    private String type;
    
 // Getters and Setters
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getAlfrescorRefNo() {
		return alfrescorRefNo;
	}

	public void setAlfrescoRefNo(String alfrescorRefNo) {
		this.alfrescorRefNo = alfrescorRefNo;
	}

	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public String getUpload_url() {
		return upload_url;
	}

	public void setUpload_url(String upload_url) {
		this.upload_url = upload_url;
	}

	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
