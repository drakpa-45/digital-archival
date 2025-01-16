package com.alfresco.archival.DTO;

import java.util.List;

public class FolderRequestDTO {
	private String parentFolder;
    private List<SubfolderDTO> subfolders;
    private String isInternal;
    
 // Getters and setters
	public String getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	public String getParentFolder() {
		return parentFolder;   
	}
	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
	}
	public List<SubfolderDTO> getSubfolders() {
		return subfolders;
	}
	public void setSubfolders(List<SubfolderDTO> subfolders) {
		this.subfolders = subfolders;
	}
}
